package com.runekit.models;

import lombok.Getter;
import net.runelite.api.Varbits;

@Getter
public class FarmingPatch {
    private final Varbits varbit;
    private final FarmingProduce produce;

    public FarmingPatch(Varbits varbit, FarmingProduce produce) {
        this.varbit = varbit;
        this.produce = produce;
    }
}
