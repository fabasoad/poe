package org.poe.entities.fleet;

/**
 * @author Yevhen Fabizhevskyi
 * @date 07.04.2016.
 */
enum Monsters {
    BIRD_RA_GREEN("Bird RA Green", "monster_bird_ra_green");

    private String displayName;
    private String imageName;

    Monsters(String displayName, String imageName) {
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
