package org.fabasoad.poe.entities.buttons;

/**
 * @author Yevhen Fabizhevskyi
 * @date 07.04.2016.
 */
public enum ButtonType {
    OK("'OK' button", "ok_button"),
    RELOAD("'Reload' button", "reload_button"),
    REPEAT("'Repeat' button", "repeat_button"),
    ATTACK("'Attack' button", "attack_button"),
    RANDOM_SECTOR("'Random sector' button", "random_sector_button"),
    REPAIR("'Repair' button", "repair_button"),
    COLLECT_FOOD("'Collect food' button", "collect_food_button"),
    NO_THANKS("'No, thanks' button", "no_thanks_button"),
    MESSAGING("'Messaging' button", "messaging_button");

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

    public static String getFolderName() {
        return "buttons";
    }
}
