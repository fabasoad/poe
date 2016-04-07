package org.poe.entities.fleet;

/**
 * @author Yevhen Fabizhevskyi
 * @date 07.04.2016.
 */
enum Fleets {
    BLUE("'Blue' fleet", "fleet_blue"),
    GREEN("'Green' fleet", "fleet_green"),
    RED("'Red' fleet", "fleet_red"),
    PURPLE("'Purple' fleet", "fleet_purple"),
    FREE("Free fleet", "fleet_free");

    private String displayName;
    private String imageName;

    Fleets(String displayName, String imageName) {
        this.displayName = displayName;
        this.imageName = imageName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getImageName() {
        return imageName;
    }
}
