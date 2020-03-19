package io.github.fabasoad.poe.entities.validation;

import org.apache.commons.lang3.tuple.Triple;

enum ValidationType {

    ANOTHER_CLIENT("'Another client' message", "another_client_message"),
    SERVER_CONNECTION("'Server connection' error", "server_connection_error_message"),
    ERROR_17("'Error 17' message", "error_17_message"),
    LEVEL_UP("'Level up' message", "level_up_message"),
    RATING_1("'Rating 1' message", "rating_1_message"),
    RATING_2("'Rating 2' message", "rating_2_message"),
    INTERNET_CONNECTION("'Internet connection' error", "internet_connection_error_message"),
    BOT_MESSAGE("'Bot message'", "bot_message"),
    SALE_MESSAGE("'Sale message'", "sale_message"),
    SCREEN_POINT("Screen point", "screen_point");

    public static final String VALIDATION_FOLDER = "validation";
    private final String displayName;
    private final String imageName;

    ValidationType(String displayName, String imageName) {
        this.displayName = displayName;
        this.imageName = imageName;
    }

    public Triple<String, String, String> asElement() {
        return Triple.of(VALIDATION_FOLDER, displayName, imageName);
    }
}
