package org.fabasoad.poe.entities.buttons;

import org.fabasoad.poe.entities.ElementsManager;
import org.sikuli.script.Match;
import org.sikuli.script.Region;

import java.util.Optional;

/**
 * @author Eugene Fabizhevsky
 * @date 4/15/2016
 */
public final class ButtonsManager extends ElementsManager {

    private static final ButtonsManager instance = new ButtonsManager();

    public static ButtonsManager getInstance() {
        return instance;
    }

    private ButtonsManager() {
    }

    public Optional<Match> find(ButtonType buttonType) {
        return find(ButtonType.getFolderName(), buttonType.getDisplayName(), buttonType.getImageName());
    }

    public void click(ButtonType buttonType) {
        click(buttonType, () -> {});
    }

    public void click(ButtonType buttonType, Runnable postClick) {
        find(ButtonType.getFolderName(), buttonType.getDisplayName(), buttonType.getImageName()).ifPresent(b -> {
            b.click();
            postClick.run();
        });
    }

    public void clickMany(ButtonType buttonType) {
        findAll(ButtonType.getFolderName(), buttonType.getDisplayName(), buttonType.getImageName())
                .ifPresent(i -> i.forEachRemaining(Region::click));
    }
}
