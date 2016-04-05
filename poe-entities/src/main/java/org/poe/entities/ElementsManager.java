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

    protected static <T extends ElementsManager> Optional<Match> find(Class<T> clazz,
                                                                      String folderName,
                                                                      String displayName,
                                                                      String imageName) {
        URL resource = ClassLoader.getSystemResource(
                String.format("img/%s/%s.png", folderName, imageName));
        if (resource == null) {
            Logger.getInstance().info(
                    clazz, String.format("'%s' image is not found.", imageName));
            return Optional.empty();
        }
        Match match = ScreenInstance.get().exists(resource.getPath());
        if (match == null) {
            Logger.getInstance().info(clazz, displayName + " does not exist.");
            return Optional.empty();
        }
        Iterator<Match> foundElements;
        try {
            foundElements = match.findAll(resource.getPath());
        } catch (FindFailed e) {
            Logger.getInstance().error(clazz, e.getMessage());
            return Optional.empty();
        }
        if (foundElements.hasNext()) {
            return Optional.of(foundElements.next());
        }
        Logger.getInstance().info(clazz, displayName + " is not found.");
        return Optional.empty();
    }
}
