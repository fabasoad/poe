package org.fabasoad.poe.entities.buttons;

import org.fabasoad.poe.entities.ElementsManager;
import org.sikuli.script.Region;

/**
 * @author Eugene Fabizhevsky
 * @date 4/15/2016
 */
public class ButtonsManager extends ElementsManager {

    private static final ButtonsManager instance = new ButtonsManager();

    public static ButtonsManager getInstance() {
        return instance;
    }

    private ButtonsManager() {
    }

    public void click(ButtonType buttonType) {
        click(buttonType, () -> {});
    }

    public void click(ButtonType buttonType, Runnable postClick) {
        find("buttons", buttonType.getDisplayName(), buttonType.getImageName()).ifPresent(b -> {
            b.click();
            postClick.run();
        });
    }

    public void clickMany(ButtonType buttonType) {
        findAll("buttons", buttonType.getDisplayName(), buttonType.getImageName())
                .ifPresent(i -> i.forEachRemaining(Region::click));
    }
}
