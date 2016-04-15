package org.fabasoad.poe.entities.resources;

import com.google.common.collect.Iterators;
import org.fabasoad.poe.entities.ElementsManager;
import org.sikuli.script.Match;

import java.util.Iterator;
import java.util.Optional;

/**
 * @author Yevhen Fabizhevskyi
 * @date 05.04.2016.
 */
public class ResourceManager extends ElementsManager {

    private static final ResourceManager instance = new ResourceManager();

    public static ResourceManager getInstance() {
        return instance;
    }

    private ResourceManager() {
    }

    public void collect() {
        findAll(ResourceType.values()).ifPresent(iterator -> {
            while (iterator.hasNext()) {
                iterator.next().click();
            }
        });
    }

    private Optional<Iterator<Match>> findAll(ResourceType[] resources) {
        @SuppressWarnings("unchecked")
        final Iterator<Match>[] result = new Iterator[1];
        for (ResourceType resource : resources) {
            findAll("resources", resource.getDisplayName(), resource.getImageName()).ifPresent(i ->
                    result[0] = Optional.ofNullable(result[0]).map(r -> Iterators.concat(r, i)).orElse(i));
        }
        return Optional.ofNullable(result[0]);
    }
}
