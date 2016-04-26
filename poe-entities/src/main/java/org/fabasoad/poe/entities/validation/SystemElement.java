package org.fabasoad.poe.entities.validation;

import org.apache.commons.lang3.tuple.Triple;

/**
 * @author Yevhen Fabizhevskyi
 * @date 18.04.2016.
 */
enum SystemElement {

    GAME_TILE("Game tile", "game_tile"),
    WIN_LOGO("'Win' logo", "win_logo");

    public static final String SYSTEM_FOLDER = "system";
    private final String displayName;
    private final String imageName;

    SystemElement(String displayName, String imageName) {
        this.displayName = displayName;
        this.imageName = imageName;
    }

    public Triple<String, String, String> asElement() {
        return Triple.of(SYSTEM_FOLDER, displayName, imageName);
    }
}
