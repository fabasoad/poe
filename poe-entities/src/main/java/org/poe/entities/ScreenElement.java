package org.poe.entities;

/**
 * @author Yevhen Fabizhevskyi
 * @date 02.04.2016.
 */
public abstract class ScreenElement {

    private final String displayName;
    private final String imagePath;

    public ScreenElement(String displayName, String imagePath) {
        this.displayName = displayName;
        this.imagePath = imagePath;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getImagePath() {
        return imagePath;
    }
}
