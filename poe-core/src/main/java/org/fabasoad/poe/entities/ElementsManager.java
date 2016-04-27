package org.fabasoad.poe.entities;

import com.google.common.collect.Iterators;
import org.apache.commons.lang3.tuple.Triple;
import org.fabasoad.poe.core.Logger;
import org.fabasoad.poe.ScreenInstance;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Match;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;

/**
 * @author Yevhen Fabizhevskyi
 * @date 05.04.2016.
 */
public abstract class ElementsManager {

    protected Optional<Match> find(Triple<String, String, String> element) {
        String fullImageName = String.format("%s/%s.png", element.getLeft(), element.getRight());
        Match match = ScreenInstance.get().exists(fullImageName);
        if (match == null) {
            Logger.getInstance().flow(getClass(), element.getMiddle() + " does not exist.");
            return Optional.empty();
        }
        return Optional.of(match);
    }

    protected Optional<Iterator<Match>> findAll(Collection<Triple<String, String, String>> elements) {
        @SuppressWarnings("unchecked")
        final Iterator<Match>[] result = new Iterator[1];
        elements.forEach(e ->
            result[0] = Optional.ofNullable(result[0])
                    .map(i -> Iterators.concat(i, findAll(e).orElse(Collections.emptyIterator())))
                    .orElse(findAll(e).orElse(null))
        );
        return Optional.ofNullable(result[0]);
    }

    protected Optional<Iterator<Match>> findAll(Triple<String, String, String> element) {
        String fullImageName = String.format("%s/%s.png", element.getLeft(), element.getRight());
        Match match = ScreenInstance.get().exists(fullImageName);
        if (match == null) {
            Logger.getInstance().flow(getClass(), element.getMiddle() + " does not exist.");
            return Optional.empty();
        }
        Iterator<Match> foundElements;
        try {
            foundElements = ScreenInstance.get().findAll(fullImageName);
        } catch (FindFailed e) {
            Logger.getInstance().error(getClass(), e.getMessage());
            return Optional.empty();
        }
        if (foundElements.hasNext()) {
            return Optional.of(foundElements);
        }
        Logger.getInstance().flow(getClass(), element.getMiddle() + " is not found.");
        return Optional.empty();
    }

    protected final Optional<Iterator<Match>> findAllExceptOf(String folderName,
                                                              String displayNameToFind,
                                                              String imageNameToFind,
                                                              String imageNameToExclude) {
        String fullImageName = String.format("%s/%s.png", folderName, imageNameToFind);
        Match match = ScreenInstance.get().exists(fullImageName);
        if (match == null) {
            Logger.getInstance().flow(getClass(), displayNameToFind + " does not exist.");
            return Optional.empty();
        }
        Iterator<Match> foundElements;
        try {
            foundElements = ScreenInstance.get().findAll(fullImageName);
        } catch (FindFailed e) {
            Logger.getInstance().error(getClass(), e.getMessage());
            return Optional.empty();
        }
        Collection<Match> filteredElements = new ArrayList<>();
        foundElements.forEachRemaining(m -> {
            if (!find(m, folderName, imageNameToExclude)) {
                filteredElements.add(m);
            }
        });
        if (filteredElements.isEmpty()) {
            Logger.getInstance().flow(getClass(), displayNameToFind + " is not found.");
            return Optional.empty();
        }
        return Optional.of(filteredElements.iterator());
    }

    private boolean find(Match match, String folderName, String imageName) {
        return match.exists(String.format("%s/%s.png", folderName, imageName)) != null;
    }

    protected final void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Logger.getInstance().error(getClass(), e.getMessage());
        }
    }
}
