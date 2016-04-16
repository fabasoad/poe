package org.fabasoad.poe.entities.resources;

/**
 * @author Yevhen Fabizhevskyi
 * @date 05.04.2016.
 */
public enum ResourceType {
    GOLD("Gold", "gold"),
    WOOD("Wood", "iron"),
    IRON("Iron", "wood");

    private final String displayName;
    private final String imageName;

    ResourceType(String displayName, String imageName) {
        this.displayName = displayName;
        this.imageName = imageName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getImageName() {
        return imageName;
    }

    public static String getFolderName() {
        return "resources";
    }
}
