package com.runekit.services;

import com.google.inject.Singleton;
import com.runekit.RunekitConfig;
import com.runekit.models.*;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Varbits;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;

import javax.inject.Inject;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Singleton
public class FarmingService {

    private final Client client;
    private final ConfigManager configManager;
    private final RunekitConfig runekitConfig;
    private final Map<String, Integer> storedData = new HashMap<>();
    private final Map<Integer[], FarmingRegion> farmingRegionMap = new HashMap<>();
    private final ScheduledExecutorService _scheduledExecutorService;

    @Inject
    private FarmingService(Client client, ConfigManager configManager, RunekitConfig runekitConfig) {
        this.client = client;
        this.configManager = configManager;
        this.runekitConfig = runekitConfig;
        this._scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        this.storedData.clear();

        List<FarmingPatch> spirtTreePatch = new ArrayList<>();
        spirtTreePatch.add(new FarmingPatch(Varbits.FARMING_4771, FarmingProduce.SPIRIT));

        List<FarmingPatch> gnomeStrongholdPatches = new ArrayList<>();
        gnomeStrongholdPatches.add(new FarmingPatch(Varbits.FARMING_4771, FarmingProduce.TREE));
        gnomeStrongholdPatches.add(new FarmingPatch(Varbits.FARMING_4772, FarmingProduce.FRUIT_TREE));

        List<FarmingPatch> brimhavenPatches = new ArrayList<>();
        brimhavenPatches.add(new FarmingPatch(Varbits.FARMING_4771, FarmingProduce.FRUIT_TREE));
        brimhavenPatches.add(new FarmingPatch(Varbits.FARMING_4772, FarmingProduce.SPIRIT));

        List<FarmingPatch> treePatch = new ArrayList<>();
        treePatch.add(new FarmingPatch(Varbits.FARMING_4771, FarmingProduce.TREE));

        List<FarmingPatch> bushPatch = new ArrayList<>();
        bushPatch.add(new FarmingPatch(Varbits.FARMING_4771, FarmingProduce.BUSH));

        List<FarmingPatch> hopsPatch = new ArrayList<>();
        hopsPatch.add(new FarmingPatch(Varbits.FARMING_4771, FarmingProduce.HOPS));

        List<FarmingPatch> herbPatch = new ArrayList<>();
        herbPatch.add(new FarmingPatch(Varbits.FARMING_4771, FarmingProduce.HERB));

        List<FarmingPatch> fruitTreePatch = new ArrayList<>();
        fruitTreePatch.add(new FarmingPatch(Varbits.FARMING_4771, FarmingProduce.FRUIT_TREE));

        List<FarmingPatch> hesporiPatch = new ArrayList<>();
        hesporiPatch.add(new FarmingPatch(Varbits.FARMING_7908, FarmingProduce.HESPORI));

        List<FarmingPatch> EtceteriaPatches = new ArrayList<>();
        EtceteriaPatches.add(new FarmingPatch(Varbits.FARMING_4771, FarmingProduce.BUSH));
        EtceteriaPatches.add(new FarmingPatch(Varbits.FARMING_4772, FarmingProduce.SPIRIT));

        List<FarmingPatch> regularPatches = new ArrayList<>();
        regularPatches.add(new FarmingPatch(Varbits.FARMING_4771, FarmingProduce.ALLOTMENT_1));
        regularPatches.add(new FarmingPatch(Varbits.FARMING_4772, FarmingProduce.ALLOTMENT_2));
        regularPatches.add(new FarmingPatch(Varbits.FARMING_4773, FarmingProduce.FLOWER));
        regularPatches.add(new FarmingPatch(Varbits.FARMING_4774, FarmingProduce.HERB));

        List<FarmingPatch> prifddinasPatches = new ArrayList<>();
        prifddinasPatches.add(new FarmingPatch(Varbits.FARMING_4771, FarmingProduce.ALLOTMENT_1));
        prifddinasPatches.add(new FarmingPatch(Varbits.FARMING_4772, FarmingProduce.ALLOTMENT_2));
        prifddinasPatches.add(new FarmingPatch(Varbits.FARMING_4773, FarmingProduce.FLOWER));
        prifddinasPatches.add(new FarmingPatch(Varbits.FARMING_4775, FarmingProduce.CRYSTAL));

        List<FarmingPatch> fossileIslandPatches = new ArrayList<>();
        fossileIslandPatches.add(new FarmingPatch(Varbits.FARMING_4771, FarmingProduce.SPECIAL_TREE));
        fossileIslandPatches.add(new FarmingPatch(Varbits.FARMING_4772, FarmingProduce.SPECIAL_TREE));
        fossileIslandPatches.add(new FarmingPatch(Varbits.FARMING_4773, FarmingProduce.SPECIAL_TREE));

        List<FarmingPatch> bwoWannaiPatches = new ArrayList<>();
        bwoWannaiPatches.add(new FarmingPatch(Varbits.FARMING_4771, FarmingProduce.SPECIAL_TREE));

        List<FarmingPatch> seaweedPatches = new ArrayList<>();
        seaweedPatches.add(new FarmingPatch(Varbits.FARMING_4771, FarmingProduce.SEAWEED));
        seaweedPatches.add(new FarmingPatch(Varbits.FARMING_4772, FarmingProduce.SEAWEED));

        List<FarmingPatch> harmonyPatches = new ArrayList<>();
        harmonyPatches.add(new FarmingPatch(Varbits.FARMING_4771, FarmingProduce.ALLOTMENT_1));
        harmonyPatches.add(new FarmingPatch(Varbits.FARMING_4772, FarmingProduce.HERB));

        List<FarmingPatch> kourendPatches = new ArrayList<>();
        kourendPatches.addAll(regularPatches);
        kourendPatches.add(new FarmingPatch(Varbits.FARMING_7904, FarmingProduce.SPIRIT));

        List<FarmingPatch> farmingGuildPatches = new ArrayList<>();
        farmingGuildPatches.add(new FarmingPatch(Varbits.FARMING_4773, FarmingProduce.ALLOTMENT_1));
        farmingGuildPatches.add(new FarmingPatch(Varbits.FARMING_4774, FarmingProduce.ALLOTMENT_2));
        farmingGuildPatches.add(new FarmingPatch(Varbits.FARMING_7906, FarmingProduce.FLOWER));
        farmingGuildPatches.add(new FarmingPatch(Varbits.FARMING_4775, FarmingProduce.HERB));
        farmingGuildPatches.add(new FarmingPatch(Varbits.FARMING_7905, FarmingProduce.TREE));
        farmingGuildPatches.add(new FarmingPatch(Varbits.FARMING_7909, FarmingProduce.FRUIT_TREE));
        farmingGuildPatches.add(new FarmingPatch(Varbits.FARMING_7907, FarmingProduce.REDWOOD));
        farmingGuildPatches.add(new FarmingPatch(Varbits.FARMING_7910, FarmingProduce.SPECIAL_TREE));
        farmingGuildPatches.add(new FarmingPatch(Varbits.FARMING_4772, FarmingProduce.BUSH));
        farmingGuildPatches.add(new FarmingPatch(Varbits.FARMING_7904, FarmingProduce.CACTUS));
        farmingGuildPatches.add(new FarmingPatch(Varbits.FARMING_4771, FarmingProduce.SPIRIT));
        farmingGuildPatches.add(new FarmingPatch(Varbits.FARMING_7911, FarmingProduce.ANIMA));

        farmingRegionMap.put(new Integer[] {15008}, new FarmingRegion("Underwater", seaweedPatches));
        farmingRegionMap.put(new Integer[] {11056}, new FarmingRegion("Tai Bwo Wannai", bwoWannaiPatches));
        farmingRegionMap.put(new Integer[] {12082}, new FarmingRegion("Port Sarim", spirtTreePatch));
        farmingRegionMap.put(new Integer[] {11570}, new FarmingRegion("Rimmington", bushPatch));
        farmingRegionMap.put(new Integer[] {10290}, new FarmingRegion("Ardougne", bushPatch));
        farmingRegionMap.put(new Integer[] {12596}, new FarmingRegion("Champions' Guild", bushPatch));
        farmingRegionMap.put(new Integer[] {10300}, new FarmingRegion("Etceteria", EtceteriaPatches));
        farmingRegionMap.put(new Integer[] {11060}, new FarmingRegion("Entrana", hopsPatch));
        farmingRegionMap.put(new Integer[] {10288}, new FarmingRegion("Yanille", hopsPatch));
        farmingRegionMap.put(new Integer[] {10551}, new FarmingRegion("Seers' Village", hopsPatch));
        farmingRegionMap.put(new Integer[] {12851}, new FarmingRegion("Lumbridge", hopsPatch));
        farmingRegionMap.put(new Integer[] {11058}, new FarmingRegion("Brimhaven", brimhavenPatches));
        farmingRegionMap.put(new Integer[] {13151}, new FarmingRegion("Prifddinas", prifddinasPatches));
        farmingRegionMap.put(new Integer[] {14391}, new FarmingRegion("Morytania", regularPatches));
        farmingRegionMap.put(new Integer[] {12083}, new FarmingRegion("Faldor", regularPatches));
        farmingRegionMap.put(new Integer[] {11828}, new FarmingRegion("Faldor", treePatch));
        farmingRegionMap.put(new Integer[] {11062}, new FarmingRegion("Catherby", regularPatches));
        farmingRegionMap.put(new Integer[] {11317}, new FarmingRegion("Catherby", fruitTreePatch));
        farmingRegionMap.put(new Integer[] {10548}, new FarmingRegion("Ardougne", regularPatches));
        farmingRegionMap.put(new Integer[] {14651, 14907, 14652}, new FarmingRegion("Fossil Island", fossileIslandPatches));
        farmingRegionMap.put(new Integer[] {9781}, new FarmingRegion("Gnome Stronghold", gnomeStrongholdPatches));
        farmingRegionMap.put(new Integer[] {6967, 6711}, new FarmingRegion("Kourend", kourendPatches));
        farmingRegionMap.put(new Integer[] {15148}, new FarmingRegion("Harmony", harmonyPatches));
        farmingRegionMap.put(new Integer[] {9265}, new FarmingRegion("Lletya", fruitTreePatch));
        farmingRegionMap.put(new Integer[] {12594}, new FarmingRegion("Lumbridge", treePatch));
        farmingRegionMap.put(new Integer[] {11573}, new FarmingRegion("Taverley", treePatch));
        farmingRegionMap.put(new Integer[] {9777}, new FarmingRegion("Tree Gnome Village", fruitTreePatch));
        farmingRegionMap.put(new Integer[] {11321}, new FarmingRegion("Troll Stronghold", herbPatch));
        farmingRegionMap.put(new Integer[] {12854}, new FarmingRegion("Varrock", treePatch));
        farmingRegionMap.put(new Integer[] {11325}, new FarmingRegion("Weiss", herbPatch));
        farmingRegionMap.put(new Integer[] {5021}, new FarmingRegion("Farming Guild", hesporiPatch));
        farmingRegionMap.put(new Integer[] {4922}, new FarmingRegion("Farming Guild", farmingGuildPatches));
    }

