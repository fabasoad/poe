package org.fabasoad.poe.entities.fleet.farm;

import org.fabasoad.poe.entities.ElementsManager;
import org.fabasoad.poe.entities.buttons.ButtonsManager;
import org.fabasoad.poe.entities.fleet.Fleet;
import org.fabasoad.poe.entities.fleet.FleetStatistics;
import org.fabasoad.poe.entities.monsters.Monster;
import org.sikuli.script.Match;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import static org.fabasoad.poe.entities.buttons.ButtonType.CLAN;
import static org.fabasoad.poe.entities.buttons.ButtonType.ANY_CLANMATE;
import static org.fabasoad.poe.entities.buttons.ButtonType.VISIT;
import static org.fabasoad.poe.entities.buttons.ButtonType.REPAIR;

/**
 * @author efabizhevsky
 * @date 11/8/2016.
 */
class ClanFarmStrategy extends FarmStrategy {

    ClanFarmStrategy(ElementsManager elementsManager) {
        super(elementsManager);
    }

    @Override
    public void sendFleets(Collection<Monster> monsters) {
        Collection<Match> foundMonsters = new ArrayList<>();
        findAllMonsters(monsters).ifPresent(i -> i.forEachRemaining(foundMonsters::add));

        elementsManager.findAll(Fleet.FREE.asElement()).ifPresent(fleetsIterator -> {
            if (foundMonsters.isEmpty()) {
                ButtonsManager.getInstance().click(
                        CLAN, () -> ButtonsManager.getInstance().click(
                                ANY_CLANMATE, () -> ButtonsManager.getInstance().click(
                                        VISIT, () -> sendFleets(monsters))));
            } else {
                // Check if repair required
                ButtonsManager.getInstance().clickMany(REPAIR,
                        () -> statistics.ifPresent(FleetStatistics::updateRepairStatistics));

                Iterator<Match> monstersIterator = foundMonsters.iterator();
                while (fleetsIterator.hasNext() && monstersIterator.hasNext()) {
                    attack(fleetsIterator.next(), monstersIterator.next());
                }
            }
        });
    }
}
