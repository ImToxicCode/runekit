package com.runekit.models;

import lombok.Getter;
import java.util.List;

@Getter
public class FarmingRegion {
    private final String name;
    private final List<FarmingPatch> patches;

    public FarmingRegion(String name, List<FarmingPatch> patches) {
        this.name = name;
        this.patches = patches;
    }
}
