package org.fabasoad.poe.entities.views;

import org.fabasoad.poe.entities.ElementsManager;
import org.sikuli.script.Match;
import org.sikuli.script.Region;

import java.util.Optional;

/**
 * @author Yevhen Fabizhevskyi
 * @date 16.04.2016.
 */
public abstract class ViewsManager extends ElementsManager {

    private ViewType lastView;

    protected abstract ViewType getCurrentView();

    protected final void goToCurrentView() {
        ViewType currentView = getCurrentView();
        if (lastView != currentView) {
            lastView = currentView;
            findView(currentView).ifPresent(Region::click);
        }
    }

    private Optional<Match> findView(ViewType viewType) {
        return find(ViewType.getFolderName(), viewType.getDisplayName(), viewType.getImageName());
    }
}
