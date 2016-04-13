package org.poe.entities.fleet;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Yevhen Fabizhevskyi
 * @date 07.04.2016.
 */
public enum Monster {
    BIRD_RA_1("Bird RA lvl.1", "monster_bird_ra_1"),
    FISHANGER_1("Fish lvl.1", "monster_fishanger_1"),
    GIANT_AGLA_KILLER_1("Giant Agla Killer lvl.1", "monster_giant_agla_killer_1");

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

    public static String getDefaultAsString() {
        return getDefaultAsCollection().stream().map(Monster::name).collect(Collectors.joining(","));
    }

    public static Collection<Monster> getDefaultAsCollection() {
        return Arrays.asList(Monster.FISHANGER_1, Monster.GIANT_AGLA_KILLER_1);
    }
}
