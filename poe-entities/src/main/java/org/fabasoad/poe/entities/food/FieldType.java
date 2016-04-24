package org.fabasoad.poe.entities.food;

/**
 * @author Yevhen Fabizhevskyi
 * @date 24.04.2016.
 */
public enum FieldType {

    SEED("Seed field", "field_seed"),
    EMPTY("Empty field", "field_empty");

    private final String displayName;
    private final String imageName;

    FieldType(String displayName, String imageName) {
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
        return "fields";
    }
}
