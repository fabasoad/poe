package org.poe.entities.fleet;

import org.sikuli.api.DefaultLocation;
import org.sikuli.api.Location;

/**
 * @author Yevhen Fabizhevskyi
 * @date 07.04.2016.
 */
public enum Fleet {
    BLUE("'Blue' fleet", "fleet_blue"),
    GREEN("'Green' fleet", "fleet_green"),
    RED("'Red' fleet", "fleet_red"),
    PURPLE("'Purple' fleet", "fleet_purple"),
    FREE("Free fleet", "fleet_free");

    private final String displayName;
    private final String imageName;

    Fleet(String displayName, String imageName) {
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
