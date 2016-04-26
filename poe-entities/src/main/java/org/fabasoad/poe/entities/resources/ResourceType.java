package org.fabasoad.poe.entities.resources;

import org.apache.commons.lang3.tuple.Triple;

/**
 * @author Yevhen Fabizhevskyi
 * @date 05.04.2016.
 */
public enum ResourceType {
    GOLD("Gold", "gold"),
    WOOD("Wood", "iron"),
    IRON("Iron", "wood");

    public static final String RESOURCES_FOLDER = "resources";
    private final String displayName;
    private final String imageName;

    ResourceType(String displayName, String imageName) {
        this.displayName = displayName;
        this.imageName = imageName;
    }

    public Triple<String, String, String> asElement() {
        return Triple.of(RESOURCES_FOLDER, displayName, imageName);
    }
}
