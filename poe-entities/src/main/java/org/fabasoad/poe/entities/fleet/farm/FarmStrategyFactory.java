package org.fabasoad.poe.entities.fleet.farm;

import org.fabasoad.poe.entities.ElementsManager;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author efabizhevsky
 * @date 11/8/2016.
 */
public class FarmStrategyFactory {

    private static Map<FarmType, Function<ElementsManager, FarmStrategy>> strategies = new HashMap<>();

    static {
        strategies.put(FarmType.CLAN, ClanFarmStrategy::new);
        strategies.put(FarmType.RANDOM, RandomFarmStrategy::new);
    }

    public static FarmStrategy getStrategy(FarmType farmType, ElementsManager elementsManager) {
        return strategies.get(farmType).apply(elementsManager);
    }
}
