package org.poe.collectors;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Match;
import org.sikuli.script.Screen;

import java.net.URL;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Yevhen Fabizhevskyi
 * @date 02.04.2016.
 */
public abstract class ElementsCollector {

    private static final Screen screen = new Screen();

    public void collect(String folderName) {
        Collection<String> targets = collectTargets(folderName);
        while (true) {
            Map<String, Match> matches = new HashMap<>();
            for (String target : targets) {
                Match exists = screen.exists(target);
                if (exists != null) {
                    matches.put(target, exists);
                }
            }
            if (matches.isEmpty()) {
                System.out.println(String.format(
                        "[INFO] [%s] Elements are not found.", getClass().getSimpleName()));
                break;
            } else {
                for (Map.Entry<String, Match> match : matches.entrySet()) {
                    Iterator<Match> foundElements;
                    try {
                        foundElements = match.getValue().findAll(match.getKey());
                    } catch (FindFailed ignored) {
                        continue;
                    }
                    while (foundElements.hasNext()) {
                        invoke(foundElements.next());
                    }
                }
            }
        }
    }

    private static Collection<String> collectTargets(String folderName) {
        Collection<String> result = new ArrayList<>();
        int counter = 1;
        while (true) {
            URL resource = ClassLoader.getSystemResource(String.format("img/%s/%s.png", folderName, counter));
            if (resource == null) {
                break;
            }
            result.add(resource.getPath());
            counter++;
        }
        return result;
    }

    protected abstract void invoke(Match match);
}
