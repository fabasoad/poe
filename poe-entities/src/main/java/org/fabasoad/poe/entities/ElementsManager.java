package org.fabasoad.poe.entities;

import org.fabasoad.poe.Logger;
import org.fabasoad.poe.ScreenInstance;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Match;

import java.util.Iterator;
import java.util.Optional;

/**
 * @author Yevhen Fabizhevskyi
 * @date 05.04.2016.
 */
public abstract class ElementsManager {

    protected Optional<Match> find(String folderName, String displayName, String imageName) {
        String fullImageName = String.format("%s/%s.png", folderName, imageName);
        Match match = ScreenInstance.get().exists(fullImageName);
        if (match == null) {
            Logger.getInstance().flow(getClass(), displayName + " does not exist.");
            return Optional.empty();
        }
        return Optional.of(match);
    }

    protected Optional<Iterator<Match>> findAll(String folderName, String displayName, String imageName) {
        String fullImageName = String.format("%s/%s.png", folderName, imageName);
        Match match = ScreenInstance.get().exists(fullImageName);
        if (match == null) {
            Logger.getInstance().flow(getClass(), displayName + " does not exist.");
            return Optional.empty();
        }
        Iterator<Match> foundElements;
        try {
            foundElements = ScreenInstance.get().findAll(fullImageName);
        } catch (FindFailed e) {
            Logger.getInstance().error(getClass(), e.getMessage());
            return Optional.empty();
        }
        if (foundElements.hasNext()) {
            return Optional.of(foundElements);
        }
        Logger.getInstance().flow(getClass(), displayName + " is not found.");
        return Optional.empty();
    }

    protected final boolean sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Logger.getInstance().error(getClass(), e.getMessage());
            return false;
        }
        return true;
    }

    protected final void trySwitchView() {
        Class clazz = getClass();

        if (clazz.isAnnotationPresent(ViewAware.class)) {
            ViewAware viewAware = (ViewAware) clazz.getAnnotation(ViewAware.class);

            if (!viewAware.type().isNone()) {
                findView(viewAware.type()).ifPresent(v -> {
                    v.click();
                    Logger.getInstance().flow(getClass(), "Switched to " + viewAware.type().getDisplayName());
                });
            }
        }
    }

    private Optional<Match> findView(ViewType viewType) {
        return find(ViewType.getFolderName(), viewType.getDisplayName(), viewType.getImageToGoName());
    }
}
