package io.github.fabasoad.poe.entities.fleet.farm;

import io.github.fabasoad.poe.entities.ElementsManager;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

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
