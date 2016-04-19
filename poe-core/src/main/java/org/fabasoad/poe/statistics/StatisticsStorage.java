package org.fabasoad.poe.statistics;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Yevhen Fabizhevskyi
 * @date 19.04.2016.
 */
public final class StatisticsStorage {

    private static final StatisticsStorage instance = new StatisticsStorage();

    public static StatisticsStorage getInstance() {
        return instance;
    }

    private StatisticsStorage() {
    }

    private final Set<SupportedStatistics> elements = new HashSet<>();

    public <T extends SupportedStatistics> void add(T element) {
        elements.add(element);
    }

    public void print() {
        System.out.println();
        System.out.println("=============== Statistics ===============");
        elements.forEach(e -> System.out.println(e.getResults()));
    }
}
