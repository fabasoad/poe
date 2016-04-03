package org.poe.collectors;

import org.poe.entities.ResourceElement;
import org.poe.entities.ScreenElementDescriptor;
import org.sikuli.script.Match;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author Yevhen Fabizhevskyi
 * @date 02.04.2016.
 */
public class ResourceCollector extends ElementsCollector<ResourceElement> {

    private static ResourceCollector instance = new ResourceCollector();

    private ResourceCollector() {}

    public static ResourceCollector getInstance() {
        return instance;
    }

    @Override
    protected void invoke(Match match) {
        match.click();
    }

    @Override
    protected String getFolderName() {
        return "resource";
    }

    @Override
    protected Collection<ScreenElementDescriptor> getDescriptors() {
        return Arrays.asList(
                new ScreenElementDescriptor("Gold", "gold"),
                new ScreenElementDescriptor("Iron", "iron"),
                new ScreenElementDescriptor("Wood", "wood"));
    }

    @Override
    protected ResourceElement createElement(String displayName, String imagePath) {
        return new ResourceElement(displayName, imagePath);
    }
}