    public void loadCurrentConfig() {
        storedData.clear();

        for (Map.Entry<Integer[], FarmingRegion> entry : farmingRegionMap.entrySet()) {
            for (FarmingPatch patch : entry.getValue().getPatches()) {
                String key = entry.getValue().getName() + ":" + patch.getProduce().name() + ":" + patch.getVarbit();
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
    }

    public boolean updateStoredData(WorldPoint location) {
        boolean updated = false;
        final Map<String, Integer> newData = new HashMap<>();

        //log.debug("Region: "+location.getRegionID());
        Optional<Integer[]> farmingRegion_key = farmingRegionMap.keySet().stream().filter(t -> Arrays.asList(t).contains(location.getRegionID())).findFirst();
        if(farmingRegion_key.isPresent()) {
            FarmingRegion entry = farmingRegionMap.get(farmingRegion_key.get());
            //log.debug(entry.getName() + " in range!!!!!");
            if (entry != null) {
                for (FarmingPatch patch : entry.getPatches()) {
                    String key = entry.getName() + ":" + patch.getProduce().name() + ":" + patch.getVarbit();
                    //log.debug(key);
                    int old = storedData.get(key) == null ? -1 : storedData.get(key);
                    int id = client.getVarbitValue(patch.getVarbit().getId());
                    if (id != old) {
                        String patch_name = (patch.getProduce().name().split("_")[0].equals("ALLOTMENT")) ? patch.getProduce().name().split("_")[0]+"_1" : patch.getProduce().name();
                        log.debug( patch_name + ": " + old + " -> " + id);
                        List<PatchType> types = Arrays.asList(PatchType.values()).stream().filter(t -> t.getStage() == id && t.getType().name().equals(patch_name)).collect(Collectors.toList());
                        if (types.stream().count() > 0) {
                            PatchType type = types.stream().findFirst().get();
                            if (old <= 3 && id == type.getStage()) {
                                NotificationRequest request = new NotificationRequest();
                                request.Minutes = type.getMinutes();
                                request.Name = entry.getName() + ":" + patch.getProduce().name() + ":" + type.getName() + ":" + patch.getVarbit();
                                request.RsName = client.getLocalPlayer().getName();
                                request.Type = "Farming";

                                try {
                                    log.debug("Creating notification-" + request.Name);
                                    _scheduledExecutorService.schedule(new Runnable() {
                                        @Override
                                        public void run() {
                                            new RunekitClient(runekitConfig.username(), runekitConfig.token()).createFarmPatchNotification(request);
                                        }
                                    }, 30, TimeUnit.MILLISECONDS);
                                } catch (Exception ex) {
                                    log.debug(ex.getMessage());
                                }
                            }
                        }
                        newData.put(key, id);
                        updated = true;
                    }
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
            try {
                configManager.setRSProfileConfiguration(runekitConfig.CONFIG_GROUP, key, item.getValue());
            }
            catch (Exception ex)
            {}
        }
    }
}
