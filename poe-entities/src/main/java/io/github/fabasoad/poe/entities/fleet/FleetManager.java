package io.github.fabasoad.poe.entities.fleet;

import io.github.fabasoad.annotations.UsedViaReflection;
import io.github.fabasoad.poe.entities.fleet.farm.FarmType;
import io.github.fabasoad.poe.entities.fleet.farm.FarmStrategy;
import io.github.fabasoad.poe.entities.fleet.farm.FarmStrategyFactory;
import io.github.fabasoad.poe.entities.monsters.Monster;
import io.github.fabasoad.poe.entities.views.ViewAwareElementsManager;
import io.github.fabasoad.poe.entities.views.ViewType;
import io.github.fabasoad.poe.statistics.SupportedStatistics;

import java.util.Collection;

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
