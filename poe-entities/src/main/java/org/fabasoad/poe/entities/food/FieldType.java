package org.fabasoad.poe.entities.food;

import org.apache.commons.lang3.tuple.Triple;

/**
 * @author Yevhen Fabizhevskyi
 * @date 24.04.2016.
 */
public enum FieldType {

    SEED("Seed field", "field_seed"),
    EMPTY("Empty field", "field_empty");

    private static final String FIELDS_FOLDER = "fields";
    private final String displayName;
    private final String imageName;

    FieldType(String displayName, String imageName) {
        this.displayName = displayName;
        this.imageName = imageName;
    }

    public Triple<String, String, String> asElement() {
        return Triple.of(FIELDS_FOLDER, displayName, imageName);
    }
}
