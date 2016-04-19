package org.fabasoad.poe.entities.views;

import org.fabasoad.poe.Logger;
import org.fabasoad.poe.entities.ElementsManager;
import org.sikuli.script.Match;

import java.util.Optional;

/**
 * @author Yevhen Fabizhevskyi
 * @date 19.04.2016.
 */
public abstract class ViewAwareElementsManager extends ElementsManager {

    protected abstract ViewType getViewType();

    protected final void trySwitchView() {
        findView(getViewType()).ifPresent(v -> {
            v.click();
            Logger.getInstance().flow(getClass(), "Switched to " + getViewType().getDisplayName());
        });
    }

    private Optional<Match> findView(ViewType viewType) {
        return find(ViewType.getFolderName(), viewType.getDisplayName(), viewType.getImageToGoName());
    }
}
