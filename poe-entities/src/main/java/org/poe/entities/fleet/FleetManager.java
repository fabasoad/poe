package org.poe.entities.fleet;

import org.poe.Logger;
import org.poe.entities.ButtonType;
import org.poe.entities.ElementsManager;
import org.poe.entities.fleet.exceptions.FleetException;
import org.poe.entities.fleet.exceptions.FleetNotFoundException;
import org.poe.entities.fleet.exceptions.MonsterNotFoundException;
import org.sikuli.script.Location;
import org.sikuli.script.Match;
import org.sikuli.script.Region;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Yevhen Fabizhevskyi
 * @date 07.04.2016.
 */
public class FleetManager extends ElementsManager {

    private static final Collection<Location> attackedMonstersList = new ArrayList<>();
    private static final Map<Fleet, Location> attackedMonstersMap = new HashMap<>();

    public static void sendFreeFleets() {
        sendFleets(Collections.singletonList(Fleet.FREE));
    }

    public static void sendFleets() {
        sendFleets(Stream.of(Fleet.values()).filter(f -> f != Fleet.FREE).collect(Collectors.toList()));
    }

    private static void sendFleets(Collection<Fleet> fleets) {
        checkIfRepairRequired();

        Runnable clear = fleets.contains(Fleet.FREE) ? attackedMonstersList::clear : attackedMonstersMap::clear;
        try {
            for (Fleet fleet : fleets) {
                sendFleet(fleet);
            }
        } catch (MonsterNotFoundException e) {
            Optional<Match> matchRandomSectorButton = find(
                    FleetManager.class,
                    "buttons",
                    ButtonType.RANDOM_SECTOR.getDisplayName(),
                    ButtonType.RANDOM_SECTOR.getImageName());
            if (matchRandomSectorButton.isPresent()) {
                clear.run();
                matchRandomSectorButton.get().click();
            }
        } catch (FleetException ignored) {
        }
    }

    private static void sendFleet(Fleet fleet) throws FleetException {
        Consumer<Fleet> attackedMonstersRemove = fleet == Fleet.FREE
                ? (f) -> {}
                : attackedMonstersMap::remove;

        Optional<Match> matchFleet = find(FleetManager.class, "fleet", fleet.getDisplayName(), fleet.getImageName());
        if (matchFleet.isPresent()) {
            attackedMonstersRemove.accept(fleet);
            matchFleet.get().click();
            attackMonsters(fleet, Monster.values());
        } else {
            if (fleet == Fleet.FREE) {
                throw new FleetNotFoundException(fleet);
            }
        }
    }

    private static void attackMonsters(Fleet fleet, Monster[] monsters) throws MonsterNotFoundException {
        Predicate<Location> attackedMonstersContains = fleet == Fleet.FREE
                ? attackedMonstersList::contains
                : attackedMonstersMap.values()::contains;
        BiConsumer<Fleet, Location> attackedMonstersAdd = fleet == Fleet.FREE
                ? (f, l) -> attackedMonstersList.add(l)
                : attackedMonstersMap::put;

        int notFoundCount = 0;
        for (Monster monster : monsters) {
            Optional<Match> matchMonster = find(
                    FleetManager.class,
                    "monsters",
                    monster.getDisplayName(),
                    monster.getImageName());
            if (matchMonster.isPresent()) {
                if (attackedMonstersContains.test(matchMonster.get().getTarget())) {
                    Logger.getInstance().info(FleetManager.class, String.format(
                            "Monster '%s' [%s, %s] already attacked.",
                            monster.getDisplayName(),
                            matchMonster.get().getTarget().getX(),
                            matchMonster.get().getTarget().getY()));
                } else {
                    attackedMonstersAdd.accept(fleet, matchMonster.get().getTarget());
                    matchMonster.get().click();
                    clickAttackButton();
                }
            } else {
                notFoundCount++;
                if (notFoundCount == monsters.length) {
                    throw new MonsterNotFoundException(monsters);
                }
            }
        }
    }

    private static void clickAttackButton() {
        find(FleetManager.class,
            "buttons",
            ButtonType.ATTACK.getDisplayName(),
            ButtonType.ATTACK.getImageName()).ifPresent(Region::click);
    }

    private static void checkIfRepairRequired() {
        int counter = Fleet.values().length - 1;
        while (counter > 0) {
            Optional<Match> matchRepairButton =
                    find(FleetManager.class, "fleet", ButtonType.REPAIR.getDisplayName(), ButtonType.REPAIR.getImageName());
            if (matchRepairButton.isPresent()) {
                matchRepairButton.get().click();
                counter--;
            } else {
                break;
            }
        }
    }
}
