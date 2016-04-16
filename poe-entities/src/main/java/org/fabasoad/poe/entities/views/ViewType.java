package org.fabasoad.poe.entities.views;

/**
 * @author Yevhen Fabizhevskyi
 * @date 16.04.2016.
 */
public enum ViewType {

    CITY("'City' view", "city_view"),
    FLEET("'Fleet' view", "fleet_view");

    private String displayName;
    private String imageName;

    ViewType(String displayName, String imageName) {
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
        return "views";
    }
}
