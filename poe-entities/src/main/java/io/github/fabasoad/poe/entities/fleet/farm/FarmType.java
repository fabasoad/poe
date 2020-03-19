package io.github.fabasoad.poe.entities.fleet.farm;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum FarmType {
    CLAN("\"Clan\" farm type"),
    RANDOM("\"Random\" farm type");

    private String displayName;

    FarmType(String displayName) {
        this.displayName = displayName;
    }

    public static String getDefaultAsString() {
        return FarmType.RANDOM.name().toLowerCase();
    }

    public static String valuesAsString() {
        return Arrays.stream(values()).map(m -> m.name().toLowerCase()).collect(Collectors.joining(", "));
    }
}
