package org.fabasoad.poe.entities.fleet;

import org.fabasoad.poe.entities.resources.ResourceType;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.fabasoad.poe.entities.resources.ResourceType.*;

/**
 * @author Yevhen Fabizhevskyi
 * @date 07.04.2016.
 */
public enum Monster {
    BIRD_RA_1("Bird RA lvl.1", "monster_bird_ra_1", WOOD),
    BIRD_RA_2("Bird RA lvl.2", "monster_bird_ra_2", WOOD),
    FISHANGER_1("Fish lvl.1", "monster_fishanger_1", GOLD),
    GIANT_AGLA_KILLER_1("Giant Agla Killer lvl.1", "monster_giant_agla_killer_1", WOOD),
    CRAB_MUTANT_1("Crab-mutant lvl.1", "monster_crab_mutant_1", WOOD),
    FIERCE_QUID_1("Fierce Quid lvl.1", "monster_fierce_quid_1", GOLD, WOOD, IRON);

    private final String displayName;
    private final String imageName;
    private final ResourceType[] resourceTypes;

    Monster(String displayName, String imageName, ResourceType... resourceTypes) {
        this.displayName = displayName;
        this.imageName = imageName;
        this.resourceTypes = resourceTypes;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getImageName() {
        return imageName;
    }

    public static String getFolderName() {
        return "monsters";
    }

    public static String getDefaultAsString() {
        return getDefaultAsCollection().stream().map(Monster::name).collect(Collectors.joining(","));
    }

    public static Collection<Monster> getDefaultAsCollection() {
        return Arrays.asList(Monster.FISHANGER_1, Monster.GIANT_AGLA_KILLER_1);
    }

    public ResourceType[] getResourceTypes() {
        return resourceTypes;
    }

    public static Collection<Monster> getMonstersByResourceType(ResourceType[] resourceTypes) {
        return Arrays.stream(Monster.values()).filter(m -> Arrays.stream(m.getResourceTypes()).anyMatch(r1 ->
                    Arrays.stream(resourceTypes).anyMatch(r2 -> r1 == r2))).collect(Collectors.toList());
    }
}
