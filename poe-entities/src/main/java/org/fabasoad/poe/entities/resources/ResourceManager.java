package org.fabasoad.poe.entities.resources;

import com.google.common.collect.Iterators;
import org.fabasoad.poe.entities.views.ViewAwareElementsManager;
import org.fabasoad.poe.entities.views.ViewType;
import org.fabasoad.poe.statistics.SupportedStatistics;
import org.sikuli.script.Match;

import java.util.Iterator;
import java.util.Optional;

/**
 * @author Yevhen Fabizhevskyi
 * @date 05.04.2016.
 */
public final class ResourceManager extends ViewAwareElementsManager implements SupportedStatistics {

    private static final ResourceManager instance = new ResourceManager();

    public static ResourceManager getInstance() {
        return instance;
    }

    private ResourceManager() {
    }

    private int statistics = 0;

    @Override
    protected ViewType getViewType() {
        return ViewType.CITY;
    }

    @Override
    public String getResults() {
        return "Collected resources count: " + statistics;
    }

    public void collect() {
        trySwitchView();
        findAll(ResourceType.values()).ifPresent(i -> {
            while (i.hasNext()) {
                i.next().click();
                statistics++;
            }
        });
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
