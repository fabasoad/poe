package io.github.fabasoad.poe;

import org.sikuli.script.Screen;

public class ScreenInstance {

    private static final Screen screen = new Screen();

    public static Screen get() {
        return screen;
    }
}
