package org.poe.entities.fleet;

/**
 * @author Yevhen Fabizhevskyi
 * @date 07.04.2016.
 */
public enum Monster {
    BIRD_RA_GREEN("Bird RA Green", "monster_bird_ra_green"),
    FISH_1("Fish lvl.1", "monster_fish_1");

    private String displayName;
    private String imageName;

    Monster(String displayName, String imageName) {
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
