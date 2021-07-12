package com.runekit.services;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.runekit.RunekitConfig;
import lombok.extern.slf4j.Slf4j;
import com.runekit.models.BirdHouseTile;
import com.runekit.models.NotificationRequest;
import net.runelite.api.Client;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.config.ConfigManager;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Singleton
public class BirdHouseService {

    private final Client client;
    private final ConfigManager configManager;
    private final RunekitConfig runekitConfig;

    private static ImmutableSet<Integer> FOSSIL_ISLAND_REGIONS = ImmutableSet.of(14650, 14651, 14652, 14906, 14907, 15162, 15163);

    private final Map<String, Integer> storedData = new HashMap<>();

    @Inject
    private BirdHouseService(Client client, ConfigManager configManager, RunekitConfig runekitConfig) {
        this.client = client;
        this.configManager = configManager;
        this.runekitConfig = runekitConfig;
    }

    public void loadCurrentConfig() {
        storedData.clear();

        for (BirdHouseTile space : BirdHouseTile.values()) {
            String key = space.getName();
            String value = configManager.getRSProfileConfiguration(runekitConfig.CONFIG_GROUP, key);
            log.debug(value);
            if (value != null) {
                try {
                    storedData.put(key, Integer.valueOf(value));
                } catch (Exception ex) {
                    log.debug(ex.getMessage());
                }
            }
        }
    }

    public boolean updateStoredData(WorldPoint location) {
        boolean updated = false;
        if (FOSSIL_ISLAND_REGIONS.contains(location.getRegionID()) && location.getPlane() == 0) {
            final Map<String, Integer> newData = new HashMap<>();

            for (BirdHouseTile space : BirdHouseTile.values()) {
                int id = client.getVar(space.getVarPlayer());
                int old = storedData.get(space.getName()) == null ? -1 : storedData.get(space.getName());

                if (id != old) {
                    if (old == 19 && id == 21) {
                        NotificationRequest request = new NotificationRequest();
                        request.Minutes = 50;
                        request.Name = space.getName();
                        request.RsName = client.getLocalPlayer().getName();
                        request.Type = "Hunter";

                        try {
                            log.debug("Creating notification-" + request.Name);
                            RunekitClient _client = new RunekitClient(runekitConfig.username(), runekitConfig.token(), request);
                            new Thread(_client).start();
                        } catch (Exception ex) {
                            log.debug(ex.getMessage());
                        }
                    }

                    newData.put(space.getName(), id);
                    updated = true;
                    break;
                }
            }

            if (updated) {
                storedData.putAll(newData);
                updateConfig(newData);
            }
        }

        return updated;
    }

    private void updateConfig(Map<String, Integer> updatedData) {
        for (Map.Entry<String, Integer> item : updatedData.entrySet()) {
            String key = item.getKey();
            configManager.setRSProfileConfiguration(runekitConfig.CONFIG_GROUP, key, item.getValue());
        }
    }
}
