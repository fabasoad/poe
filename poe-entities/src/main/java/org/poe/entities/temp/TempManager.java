package org.poe.entities.temp;

import org.poe.entities.ElementsManager;

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
        find("temp", "Temp circle", "circle_01");
    }
}
