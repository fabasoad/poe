package org.fabasoad.poe.entities.fleet.farm;

import org.fabasoad.poe.entities.ElementsManager;
import org.fabasoad.poe.entities.buttons.ButtonType;
import org.fabasoad.poe.entities.buttons.ButtonsManager;
import org.fabasoad.poe.entities.fleet.Fleet;
import org.fabasoad.poe.entities.fleet.FleetStatistics;
import org.fabasoad.poe.entities.monsters.Monster;
import org.sikuli.script.Match;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author efabizhevsky
 * @date 11/8/2016.
 */
class RandomFarmStrategy extends FarmStrategy {

    RandomFarmStrategy(ElementsManager elementsManager) {
        super(elementsManager);
    }

    @Override
    public void sendFleets(Collection<Monster> monsters) {
        Collection<Match> foundMonsters = new ArrayList<>();
        findAllMonsters(monsters).ifPresent(i -> i.forEachRemaining(foundMonsters::add));

        elementsManager.findAll(Fleet.FREE.asElement()).ifPresent(fleetsIterator -> {
            if (foundMonsters.isEmpty()) {
                ButtonsManager.getInstance().click(ButtonType.RANDOM_SECTOR, () -> sendFleets(monsters));
            } else {
                // Check if repair required
                ButtonsManager.getInstance().clickMany(
                        ButtonType.REPAIR,
                        () -> statistics.ifPresent(FleetStatistics::updateRepairStatistics));

                Iterator<Match> monstersIterator = foundMonsters.iterator();
                while (fleetsIterator.hasNext() && monstersIterator.hasNext()) {
                    attack(fleetsIterator.next(), monstersIterator.next());
                }
            }
        });
    }
}
