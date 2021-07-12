package com.runekit.services;

import com.google.inject.Singleton;
import com.runekit.RunekitConfig;
import com.runekit.models.RaidParty;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.client.config.ConfigManager;

import javax.inject.Inject;
import java.util.Date;
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

    @Inject
    private RaidCoxPingerService(Client client, ConfigManager configManager, RunekitConfig runekitConfig) {
        this.client = client;
        this.configManager = configManager;
        this.runekitConfig = runekitConfig;
        this._scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    }


    public boolean raidFormed(int raidPartyId) {
        RaidParty request = new RaidParty();
        request.Raid = "COX";
        request.Id = String.valueOf(raidPartyId);
        request.PlayerName = client.getLocalPlayer().getName();

        try {
            log.debug("Establish raid");
            _scheduledExecutorService.schedule(new Runnable() {
                @Override
                public void run() {
                    new RunekitClient(runekitConfig.username(), runekitConfig.token()).joinedParty(request);
                }
            }, 30, TimeUnit.MILLISECONDS);
        } catch (Exception ex) {
            log.debug(ex.getMessage());
            return false;
        }

        return true;
    }

    public Date healthCheck(int raidPartyId) {
        RaidParty request = new RaidParty();
        request.Raid = "COX";
        request.Id = String.valueOf(raidPartyId);
        request.PlayerName = client.getLocalPlayer().getName();

        try {
            log.debug("Health check");
            _scheduledExecutorService.schedule(new Runnable() {
                @Override
                public void run() {
                    new RunekitClient(runekitConfig.username(), runekitConfig.token()).healthCheck(request);
                }
            }, 30, TimeUnit.MILLISECONDS);
        } catch (Exception ex) {
            log.debug(ex.getMessage());
        }
        return new Date();
    }
}
