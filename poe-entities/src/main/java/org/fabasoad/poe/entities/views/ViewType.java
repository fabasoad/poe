package org.fabasoad.poe.entities.views;

import org.apache.commons.lang3.tuple.Triple;

/**
 * @author Yevhen Fabizhevskyi
 * @date 16.04.2016.
 */
public enum ViewType {

    CITY("'City' view", "ocean_view"),
    OCEAN("'Ocean' view", "city_view");

    private static final String VIEWS_FOLDER = "views";
    private String displayName;
    private String imageToGoName;

    ViewType(String displayName, String imageToGoName) {
        this.displayName = displayName;
        this.imageToGoName = imageToGoName;
    }

    public Triple<String, String, String> asElement() {
        return Triple.of(VIEWS_FOLDER, displayName, imageToGoName);
    }
}
