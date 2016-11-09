package org.fabasoad.poe.entities.fleet;

import org.fabasoad.poe.core.UsedViaReflection;
import org.fabasoad.poe.entities.fleet.farm.FarmType;
import org.fabasoad.poe.entities.fleet.farm.FarmStrategy;
import org.fabasoad.poe.entities.fleet.farm.FarmStrategyFactory;
import org.fabasoad.poe.entities.monsters.Monster;
import org.fabasoad.poe.entities.views.ViewAwareElementsManager;
import org.fabasoad.poe.entities.views.ViewType;
import org.fabasoad.poe.statistics.SupportedStatistics;

import java.util.Collection;

/**
 * @author Yevhen Fabizhevskyi
 * @date 07.04.2016.
 */
@SupportedStatistics
public final class FleetManager extends ViewAwareElementsManager {

    private static final FleetManager instance = new FleetManager();

    @UsedViaReflection
    public static FleetManager getInstance() {
        return instance;
    }

    private FleetManager() {
    }

    private final FleetStatistics statistics = new FleetStatistics();

    @Override
    protected ViewType getViewType() {
        return ViewType.OCEAN;
    }

    @UsedViaReflection
    public String getStatistics() {
        return statistics.getStatistics();
    }

    public void sendFleets(FarmType farmType, Collection<Monster> monsters) {
        // Try switching view to 'OCEAN'
        trySwitchView();
        // Send fleets to the journey
        FarmStrategy strategy = FarmStrategyFactory.getStrategy(farmType, this);
        strategy.attachStatistics(statistics);
        strategy.sendFleets(monsters);
    }
}
