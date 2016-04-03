package org.poe.entities;

/**
 * @author Yevhen Fabizhevskyi
 * @date 02.04.2016.
 */
public class ScreenElementDescriptor {

    private final String displayName;
    private final String imageName;

    public ScreenElementDescriptor(String displayName, String imageName) {
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
