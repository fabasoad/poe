package org.fabasoad.poe.entities.validation;

/**
 * @author Yevhen Fabizhevskyi
 * @date 18.04.2016.
 */
public enum SystemElement {

    GAME_TILE("Game tile", "game_tile"),
    WIN_LOGO("'Win' logo", "win_logo");

    private final String displayName;
    private final String imageName;

    SystemElement(String displayName, String imageName) {
        this.displayName = displayName;
        this.imageName = imageName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getImageName() {
        return imageName;
    }

    public static String getFolderName() {
        return "system";
    }
}
