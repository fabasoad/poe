package org.fabasoad.poe.entities.fleet.farm;

import org.fabasoad.poe.entities.ElementsManager;
import org.fabasoad.poe.entities.fleet.FleetStatistics;
import org.fabasoad.poe.entities.monsters.Monster;

import java.util.Collection;
import java.util.Optional;

/**
 * @author efabizhevsky
 * @date 11/8/2016.
 */
public abstract class FarmStrategy {

    final ElementsManager elementsManager;
    Optional<FleetStatistics> statistics;

    FarmStrategy(ElementsManager elementsManager) {
        this.elementsManager = elementsManager;
    }

    public abstract void sendFleets(Collection<Monster> monsters);

    public void attachStatistics(FleetStatistics statistics) {
        this.statistics = Optional.ofNullable(statistics);
    }
}
