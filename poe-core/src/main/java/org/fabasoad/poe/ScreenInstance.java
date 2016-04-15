package org.fabasoad.poe;

import org.sikuli.script.Screen;

/**
 * @author Yevhen Fabizhevskyi
 * @date 05.04.2016.
 */
public class ScreenInstance {

    private static final Screen screen = new Screen();

    public static Screen get() {
        return screen;
    }
}
