package org.fabasoad.poe.entities.views;

/**
 * @author Yevhen Fabizhevskyi
 * @date 16.04.2016.
 */
public enum ViewType {

    CITY("'City' view", "ocean_view"),
    OCEAN("'Ocean' view", "city_view");

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
}
