package org.fabasoad.poe.entities.monsters;

import org.fabasoad.poe.entities.resources.ResourceType;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.fabasoad.poe.entities.resources.ResourceType.*;
import static org.fabasoad.poe.entities.monsters.DangerLevel.*;

/**
 * @author Yevhen Fabizhevskyi
 * @date 07.04.2016.
 */
public enum Monster {
    BIRD_RA_1("Bird RA lvl.1", "monster_bird_ra_1", LOW, WOOD),
    BIRD_RA_2("Bird RA lvl.2", "monster_bird_ra_2", LOW, WOOD),
    FISHANGER_1("Fishanger lvl.1", "monster_fishanger_1", LOW, GOLD),
    GIANT_AGLA_KILLER_1("Giant Agla Killer lvl.1", "monster_giant_agla_killer_1", LOW, WOOD),
    CRAB_MUTANT_1("Crab-mutant lvl.1", "monster_crab_mutant_1", LOW, WOOD),
    OGRS_HAND_1("Ogr's hand lvl.1", "monster_ogrs_hand_1", LOW, GOLD),
    FIERCE_QUID_1("Fierce Quid lvl.1", "monster_fierce_quid_1", HIGH, GOLD, WOOD, IRON),
    FIERCE_QUID_2("Fierce Quid lvl.2", "monster_fierce_quid_2", HIGH, GOLD, WOOD, IRON);

    private final String displayName;
    private final String imageName;
    private final DangerLevel dangerLevel;
    private final ResourceType[] resourceTypes;

    Monster(String displayName, String imageName, DangerLevel dangerLevel, ResourceType... resourceTypes) {
        this.displayName = displayName;
        this.imageName = imageName;
        this.dangerLevel = dangerLevel;
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
        Filter filter = new Filter();
        filter.setDangerLevels(LOW);
        filter.setResourceTypes(GOLD);
        return getMonsters(filter);
    }

    public DangerLevel getDangerLevel() {
        return dangerLevel;
    }

    public ResourceType[] getResourceTypes() {
        return resourceTypes;
    }

    public static Collection<Monster> getMonsters(Filter filter) {
        return Arrays.stream(values()).filter(filter::match).collect(Collectors.toList());
    }
}
