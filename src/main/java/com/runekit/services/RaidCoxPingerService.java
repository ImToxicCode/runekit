package com.runekit.services;

import com.google.inject.Singleton;
import com.runekit.RunekitConfig;
import com.runekit.models.PartyResponse;
import com.runekit.models.RaidParty;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.client.Notifier;
import net.runelite.client.config.ConfigManager;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Singleton
public class RaidCoxPingerService {

    private final Client client;
    private final ConfigManager configManager;
    private final RunekitConfig runekitConfig;
    private final ScheduledExecutorService _scheduledExecutorService;
    private final Notifier _notifier;

    @Inject
    private RaidCoxPingerService(Client client, ConfigManager configManager, RunekitConfig runekitConfig, Notifier notifier) {
        this.client = client;
        this.configManager = configManager;
        this.runekitConfig = runekitConfig;
        this._notifier = notifier;
        this._scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    }

    public boolean checker(int raidPartyId) {
        try {
            log.debug("Checking....");
            _scheduledExecutorService.schedule(new Runnable() {
                @Override
                public void run() {
                    List<PartyResponse> memebers = new RunekitClient(runekitConfig.username(), runekitConfig.token()).getPossibleDCPlayersInParty(String.valueOf(raidPartyId));
                    if (memebers.stream().count() == 1) {
                        _notifier.notify(String.format("%s has disconnected!", memebers.stream().findFirst().get().getPlayerName()));
                    } else if (memebers.stream().count() > 1) {
                        List<String> names = new ArrayList<>();
                        for (PartyResponse member : memebers) {
                            names.add(member.getPlayerName());
                        }
                        _notifier.notify(String.format("%s has disconnected!", names));
                    }
                }
            }, 30, TimeUnit.MILLISECONDS);
        } catch (Exception ex) {
            log.debug(ex.getMessage());
            return false;
        }

        return true;
    }

    public boolean leaveRaid(int raidPartyId, String type, String playerName) {
        RaidParty request = new RaidParty();
        request.Raid = type;
        request.Id = String.valueOf(raidPartyId);
        request.PlayerName = playerName;

        try {
            log.debug("Leaving raid");
            _scheduledExecutorService.schedule(new Runnable() {
                @Override
                public void run() {
                    new RunekitClient(runekitConfig.username(), runekitConfig.token()).leaveParty(request);
                }
            }, 30, TimeUnit.MILLISECONDS);
        } catch (Exception ex) {
            log.debug(ex.getMessage());
            return false;
        }

        return true;
    }

    public void ping(int raidPartyId, String type, String playerName) {
        RaidParty request = new RaidParty();
        request.Raid = type;
        request.Id = String.valueOf(raidPartyId);
        request.PlayerName = playerName;

        try {
            log.debug("Ping");
            _scheduledExecutorService.schedule(new Runnable() {
                @Override
                public void run() {
                    new RunekitClient(runekitConfig.username(), runekitConfig.token()).ping(request);
                }
            }, 30, TimeUnit.MILLISECONDS);
        } catch (Exception ex) {
            log.debug(ex.getMessage());
        }
    }
}
