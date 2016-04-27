package org.fabasoad.poe.entities.buttons;

import org.fabasoad.poe.entities.ElementsManager;

import java.util.Collection;
import java.util.stream.Collectors;

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

    public void click(ButtonType buttonType) {
        click(buttonType, () -> {});
    }

    public void click(ButtonType buttonType, Runnable postClick) {
        find(buttonType.asElement()).ifPresent(b -> {
            b.click();
            postClick.run();
        });
    }

    public void clickMany(Collection<ButtonType> buttonType, Runnable postClick) {
        findAll(buttonType.stream().map(ButtonType::asElement).collect(Collectors.toList())).ifPresent(i -> {
            while (i.hasNext()) {
                i.next().click();
                postClick.run();
            }
        });
    }

    public void clickMany(ButtonType buttonType, Runnable postClick) {
        findAll(buttonType.asElement()).ifPresent(i -> {
            while (i.hasNext()) {
                i.next().click();
                postClick.run();
            }
        });
    }
}
