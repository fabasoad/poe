package io.github.fabasoad.poe.entities.buttons;

import io.github.fabasoad.poe.entities.ElementsManager;
import org.sikuli.script.Match;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public void clickAny(ButtonType buttonType, Runnable postClick) {
        final List<Match> result = new ArrayList<>();
        findAll(buttonType.asElement()).ifPresent(i -> i.forEachRemaining(result::add));
        if (!result.isEmpty()) {
            result.get(new Random().nextInt(result.size())).click();
            postClick.run();
        }
    }

    public void click(ButtonType buttonType, Runnable postClick) {
        find(buttonType.asElement()).ifPresent(b -> {
            b.click();
            postClick.run();
        });
    }

    public void clickMany(ButtonType buttonType, Runnable postClick) {
        findAll(buttonType.asElement()).ifPresent(i -> i.forEachRemaining(m -> {
            m.click();
            postClick.run();
        }));
    }
}
