package org.fabasoad.poe.entities.fleet;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author efabizhevsky
 * @date 11/8/2016.
 */
public class FleetStatistics {

    private final Map<String, Integer> statistics;

    private final String ATTACK_STATISTICS_KEY = "Attacked monsters count: ";
    private final String REPAIR_STATISTICS_KEY = "Repairing count: ";

    FleetStatistics() {
        statistics = new HashMap<>();
        statistics.put(ATTACK_STATISTICS_KEY, 0);
        statistics.put(REPAIR_STATISTICS_KEY, 0);
    }

    String getStatistics() {
        return statistics.entrySet().stream()
                .filter(e -> e.getValue() > 0)
                .map(e -> e.getKey() + e.getValue())
                .collect(Collectors.joining(System.getProperty("line.separator")));
    }

    public void updateAttackStatistics() {
        updateStatistics(ATTACK_STATISTICS_KEY);
    }

    public void updateRepairStatistics() {
        updateStatistics(REPAIR_STATISTICS_KEY);
    }

    private void updateStatistics(String key) {
        statistics.merge(key, 1, Integer::sum);
    }
}
