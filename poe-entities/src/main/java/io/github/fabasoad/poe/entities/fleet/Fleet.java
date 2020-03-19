package io.github.fabasoad.poe.entities.fleet;

import org.apache.commons.lang3.tuple.Triple;

public enum Fleet {
    BLUE("'Blue' fleet", "fleet_blue"),
    GREEN("'Green' fleet", "fleet_green"),
    RED("'Red' fleet", "fleet_red"),
    PURPLE("'Purple' fleet", "fleet_purple"),
    FREE("Free fleet", "fleet_free");

    private static final String FLEET_FOLDER = "fleet";
    private final String displayName;
    private final String imageName;

    Fleet(String displayName, String imageName) {
        this.displayName = displayName;
        this.imageName = imageName;
    }

    public Triple<String, String, String> asElement() {
        return Triple.of(FLEET_FOLDER, displayName, imageName);
    }
}
