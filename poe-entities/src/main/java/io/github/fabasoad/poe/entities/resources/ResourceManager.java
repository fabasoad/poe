package io.github.fabasoad.poe.entities.resources;

import io.github.fabasoad.annotations.UsedViaReflection;
import io.github.fabasoad.poe.entities.views.ViewAwareElementsManager;
import io.github.fabasoad.poe.entities.views.ViewType;
import io.github.fabasoad.poe.statistics.SupportedStatistics;
import org.sikuli.script.Match;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Collectors;

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
