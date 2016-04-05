package org.poe.entities.resources;

import org.poe.entities.ElementsManager;
import org.sikuli.script.Match;

import java.util.Optional;

import static org.poe.entities.resources.ResourceType.*;

/**
 * @author Yevhen Fabizhevskyi
 * @date 05.04.2016.
 */
public class ResourceManager extends ElementsManager {

    private final static ResourceType[] RESOURCE_TYPES = { GOLD, WOOD, IRON };

    public static void collect() {
        for (ResourceType resourceType : RESOURCE_TYPES) {
            Optional<Match> element = find(resourceType);
            if (!element.isPresent()) {
                continue;
            }
            element.get().click();
        }
    }

    private static Optional<Match> find(ResourceType resourceType) {
        return find(ResourceManager.class, "resources", resourceType.getDisplayName(), resourceType.getImageName());
    }
}
