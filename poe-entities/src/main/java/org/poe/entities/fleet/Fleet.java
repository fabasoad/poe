package org.poe.entities.fleet;

import org.sikuli.api.DefaultLocation;
import org.sikuli.api.Location;

/**
 * @author Yevhen Fabizhevskyi
 * @date 07.04.2016.
 */
public enum Fleet {
    BLUE("'Blue' fleet", "fleet_blue", new DefaultLocation(485, 675)),
    GREEN("'Green' fleet", "fleet_green", new DefaultLocation(617, 675)),
    RED("'Red' fleet", "fleet_red", new DefaultLocation(516, 667)),
    PURPLE("'Purple' fleet", "fleet_purple", new DefaultLocation(516, 667)),
    FREE("Free fleet", "fleet_free", new DefaultLocation(0, 0));

    private String displayName;
    private String imageName;
    private Location location;

    Fleet(String displayName, String imageName, Location location) {
        this.displayName = displayName;
        this.imageName = imageName;
        this.location = location;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getImageName() {
        return imageName;
    }

    public Location getLocation() {
        return location;
    }
}
