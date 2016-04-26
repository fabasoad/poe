package org.fabasoad.poe.entities.temp;

import org.apache.commons.lang3.tuple.Triple;
import org.fabasoad.poe.entities.ElementsManager;

/**
 * @author Yevhen Fabizhevskyi
 * @date 11.04.2016.
 */
public class TempManager extends ElementsManager {

    private static final TempManager instance = new TempManager();

    public static TempManager getInstance() {
        return instance;
    }

    private TempManager() {
    }

    public void test() {
        find(Triple.of("temp", "Temp circle", "circle_01"));
    }
}
