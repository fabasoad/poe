package org.fabasoad.poe.entities;

import com.google.common.collect.Iterators;
import org.apache.commons.lang3.tuple.Triple;
import org.fabasoad.poe.core.Logger;
import org.fabasoad.poe.ScreenInstance;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Match;
import org.sikuli.script.Region;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

/**
 * @author Yevhen Fabizhevskyi
 * @date 05.04.2016.
 */
public abstract class ElementsManager {

    protected final Optional<Match> find(Triple<String, String, String> element) {
        String fullImageName = String.format("%s/%s.png", element.getLeft(), element.getRight());
        Match match = ScreenInstance.get().exists(fullImageName);
        if (match == null) {
            Logger.getInstance().flow(getClass(), element.getMiddle() + " is not found.");
            return Optional.empty();
        }
        return Optional.of(match);
    }

    protected final Optional<Iterator<Match>> findAll(Collection<Triple<String, String, String>> elements) {
        @SuppressWarnings("unchecked")
        final Iterator<Match>[] result = new Iterator[1];
        elements.forEach(e -> findAll(e).ifPresent(i1 ->
            result[0] = Optional.ofNullable(result[0]).map(i2 -> Iterators.concat(i1, i2)).orElse(i1)
        ));
        return Optional.ofNullable(result[0]);
    }

    protected final Optional<Iterator<Match>> findAll(Triple<String, String, String> element) {
        String fullImageName = String.format("%s/%s.png", element.getLeft(), element.getRight());
        if (exists(ScreenInstance.get(), fullImageName)) {
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
        }
        Logger.getInstance().flow(getClass(), element.getMiddle() + " is not found.");
        return Optional.empty();
    }

    private boolean exists(Region region, String fullImageName) {
        return region.exists(fullImageName) != null;
    }

    protected final void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Logger.getInstance().error(getClass(), e.getMessage());
        }
    }
}
