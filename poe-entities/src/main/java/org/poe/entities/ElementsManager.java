package org.poe.entities;

import org.poe.Logger;
import org.poe.ScreenInstance;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Match;

import java.net.URL;
import java.util.Iterator;
import java.util.Optional;

/**
 * @author Yevhen Fabizhevskyi
 * @date 05.04.2016.
 */
public abstract class ElementsManager {

    protected Optional<Match> find(String folderName, String displayName, String imageName) {
        URL resource = ClassLoader.getSystemResource(
                String.format("img/%s/%s.png", folderName, imageName));
        if (resource == null) {
            Logger.getInstance().flow(
                    getClass(), String.format("'%s' image is not found.", imageName));
            return Optional.empty();
        }
        Match match = ScreenInstance.get().exists(resource.getPath());
        if (match == null) {
            Logger.getInstance().flow(getClass(), displayName + " does not exist.");
            return Optional.empty();
        }
        return Optional.of(match);
    }

    protected Optional<Iterator<Match>> findAll(String folderName, String displayName, String imageName) {
        URL resource = ClassLoader.getSystemResource(
                String.format("img/%s/%s.png", folderName, imageName));
        if (resource == null) {
            Logger.getInstance().flow(
                    getClass(), String.format("'%s' image is not found.", imageName));
            return Optional.empty();
        }
        Match match = ScreenInstance.get().exists(resource.getPath());
        if (match == null) {
            Logger.getInstance().flow(getClass(), displayName + " does not exist.");
            return Optional.empty();
        }
        Iterator<Match> foundElements;
        try {
            foundElements = ScreenInstance.get().findAll(resource.getPath());
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

    protected boolean sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Logger.getInstance().error(getClass(), e.getMessage());
            return false;
        }
        return true;
    }
}
