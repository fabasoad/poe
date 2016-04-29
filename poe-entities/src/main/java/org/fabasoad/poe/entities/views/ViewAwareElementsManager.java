package org.fabasoad.poe.entities.views;

import org.fabasoad.poe.core.Logger;
import org.fabasoad.poe.entities.ElementsManager;

/**
 * @author Yevhen Fabizhevskyi
 * @date 19.04.2016.
 */
public abstract class ViewAwareElementsManager extends ElementsManager {

    protected abstract ViewType getViewType();

    protected final void trySwitchView() {
        find(getViewType().asElement()).ifPresent(view -> {
            view.click();
            Logger.getInstance().flow(getClass(), "Switched to " + getViewType().asElement().getMiddle());
        });
    }
}
