package org.poe.entities.validation;

import org.poe.entities.ButtonType;
import org.poe.entities.ElementsManager;
import org.sikuli.script.Match;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author Yevhen Fabizhevskyi
 * @date 05.04.2016.
 */
public class ValidationManager extends ElementsManager {

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
            sleep(ValidationManager.class, ANOTHER_CLIENT_WAIT_TIME);

            Optional<Match> matchReloadButton = find(
                    ValidationManager.class,
                    "buttons",
                    ButtonType.RELOAD.getDisplayName(),
                    ButtonType.RELOAD.getImageName());
            if (matchReloadButton.isPresent()) {
                matchReloadButton.get().click();
            }
        }
    }

    public static void validateServerConnectionError() {
        final String SERVER_CONNECTION_ERROR_MESSAGE_DISPLAY_NAME = "'Server connection' error";
        final String SERVER_CONNECTION_ERROR_MESSAGE_IMAGE_NAME = "server_connection_error_message";

        validateError(
                SERVER_CONNECTION_ERROR_MESSAGE_DISPLAY_NAME,
                SERVER_CONNECTION_ERROR_MESSAGE_IMAGE_NAME,
                ButtonType.REPEAT);
    }

    public static void validateError17() {
        final String ERROR_17_MESSAGE_DISPLAY_NAME = "'Error 17' message";
        final String ERROR_17_MESSAGE_IMAGE_NAME = "error_17_message";

        validateError(
                ERROR_17_MESSAGE_DISPLAY_NAME,
                ERROR_17_MESSAGE_IMAGE_NAME,
                ButtonType.RELOAD);
    }

    private static void validateError(String messageDisplayName,
                                     String messageImageName,
                                     ButtonType buttonType) {
        Optional<Match> matchErrorMessage = find(
                ValidationManager.class,
                "validation",
                messageDisplayName,
                messageImageName);
        if (matchErrorMessage.isPresent()) {
            Optional<Match> matchButton = find(
                    ValidationManager.class,
                    "buttons",
                    buttonType.getDisplayName(),
                    buttonType.getImageName());
            if (matchButton.isPresent()) {
                matchButton.get().click();
            }
        }
    }
}
