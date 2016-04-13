package org.poe.entities.fleet;

import com.google.common.collect.Iterators;
import org.poe.entities.ButtonType;
import org.poe.entities.ElementsManager;
import org.sikuli.script.Match;
import org.sikuli.script.Region;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

/**
 * @author Yevhen Fabizhevskyi
 * @date 07.04.2016.
 */
public class FleetManager extends ElementsManager {

    private static final FleetManager instance = new FleetManager();

    public static FleetManager getInstance() {
        return instance;
    }

    private FleetManager() {
    }

    private final Collection<Match> attackedMonsters = new ArrayList<>();

    public void sendFleets(Collection<Monster> monsters) {
        checkIfRepairRequired();

        Collection<Match> foundMonsters = new ArrayList<>();
        findAllMonsters(monsters).ifPresent(monstersIterator -> {
            while (monstersIterator.hasNext()) {
                Match matchMonster = monstersIterator.next();
                if (!attackedMonsters.contains(matchMonster)) {
                    foundMonsters.add(matchMonster);
                }
            }
        });

        if (foundMonsters.isEmpty()) {
            findRandomSectorButton().ifPresent(Region::click);
        } else {
            findFreeFleets().ifPresent(fleetsIterator -> {
                attackedMonsters.clear();
                Iterator<Match> monstersIterator = foundMonsters.iterator();
                while (fleetsIterator.hasNext() && monstersIterator.hasNext()) {
                    attack(fleetsIterator.next(), monstersIterator.next());
                }
            });
        }
    }

    private void attack(Match fleet, Match monster) {
        attackedMonsters.add(monster);

        fleet.click();
        monster.click();
        clickAttackButton();
    }

    private Optional<Iterator<Match>> findAllMonsters(Collection<Monster> monsters) {
        @SuppressWarnings("unchecked")
        final Iterator<Match>[] result = new Iterator[1];
        for (Monster monster : monsters) {
            findAll("monsters", monster.getDisplayName(), monster.getImageName()).ifPresent(iterator ->
                    result[0] = result[0] == null ? iterator : Iterators.concat(result[0], iterator));
        }
        return Optional.ofNullable(result[0]);
    }

    private Optional<Iterator<Match>> findFreeFleets() {
        return findAll("fleet", Fleet.FREE.getDisplayName(), Fleet.FREE.getImageName());
    }

    private Optional<Match> findRandomSectorButton() {
        return find("buttons", ButtonType.RANDOM_SECTOR.getDisplayName(), ButtonType.RANDOM_SECTOR.getImageName());
    }

    private void clickAttackButton() {
        find("buttons",
            ButtonType.ATTACK.getDisplayName(),
            ButtonType.ATTACK.getImageName()).ifPresent(Region::click);
    }

    private void checkIfRepairRequired() {
        findAll("fleet", ButtonType.REPAIR.getDisplayName(), ButtonType.REPAIR.getImageName()).ifPresent(iterator -> {
            while (iterator.hasNext()) {
                iterator.next().click();
            }
        });
    }
}
