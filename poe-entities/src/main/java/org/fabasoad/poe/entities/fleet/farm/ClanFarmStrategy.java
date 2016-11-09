package org.fabasoad.poe.entities.fleet.farm;

import org.fabasoad.poe.entities.ElementsManager;
import org.fabasoad.poe.entities.monsters.Monster;

import java.util.Collection;

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

    }
}
