package org.poe.collectors;

import org.sikuli.script.Match;

/**
 * @author Yevhen Fabizhevskyi
 * @date 02.04.2016.
 */
public class ResourceCollector extends ElementsCollector {

    private static ResourceCollector instance = new ResourceCollector();

    private ResourceCollector() {}

    public static ResourceCollector getInstance() {
        return instance;
    }

    @Override
    protected void invoke(Match match) {
        match.click();
    }
}
