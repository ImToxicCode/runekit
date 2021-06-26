package com.runekit.models;

import net.runelite.api.VarPlayer;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BirdHouseTile {
    MEADOW_NORTH("Mushroom Meadow (North)", VarPlayer.BIRD_HOUSE_MEADOW_NORTH),
    MEADOW_SOUTH("Mushroom Meadow (South)", VarPlayer.BIRD_HOUSE_MEADOW_SOUTH),
    VALLEY_NORTH("Verdant Valley (Northeast)", VarPlayer.BIRD_HOUSE_VALLEY_NORTH),
    VALLEY_SOUTH("Verdant Valley (Southwest)", VarPlayer.BIRD_HOUSE_VALLEY_SOUTH);

    private final String name;
    private final VarPlayer varPlayer;
}
