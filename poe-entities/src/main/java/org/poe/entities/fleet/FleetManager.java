package org.poe.entities.fleet;

import org.poe.Logger;
import org.poe.entities.ButtonType;
import org.poe.entities.ElementsManager;
import org.poe.entities.fleet.exceptions.FleetException;
import org.poe.entities.fleet.exceptions.FleetNotFoundException;
import org.poe.entities.fleet.exceptions.MonsterNotFoundException;
import org.sikuli.script.Location;
import org.sikuli.script.Match;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author Yevhen Fabizhevskyi
 * @date 07.04.2016.
 */
public class FleetManager extends ElementsManager {

    private static final Collection<Location> attackedMonstersList = new ArrayList<>();
    private static final Map<Fleets, Location> attackedMonstersMap = new HashMap<>();

    public static void sendFreeFleets() {
        try {
            sendFleet(Fleets.FREE, true);
        } catch (MonsterNotFoundException e) {
            Optional<Match> matchRandomSectorButton = find(
                    FleetManager.class,
                    "buttons",
                    ButtonType.RANDOM_SECTOR.getDisplayName(),
                    ButtonType.RANDOM_SECTOR.getImageName());
            if (matchRandomSectorButton.isPresent()) {
                attackedMonstersList.clear();
                matchRandomSectorButton.get().click();
            }
        } catch (FleetException ignored) {
        }
    }

    public static void sendAllFleets() {
        while (true) {
            try {
                for (Fleets fleet : Fleets.values()) {
                    if (fleet == Fleets.FREE) {
                        continue;
                    }
                    sendFleet(fleet, false);
                }
            } catch (MonsterNotFoundException e) {
                Optional<Match> matchRandomSectorButton = find(
                        FleetManager.class,
                        "buttons",
                        ButtonType.RANDOM_SECTOR.getDisplayName(),
                        ButtonType.RANDOM_SECTOR.getImageName());
                if (matchRandomSectorButton.isPresent()) {
                    attackedMonstersMap.clear();
                    matchRandomSectorButton.get().click();
                } else {
                    break;
                }
            } catch (FleetException e) {
                break;
            }
        }
    }

    private static void sendFleet(Fleets fleet, boolean isFreeFleet) throws FleetException {
        Predicate<Location> attackedMonstersContains = isFreeFleet
                ? attackedMonstersList::contains
                : attackedMonstersMap.values()::contains;
        BiConsumer<Fleets, Location> attackedMonstersAdd = isFreeFleet
                ? (f, l) -> attackedMonstersList.add(l)
                : attackedMonstersMap::put;
        Consumer<Fleets> attackedMonstersRemove = isFreeFleet
                ? (f) -> {}
                : attackedMonstersMap::remove;

        Optional<Match> matchFleet = find(FleetManager.class, "fleet", fleet.getDisplayName(), fleet.getImageName());
        if (matchFleet.isPresent()) {
            attackedMonstersRemove.accept(fleet);
            matchFleet.get().click();
            Optional<Match> matchMonster = find(
                    FleetManager.class,
                    "monsters",
                    Monsters.BIRD_RA_GREEN.getDisplayName(),
                    Monsters.BIRD_RA_GREEN.getImageName());
            if (matchMonster.isPresent()) {
                Match monster = matchMonster.get();
                if (attackedMonstersContains.test(monster.getTarget())) {
                    Logger.getInstance().info(FleetManager.class, String.format(
                            "Monster '%s' [%s, %s] already attacked.",
                            Monsters.BIRD_RA_GREEN.getDisplayName(),
                            monster.getTarget().getX(),
                            monster.getTarget().getY()));
                } else {
                    attackedMonstersAdd.accept(fleet, monster.getTarget());
                    monster.click();
                    Optional<Match> matchAttackButton = find(
                            FleetManager.class,
                            "buttons",
                            ButtonType.ATTACK.getDisplayName(),
                            ButtonType.ATTACK.getImageName());
                    if (matchAttackButton.isPresent()) {
                        matchAttackButton.get().click();
                    }
                }
            } else {
                throw new MonsterNotFoundException();
            }
        } else {
            if (isFreeFleet) {
                throw new FleetNotFoundException();
            }
        }
    }
}
