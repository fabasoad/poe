package org.fabasoad.poe.entities.resources;

import com.google.common.collect.Iterators;
import org.fabasoad.poe.entities.ElementsManager;
import org.fabasoad.poe.entities.views.ViewType;
import org.fabasoad.poe.entities.views.ViewsManager;
import org.sikuli.script.Match;
import org.sikuli.script.Region;

import java.util.Iterator;
import java.util.Optional;

/**
 * @author Yevhen Fabizhevskyi
 * @date 05.04.2016.
 */
public class ResourceManager extends ViewsManager {

    private static final ResourceManager instance = new ResourceManager();

    public static ResourceManager getInstance() {
        return instance;
    }

    private ResourceManager() {
    }

    @Override
    protected ViewType getCurrentView() {
        return ViewType.CITY;
    }

    public void collect() {
        // Check if we are on a 'City' view
        goToCurrentView();

        findAll(ResourceType.values()).ifPresent(i -> i.forEachRemaining(Region::click));
    }

    private Optional<Iterator<Match>> findAll(ResourceType[] resources) {
        @SuppressWarnings("unchecked")
        final Iterator<Match>[] result = new Iterator[1];
        for (ResourceType resource : resources) {
            findAll(ResourceType.getFolderName(), resource.getDisplayName(), resource.getImageName()).ifPresent(i ->
                    result[0] = Optional.ofNullable(result[0]).map(r -> Iterators.concat(r, i)).orElse(i));
        }
        return Optional.ofNullable(result[0]);
    }
}
