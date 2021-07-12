package com.runekit;

import com.google.inject.Provides;

import javax.inject.Inject;

import com.runekit.services.RaidCoxPingerService;
import net.runelite.api.*;
import com.runekit.services.BirdHouseService;
import com.runekit.services.FarmingService;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.VarbitChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import java.util.Date;

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

    private boolean inTOB;
    private boolean inCOX;
    private boolean isLoggedIn;
    private int raidPartyId;
    private boolean raidEstablished;
    private Date lastCheck;

    @Provides
    RunekitConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(RunekitConfig.class);
    }

    @Override
    protected void startUp() {
        log.debug("RuneKit started!");
        farmingService.loadCurrentConfig();
        birdHouseService.loadCurrentConfig();
        //Check server to see if record exist and set alreadyInRaidParty

        //add quest tracker service -- future update
    }

    @Override
    protected void shutDown() {
        log.debug("RuneKit stopped!");
    }

    @Subscribe
    public void onGameStateChanged(GameStateChanged event) {
        if(event.getGameState() == GameState.LOGGED_IN)
        {
            isLoggedIn = true;
        }
        else if(client.getGameState() == GameState.LOGIN_SCREEN || client.getGameState() == GameState.CONNECTION_LOST) {
            isLoggedIn = false;
        }
    }

    @Subscribe
    public void onConfigChanged(ConfigChanged configChanged) {
        log.debug(configChanged.getGroup());
        if (!configChanged.getGroup().equals("runekit")) {
            return;
        } else {
            farmingService.loadCurrentConfig();
            birdHouseService.loadCurrentConfig();
        }
    }

    @Subscribe
    public void onVarbitChanged(VarbitChanged event) {
        int tempRaidPartyId = client.getVar(VarPlayer.IN_RAID_PARTY);
        boolean tempInTob = client.getVar(Varbits.THEATRE_OF_BLOOD) == 1;
        boolean tempInRaid = client.getVar(Varbits.IN_RAID) == 1;

        if(tempRaidPartyId != raidPartyId) {
            if((tempInRaid || tempInTob) && isLoggedIn) {
                inCOX = tempInRaid;
                inTOB = tempInTob;
                if(tempInRaid && !raidEstablished)
                {
                   raidEstablished = raidCoxPingerService.raidFormed(raidPartyId);
                }
                else if(tempInTob && !raidEstablished)
                {
                    raidEstablished = raidCoxPingerService.raidFormed(raidPartyId);
                }
            }
            else
            {
                inCOX = false;
                inTOB = false;
                raidEstablished = false;
            }
            raidPartyId = tempRaidPartyId;
        }
    }

    @Subscribe
    public void onGameTick(GameTick t) {
        birdHouseService.updateStoredData(client.getLocalPlayer().getWorldLocation());
        farmingService.updateStoredData(client.getLocalPlayer().getWorldLocation());
        if(inCOX || inTOB)
        {
            if(inCOX && raidEstablished)
            {
                if(lastCheck != null) {
                    long seconds = (new Date().getTime() - lastCheck.getTime()) / 1000;
                    if (seconds > 30) {
                        lastCheck = raidCoxPingerService.healthCheck(raidPartyId);
                    }
                }
                else
                {
                    lastCheck = raidCoxPingerService.healthCheck(raidPartyId);
                }
            }
        }
    }
}
