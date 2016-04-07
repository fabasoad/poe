package org.poe.entities.resources;

import org.poe.entities.ElementsManager;
import org.sikuli.script.Match;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author Yevhen Fabizhevskyi
 * @date 05.04.2016.
 */
public class ResourceManager extends ElementsManager {

    private final static long COLLECT_WAIT_TIME = TimeUnit.SECONDS.toMillis(1);

    public static void collect() {
        for (ResourceType resourceType : ResourceType.values()) {
            while (true) {
                Optional<Match> element = find(resourceType);
                if (!element.isPresent()) {
                    break;
                }
                element.get().click();

                if (!sleep(ResourceManager.class, COLLECT_WAIT_TIME)) {
                    break;
                }
            }
        }
    }

    private static Optional<Match> find(ResourceType resourceType) {
        return find(ResourceManager.class, "resources", resourceType.getDisplayName(), resourceType.getImageName());
    }
}
