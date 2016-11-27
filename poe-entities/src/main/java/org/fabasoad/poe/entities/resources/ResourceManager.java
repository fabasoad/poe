package org.fabasoad.poe.entities.resources;

import org.fabasoad.annotations.UsedViaReflection;
import org.fabasoad.poe.entities.views.ViewAwareElementsManager;
import org.fabasoad.poe.entities.views.ViewType;
import org.fabasoad.poe.statistics.SupportedStatistics;
import org.sikuli.script.Match;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Yevhen Fabizhevskyi
 * @date 05.04.2016.
 */
@SupportedStatistics
public final class ResourceManager extends ViewAwareElementsManager {

    private static final ResourceManager instance = new ResourceManager();

    @UsedViaReflection
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

    @UsedViaReflection
    public String getStatistics() {
        return statistics > 0 ? "Collected resources count: " + statistics : "";
    }

    public void collect() {
        trySwitchView();
        findAll(ResourceType.values()).ifPresent(i -> i.forEachRemaining(resource -> {
            resource.click();
            statistics++;
        }));
    }

    private Optional<Iterator<Match>> findAll(ResourceType[] resources) {
        return findAll(Arrays.stream(resources).map(ResourceType::asElement).collect(Collectors.toList()));
    }
}
