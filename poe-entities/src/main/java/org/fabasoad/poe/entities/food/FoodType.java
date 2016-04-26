package org.fabasoad.poe.entities.food;

import org.apache.commons.lang3.tuple.Triple;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * @author Yevhen Fabizhevskyi
 * @date 05.04.2016.
 */
public enum FoodType {
    CARROT("Carrot", "collect_carrot", "grow_carrot"),
    CABBAGE("Cabbage", "collect_cabbage", "grow_cabbage"),
    GRAPE("Grape", "collect_grape", "grow_grape"),
    WHEAT("Wheat", "collect_wheat", "grow_wheat"),
    RICE("Rice", "collect_rice", "grow_rice"),
    OLIVES("Olives", "collect_olives", "grow_olives");

    public static final String FOOD_FOLDER = "food";
    private final String displayName;
    private final String collectImageName;
    private final String growImageName;

    FoodType(String displayName, String collectImageName, String growImageName) {
        this.displayName = displayName;
        this.collectImageName = collectImageName;
        this.growImageName = growImageName;
    }

    public Triple<String, String, String> asElementToCollect() {
        return Triple.of(FOOD_FOLDER, displayName, collectImageName);
    }

    public Triple<String, String, String> asElementToGrow() {
        return Triple.of(FOOD_FOLDER, displayName, growImageName);
    }

    public static String defaultToCollectAsString() {
        return defaultToCollectAsCollection().stream().map(m -> m.name().toLowerCase()).collect(Collectors.joining(", "));
    }

    private static Collection<FoodType> defaultToCollectAsCollection() {
        return Collections.singletonList(CARROT);
    }

    public static String defaultToGrowAsString() {
        return defaultToGrow().name().toLowerCase();
    }

    private static FoodType defaultToGrow() {
        return CARROT;
    }

    public static String valuesAsString() {
        return Arrays.stream(values()).map(m -> m.name().toLowerCase()).collect(Collectors.joining(", "));
    }
}
