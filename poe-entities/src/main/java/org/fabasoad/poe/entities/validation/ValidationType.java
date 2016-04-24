package org.fabasoad.poe.entities.validation;

/**
 * @author Yevhen Fabizhevskyi
 * @date 14.04.2016.
 */
enum ValidationType {

    ANOTHER_CLIENT("'Another client' message", "another_client_message"),
    SERVER_CONNECTION("'Server connection' error", "server_connection_error_message"),
    ERROR_17("'Error 17' message", "error_17_message"),
    LEVEL_UP("'Level up' message", "level_up_message"),
    RATING_1("'Rating 1' message", "rating_1_message"),
    RATING_2("'Rating 2' message", "rating_2_message"),
    INTERNET_CONNECTION("'Internet connection' error", "internet_connection_error_message"),
    BOT_MESSAGE("'Bot message'", "bot_message");

    private final String displayName;
    private final String imageName;

    ValidationType(String displayName, String imageName) {
        this.displayName = displayName;
        this.imageName = imageName;
    }

    String getDisplayName() {
        return displayName;
    }

    String getImageName() {
        return imageName;
    }

    static String getFolderName() {
        return "validation";
    }
}
