package com.runekit;

import com.google.inject.Inject;
import com.google.inject.Provides;

import net.runelite.api.*;
import com.runekit.services.BirdHouseService;
import com.runekit.services.FarmingService;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import java.io.IOException;

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

    @Provides
    RunekitConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(RunekitConfig.class);
    }

    @Override
    protected void startUp() {
        log.debug("RuneKit started!");
        farmingService.loadCurrentConfig();
        birdHouseService.loadCurrentConfig();
        //add quest tracker service -- future update
    }

    @Override
    protected void shutDown() {
        log.debug("RuneKit stopped!");
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
    public void onGameTick(GameTick t) {
        birdHouseService.updateStoredData(client.getLocalPlayer().getWorldLocation());
        farmingService.updateStoredData(client.getLocalPlayer().getWorldLocation());
    }
}
