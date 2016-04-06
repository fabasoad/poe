package org.poe.entities.validation;

import org.poe.Logger;
import org.poe.entities.ElementsManager;
import org.sikuli.script.Match;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author Yevhen Fabizhevskyi
 * @date 05.04.2016.
 */
public class ValidationManager extends ElementsManager {

    private final static String RELOAD_BUTTON_DISPLAY_NAME = "'Another client' button";
    private final static String RELOAD_BUTTON_IMAGE_NAME = "reload_button";

    public static void validateAnotherClient() {
        final long ANOTHER_CLIENT_WAIT_TIME = TimeUnit.MINUTES.toMillis(2);
        final String ANOTHER_CLIENT_MESSAGE_DISPLAY_NAME = "'Another client' message";
        final String ANOTHER_CLIENT_MESSAGE_IMAGE_NAME = "another_client_message";

        Optional<Match> matchAnotherClientMessage = find(
                ValidationManager.class,
                "validation",
                ANOTHER_CLIENT_MESSAGE_DISPLAY_NAME,
                ANOTHER_CLIENT_MESSAGE_IMAGE_NAME);
        if (matchAnotherClientMessage.isPresent()) {
            try {
                Thread.sleep(ANOTHER_CLIENT_WAIT_TIME);
            } catch (InterruptedException e) {
                Logger.getInstance().error(ValidationManager.class, e.getMessage());
            } finally {
                Optional<Match> matchReloadButton = find(
                        ValidationManager.class,
                        "validation",
                        RELOAD_BUTTON_DISPLAY_NAME,
                        RELOAD_BUTTON_IMAGE_NAME);
                if (matchReloadButton.isPresent()) {
                    matchReloadButton.get().click();
                }
            }
        }
    }

    public static void validateServerConnectionError() {
        final String SERVER_CONNECTION_ERROR_MESSAGE_DISPLAY_NAME = "'Server connection error' message";
        final String SERVER_CONNECTION_ERROR_MESSAGE_IMAGE_NAME = "server_connection_error_message";
        final String SERVER_CONNECTION_ERROR_BUTTON_DISPLAY_NAME = "'Server connection error' button";
        final String SERVER_CONNECTION_ERROR_BUTTON_IMAGE_NAME = "server_connection_error_button";

        validateError(
                SERVER_CONNECTION_ERROR_MESSAGE_DISPLAY_NAME,
                SERVER_CONNECTION_ERROR_MESSAGE_IMAGE_NAME,
                SERVER_CONNECTION_ERROR_BUTTON_DISPLAY_NAME,
                SERVER_CONNECTION_ERROR_BUTTON_IMAGE_NAME);
    }

    public static void validateError17() {
        final String ERROR_17_MESSAGE_DISPLAY_NAME = "'Error 17' message";
        final String ERROR_17_MESSAGE_IMAGE_NAME = "error_17_message";

        validateError(
                ERROR_17_MESSAGE_DISPLAY_NAME,
                ERROR_17_MESSAGE_IMAGE_NAME,
                RELOAD_BUTTON_DISPLAY_NAME,
                RELOAD_BUTTON_IMAGE_NAME);
    }

    private static void validateError(String messageDisplayName,
                                     String messageImageName,
                                     String buttonDisplayName,
                                     String buttonImageName) {
        Optional<Match> matchErrorMessage = find(
                ValidationManager.class,
                "validation",
                messageDisplayName,
                messageImageName);
        if (matchErrorMessage.isPresent()) {
            Optional<Match> matchButton = find(
                    ValidationManager.class,
                    "validation",
                    buttonDisplayName,
                    buttonImageName);
            if (matchButton.isPresent()) {
                matchButton.get().click();
            }
        }
    }
}
