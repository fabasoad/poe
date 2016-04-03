package org.poe.collectors;

import org.poe.Logger;
import org.poe.entities.ScreenElement;
import org.poe.entities.ScreenElementDescriptor;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Match;
import org.sikuli.script.Screen;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Yevhen Fabizhevskyi
 * @date 02.04.2016.
 */
public abstract class ElementsCollector<T extends ScreenElement> {

    private static final Screen screen = new Screen();

    public void collect() {
        for (T element : collectElements()) {
            while (true) {
                Match match = screen.exists(element.getImagePath());
                if (match == null) {
                    Logger.getInstance().info(getClass(), element.getDisplayName() + " is not found.");
                    break;
                }
                Iterator<Match> foundElements;
                try {
                    foundElements = match.findAll(element.getImagePath());
                } catch (FindFailed e) {
                    Logger.getInstance().error(getClass(), e.getMessage());
                    continue;
                }
                while (foundElements.hasNext()) {
                    invoke(foundElements.next());
                }
            }
        }
    }

    private Collection<T> collectElements() {
        Collection<T> result = new ArrayList<>();
        for (ScreenElementDescriptor descriptor : getDescriptors()) {
            URL resource = ClassLoader.getSystemResource(
                    String.format("img/%s/%s.png", getFolderName(), descriptor.getImageName()));
            if (resource == null) {
                break;
            }
            result.add(createElement(descriptor.getDisplayName(), resource.getPath()));
        }
        return result;
    }

    protected abstract void invoke(Match match);

    protected abstract String getFolderName();

    protected abstract Collection<ScreenElementDescriptor> getDescriptors();

    protected abstract T createElement(String displayName, String imagePath);
}
