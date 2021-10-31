package com.runekit.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PatchType {
    POTATO("Potato", FarmingProduce.ALLOTMENT_1, 6, 4 * 10),
    ONION("Onion", FarmingProduce.ALLOTMENT_1, 13, 4 * 10),
    CABBAGE("Cabbage", FarmingProduce.ALLOTMENT_1, 20, 4 * 10),
    TOMATO("Tomato", FarmingProduce.ALLOTMENT_1, 27, 4 * 10),
    SWEETCORN("Sweetcorn", FarmingProduce.ALLOTMENT_1, 34, 6 * 10),
    STRAWBERRY("Strawberry", FarmingProduce.ALLOTMENT_1, 43, 6 * 10),
    WATERMELON("Watermelon", FarmingProduce.ALLOTMENT_1, 52, 8 * 10),
    SNAPE_GRASS("Snape grass", FarmingProduce.ALLOTMENT_1, 128, 7 * 10),

    MARIGOLD("Marigold", FarmingProduce.FLOWER, 8, 4 * 5),
    ROSEMARY("Rosemary", FarmingProduce.FLOWER, 13, 4 * 5),
    NASTURTIUM("Nasturtium", FarmingProduce.FLOWER, 18, 4 * 5),
    WOAD_LEAF("Woad leaf", FarmingProduce.FLOWER, 23, 4 * 5),
    LIMPWURT("Limpwurt", FarmingProduce.FLOWER, 28, 4 * 5),
    WHITE_LILY("White lily", FarmingProduce.FLOWER, 37, 4 * 5),

    GUAM("Guam", FarmingProduce.HERB, 4, 4 * 20),
    MARRENTILL("Marrentill", FarmingProduce.HERB, 11, 4 * 20),
    TARROMIN("Tarromin", FarmingProduce.HERB, 18, 4 * 20),
    HARRALANDER("Harralander", FarmingProduce.HERB, 25, 4 * 20),
    RANARR("Ranarr", FarmingProduce.HERB, 32, 4 * 20),
    TOADFLAX("Toadflax", FarmingProduce.HERB, 39, 4 * 20),
    IRIT("Irit", FarmingProduce.HERB, 46, 4 * 20),
    AVANTOE("Avantoe", FarmingProduce.HERB, 53, 4 * 20),
    KWUARM("Kwuarm", FarmingProduce.HERB, 68, 4 * 20),
    SNAPDRAGON("Snapdragon", FarmingProduce.HERB, 75, 4 * 20),
    CADANTINE("Cadantine", FarmingProduce.HERB, 82, 4 * 20),
    LANTADYME("Lantadyme", FarmingProduce.HERB, 89, 4 * 20),
    DWARF_WEED("Dwarf weed", FarmingProduce.HERB, 96, 4 * 20),
    TORSTOL("Torsol", FarmingProduce.HERB, 103, 4 * 20),

    BARLEY("Barley", FarmingProduce.HOPS, 49, 4 * 10),
    HAMMERSTONE("Hammerstone", FarmingProduce.HOPS, 4, 4 * 10),
    ASGARNIAN("Asgarnian", FarmingProduce.HOPS, 11, 5 * 10),
    JUTE("Jute", FarmingProduce.HOPS, 56, 5 * 10),
    YANILLIAN("Yanillian", FarmingProduce.HOPS, 19, 6 * 10),
    KRANDORIAN("Krandorian", FarmingProduce.HOPS, 28, 7 * 10),
    WILDBLOOD("Wildblood", FarmingProduce.HOPS, 38, 8 * 10),

    ACORN("Oak tree", FarmingProduce.TREE, 8, 5 * 40),
    WILLOW("Willow tree", FarmingProduce.TREE, 15, 7 * 40),
    MAPLE("Maple tree", FarmingProduce.TREE, 24, 8 * 40),
    YEW("Yew tree", FarmingProduce.TREE, 35, 10 * 40),
    MAGIC("Magic tree", FarmingProduce.TREE, 48, 12 * 40),

    REDBERRY("Redberry", FarmingProduce.BUSH, 5, 5 * 20),
    CADAVABERRY("Vadavaberry", FarmingProduce.BUSH, 15, 6 * 20),
    DWELLBERRY("Dwellberry", FarmingProduce.BUSH, 26, 7 * 20),
    WHITEBERRY("Whiteberry", FarmingProduce.BUSH, 38, 8 * 20),
    JANGERBERRY("Jangerberry", FarmingProduce.BUSH, 51, 8 * 20),
    POISON_IVY_BERRY("Poison ivy berry", FarmingProduce.BUSH, 197, 8 * 20),

    APPLE("Apple tree", FarmingProduce.FRUIT_TREE, 8, 6 * 160),
    BANANA("Banana tree", FarmingProduce.FRUIT_TREE, 35, 6 * 160),
    ORANGE("Orange tree", FarmingProduce.FRUIT_TREE, 72, 6 * 160),
    CURRY("Curry tree", FarmingProduce.FRUIT_TREE, 99, 6 * 160),
    PINEAPPLE("Pineapple tree", FarmingProduce.FRUIT_TREE, 136, 6 * 160),
    PAPAYA("Papaya tree", FarmingProduce.FRUIT_TREE, 163, 6 * 160),
    PALM("Palm tree", FarmingProduce.FRUIT_TREE, 200, 6 * 160),
    DRAGONFRUIT("Dragonfruit tree", FarmingProduce.FRUIT_TREE, 227, 6 * 160),

    KRONOS("Kronos", FarmingProduce.ANIMA, 26, 8 * 640),
    IASOR("Iasor", FarmingProduce.ANIMA, 17, 8 * 640),
    ATTAS("Attas", FarmingProduce.ANIMA, 8, 8 * 640),

    CACTUS("Cactus", FarmingProduce.CACTUS, 8, 7 * 80),
    POTATO_CACTUS("Potato Cactus", FarmingProduce.CACTUS, 32, 7 * 10),

    GIANT_SEAWEED("Giant Seaweed", FarmingProduce.SEAWEED, 4, 4 * 10),

    GRAPES("Grapes", FarmingProduce.GRAPES, 2, 7 * 5),

    HESPORI("Hespori", FarmingProduce.HESPORI, 4, 3 * 640),

    CALQUAT("Calquat tree", FarmingProduce.CALQUAT, 4, 8 * 160),
    CELASTRUS("Celastrus tree", FarmingProduce.CELASTRUS, 8, 5 * 160),
    TEAK("Teak tree", FarmingProduce.HARDWOOD_1, 15, 7 * 640),
    MAHOGANY("Mahogany tree", FarmingProduce.HARDWOOD_1, 30, 8 * 640),

    CRYSTAL("Crystal tree", FarmingProduce.CRYSTAL, 8, 6 * 80),

    SPIRIT("Spirit tree", FarmingProduce.SPIRIT, 8, 12 * 320),

    REDWOOD("Redwood tree", FarmingProduce.REDWOOD, 8, 10 * 640);


    private final String name;
    private final FarmingProduce type;
    private final int stage;
    private final int minutes;
}
