package io.github.fabasoad.poe.entities.monsters;

import org.apache.commons.lang3.tuple.Triple;
import io.github.fabasoad.poe.entities.resources.ResourceType;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static io.github.fabasoad.poe.entities.resources.ResourceType.*;
import static io.github.fabasoad.poe.entities.monsters.DangerLevel.*;

public enum Monster {
    BIRD_RA_1("Bird RA lvl.1", "monster_bird_ra_1", VERY_LOW, WOOD),
    BIRD_RA_2("Bird RA lvl.2", "monster_bird_ra_2", LOW, WOOD),
    BIRD_RA_3("Bird RA lvl.3", "monster_bird_ra_3", MEDIUM, WOOD),
    FISHANGER_1("Fishanger lvl.1", "monster_fishanger_1", LOW, GOLD),
    JELLY_PIG_1("Jelly Pig lvl.1", "monster_jelly_pig_1", MEDIUM, WOOD),
    JELLY_PIG_2("Jelly Pig lvl.2", "monster_jelly_pig_2", HIGH, WOOD),
    JELLY_PIG_3("Jelly Pig lvl.3", "monster_jelly_pig_3", VERY_HIGH, WOOD),
    KAPPA_1("Kappa lvl.1", "monster_kappa_1", LOW, IRON),
    GIANT_AGLA_KILLER_1("Giant Agla Killer lvl.1", "monster_giant_agla_killer_1", VERY_LOW, WOOD),
    GIANT_AGLA_KILLER_2("Giant Agla Killer lvl.2", "monster_giant_agla_killer_2", LOW, WOOD),
    GIANT_AGLA_KILLER_3("Giant Agla Killer lvl.3", "monster_giant_agla_killer_3", HIGH, WOOD),
    BEAKLIMB_1("Beaklimb lvl.1", "monster_beaklimb_1", MEDIUM, GOLD),
    BEAKLIMB_2("Beaklimb lvl.2", "monster_beaklimb_2", HIGH, GOLD),
    CRAB_MUTANT_1("Crab-mutant lvl.1", "monster_crab_mutant_1", LOW, WOOD),
    OGRS_HAND_1("Ogr's Hand lvl.1", "monster_ogrs_hand_1", LOW, GOLD),
    FIERCE_QUID_1("Fierce Quid lvl.1", "monster_fierce_quid_1", HIGH, GOLD, WOOD, IRON),
    FIERCE_QUID_2("Fierce Quid lvl.2", "monster_fierce_quid_2", VERY_HIGH, GOLD, WOOD, IRON);

    private static final String MONSTERS_FOLDER = "monsters";
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

    public Triple<String, String, String> asElement() {
        return Triple.of(MONSTERS_FOLDER, displayName, imageName);
    }

    public static String defaultAsString() {
        return defaultAsCollection().stream().map(m -> m.name().toLowerCase()).collect(Collectors.joining(", "));
    }

    public static String valuesAsString() {
        return Arrays.stream(values()).map(m -> m.name().toLowerCase()).collect(Collectors.joining(", "));
    }

    private static Collection<Monster> defaultAsCollection() {
        Filter filter = new Filter();
        filter.setDangerLevels(LOW);
        filter.setResourceTypes(GOLD, IRON);
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
