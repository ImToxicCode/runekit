package com.runekit;

import com.google.inject.Inject;
import com.google.inject.Provides;

import com.runekit.services.RaidCoxPingerService;
import net.runelite.api.*;
import com.runekit.services.BirdHouseService;
import com.runekit.services.FarmingService;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.VarbitChanged;
import net.runelite.client.Notifier;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.game.WorldService;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@PluginDescriptor(
        name = "Runekit"
)
public class RunekitPlugin extends Plugin {
    @Inject
    private Client client;
    @Inject
    private RunekitConfig runekitConfig;
    @Inject
    private BirdHouseService birdHouseService;
    @Inject
    private FarmingService farmingService;
    @Inject
    private ConfigManager configManager;
    @Inject
    private RaidCoxPingerService raidCoxPingerService;
    @Inject
    private WorldService worldService;
    @Inject
    private Notifier notifier;
    @Inject
    private ScheduledExecutorService executorService;

    private ScheduledFuture scheduledFuturePing;
    private ScheduledFuture scheduledFutureCheckDC;
    private boolean inTOB;
    private boolean inCOX;
    private boolean isLoggedIn;
    private int raidPartyId;
    private boolean raidEstablished;
    private int raidPartySize;

    @Provides
    RunekitConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(RunekitConfig.class);
    }

    @Override
    protected void startUp() {
        log.debug("RuneKit started!");
        raidEstablished = false;
        inCOX = false;
        inTOB = false;
        farmingService.loadCurrentConfig();
        birdHouseService.loadCurrentConfig();

        scheduledFuturePing = executorService.scheduleAtFixedRate(this::checkPing, 10, 20, TimeUnit.SECONDS);
        scheduledFutureCheckDC = executorService.scheduleAtFixedRate(this::checkForDCMembers, 10, 20, TimeUnit.SECONDS);

        //add quest tracker service -- future update
    }

    @Override
    protected void shutDown() {
        scheduledFuturePing.cancel(true);
        scheduledFuturePing = null;
        scheduledFutureCheckDC.cancel(true);
        scheduledFutureCheckDC = null;
        log.debug("RuneKit stopped!");
    }

    @Subscribe
    public void onGameStateChanged(GameStateChanged event) {
        if (event.getGameState() == GameState.LOGGED_IN) {
            isLoggedIn = true;
        } else if (client.getGameState() == GameState.LOGIN_SCREEN || client.getGameState() == GameState.CONNECTION_LOST) {
            isLoggedIn = false;
        }
    }

    @Subscribe
    public void onConfigChanged(ConfigChanged configChanged) {
        if (!configChanged.getGroup().equals("runekit")) {
            return;
        } else {
            farmingService.loadCurrentConfig();
            birdHouseService.loadCurrentConfig();
        }
    }

    @Subscribe
    public void onVarbitChanged(VarbitChanged event) {
        // Raid state 5 = not in raid | 0 = in raid party | 1 = started
        boolean tempInTob = client.getVar(Varbits.THEATRE_OF_BLOOD) > 0;
        boolean tempInCox = client.getVar(Varbits.RAID_STATE) == 1;

        if (client.getVar(Varbits.RAID_STATE) == 0) {
            if(client.getVar(VarPlayer.IN_RAID_PARTY) > 0) {
                raidPartyId = client.getVar(VarPlayer.IN_RAID_PARTY);
            }
        }

        if (!raidEstablished) {
            if (tempInCox && isLoggedIn) {
                log.debug("JOINED COX! RAID: " + raidPartyId);
                inCOX = true;
                inTOB = false;
                raidEstablished = true;
            }
            if (tempInTob && isLoggedIn) {
                raidPartyId = client.getVarpValue(1740);
                log.debug("JOINED TOB! RAID: " + raidPartyId);
                inTOB = true;
                inCOX = false;
                raidEstablished = true;
            }
        } else if (raidEstablished) {
            if (tempInCox != inCOX && client.getVar(Varbits.RAID_STATE) == 5) {
                log.debug("LEFT COX!");
                inCOX = false;
                raidEstablished = false;
                raidCoxPingerService.leaveRaid(raidPartyId, "COX", client.getLocalPlayer().getName());
            }
            if (tempInTob != inTOB) {
                log.debug("LEFT TOB!");
                inTOB = false;
                raidEstablished = false;
                raidCoxPingerService.leaveRaid(raidPartyId, "TOB", client.getLocalPlayer().getName());
            }
        }
    }

    @Subscribe
    public void onGameTick(GameTick t) {
        birdHouseService.updateStoredData(client.getLocalPlayer().getWorldLocation());
        farmingService.updateStoredData(client.getLocalPlayer().getWorldLocation());
    }

    private void checkPing() {
        log.debug("Ping!");
        if (raidEstablished && raidPartyId > 0) {
            if (inCOX) {
                log.debug("COX raiding....");
                raidCoxPingerService.ping(raidPartyId, "COX", client.getLocalPlayer().getName());
            }
            if (inTOB) {
                log.debug("TOB raiding....");
                raidCoxPingerService.ping(raidPartyId, "TOB", client.getLocalPlayer().getName());
            }
        }
    }

    private void checkForDCMembers() {
        if (raidEstablished) {
            if(isLoggedIn) {
                raidCoxPingerService.checker(raidPartyId);
            }
        }
    }
}
