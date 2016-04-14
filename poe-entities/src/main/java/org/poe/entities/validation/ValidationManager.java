package org.poe.entities.validation;

import org.poe.entities.ButtonType;
import org.poe.entities.ElementsManager;
import org.sikuli.script.Match;
import org.sikuli.script.Region;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author Yevhen Fabizhevskyi
 * @date 05.04.2016.
 */
public class ValidationManager extends ElementsManager {

    private static final ValidationManager instance = new ValidationManager();

    public static ValidationManager getInstance() {
        return instance;
    }

    private ValidationManager() {
    }

    public void validateAnotherClient() {
        final long ANOTHER_CLIENT_WAIT_TIME = TimeUnit.MINUTES.toMillis(2);
        final String ANOTHER_CLIENT_MESSAGE_DISPLAY_NAME = "'Another client' message";
        final String ANOTHER_CLIENT_MESSAGE_IMAGE_NAME = "another_client_message";

        Optional<Match> matchAnotherClientMessage = find(
                "validation",
                ANOTHER_CLIENT_MESSAGE_DISPLAY_NAME,
                ANOTHER_CLIENT_MESSAGE_IMAGE_NAME);
        if (matchAnotherClientMessage.isPresent()) {
            sleep(ANOTHER_CLIENT_WAIT_TIME);

            Optional<Match> matchReloadButton = find(
                    "buttons",
                    ButtonType.RELOAD.getDisplayName(),
                    ButtonType.RELOAD.getImageName());
            if (matchReloadButton.isPresent()) {
                matchReloadButton.get().click();
            }
        }
    }

    public void validateServerConnectionError() {
        final String SERVER_CONNECTION_ERROR_MESSAGE_DISPLAY_NAME = "'Server connection' error";
        final String SERVER_CONNECTION_ERROR_MESSAGE_IMAGE_NAME = "server_connection_error_message";

        validateError(
                SERVER_CONNECTION_ERROR_MESSAGE_DISPLAY_NAME,
                SERVER_CONNECTION_ERROR_MESSAGE_IMAGE_NAME,
                ButtonType.REPEAT);
    }

    public void validateError17() {
        final String ERROR_17_MESSAGE_DISPLAY_NAME = "'Error 17' message";
        final String ERROR_17_MESSAGE_IMAGE_NAME = "error_17_message";

        validateError(
                ERROR_17_MESSAGE_DISPLAY_NAME,
                ERROR_17_MESSAGE_IMAGE_NAME,
                ButtonType.RELOAD);
    }

    public void validateLevelUp() {
        final String LEVEL_UP_MESSAGE_DISPLAY_NAME = "'Level up' message";
        final String LEVEL_UP_MESSAGE_IMAGE_NAME = "level_up_message";

        validateError(
                LEVEL_UP_MESSAGE_DISPLAY_NAME,
                LEVEL_UP_MESSAGE_IMAGE_NAME,
                ButtonType.OK);
    }

    private void validateError(String messageDisplayName,
                               String messageImageName,
                               ButtonType buttonType) {
        find("validation", messageDisplayName, messageImageName)
                .ifPresent(m -> find("buttons", buttonType.getDisplayName(), buttonType.getImageName())
                        .ifPresent(Region::click));
    }
}
