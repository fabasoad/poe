package org.fabasoad.poe.entities;

/**
 * @author Yevhen Fabizhevskyi
 * @date 16.04.2016.
 */
public enum ViewType {

    NONE(null, null),
    CITY("'City' view", "fleet_view"),
    OCEAN("'Fleet' view", "city_view");

    private String displayName;
    private String imageToGoName;

    ViewType(String displayName, String imageToGoName) {
        this.displayName = displayName;
        this.imageToGoName = imageToGoName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getImageToGoName() {
        return imageToGoName;
    }

    public static String getFolderName() {
        return "views";
    }

    public boolean isNone() {
        return this == ViewType.NONE;
    }
}
