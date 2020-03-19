package io.github.fabasoad.poe.entities;

import com.google.common.collect.Iterators;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Triple;
import io.github.fabasoad.poe.ScreenInstance;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Match;
import org.sikuli.script.Region;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;

@Slf4j
public abstract class ElementsManager {

    protected final Optional<Match> find(Triple<String, String, String> element) {
        return find(element, ScreenInstance.get());
    }

    protected final Optional<Match> find(Triple<String, String, String> element, Region region) {
        String fullImageName = String.format("%s/%s.png", element.getLeft(), element.getRight());
        Match match = region.exists(fullImageName);
        if (match == null) {
            log.info("{} is not found.", element.getMiddle());
            return Optional.empty();
        }
        return Optional.of(match);
    }

    public final Optional<Iterator<Match>> findAll(Collection<Triple<String, String, String>> elements) {
        final Iterator<Match> result = elements.stream()
                .map(e -> findAll(e).orElse(Collections.emptyIterator()))
                .reduce(Collections.emptyIterator(), Iterators::concat);
        return result.hasNext() ? Optional.of(result) : Optional.empty();
    }

    public final Optional<Iterator<Match>> findAll(Triple<String, String, String> element) {
        String fullImageName = String.format("%s/%s.png", element.getLeft(), element.getRight());
        if (exists(ScreenInstance.get(), fullImageName)) {
            Iterator<Match> foundElements;
            try {
                foundElements = ScreenInstance.get().findAll(fullImageName);
            } catch (FindFailed e) {
                log.error("Failed to find {}.", fullImageName, e);
                return Optional.empty();
            }
            if (foundElements.hasNext()) {
                return Optional.of(foundElements);
            }
        }
        log.info("{} is not found.", element.getMiddle());
        return Optional.empty();
    }

    private boolean exists(Region region, String fullImageName) {
        return region.exists(fullImageName) != null;
    }

    protected final void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            log.error("Failed to sleep the thread.", e);
        }
    }
}
