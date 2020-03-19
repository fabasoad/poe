package io.github.fabasoad.poe.entities.temp;

import org.apache.commons.lang3.tuple.Triple;
import io.github.fabasoad.poe.entities.ElementsManager;

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
