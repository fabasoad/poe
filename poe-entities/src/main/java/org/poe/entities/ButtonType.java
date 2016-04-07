package org.poe.entities;

/**
 * @author Yevhen Fabizhevskyi
 * @date 07.04.2016.
 */
public enum ButtonType {
    RELOAD("'Reload' button", "reload_button"),
    REPEAT("'Repeat' button", "repeat_button"),
    ATTACK("'Attack' button", "attack_button"),
    RANDOM_SECTOR("'Random sector' button", "random_sector_button");

    private String displayName;
    private String imageName;

    ButtonType(String displayName, String imageName) {
        this.displayName = displayName;
        this.imageName = imageName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getImageName() {
        return imageName;
    }
}
