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

    public static void validateAnotherClient() {
        final long ANOTHER_CLIENT_WAIT_TIME = TimeUnit.MINUTES.toMillis(2);
        final String ANOTHER_CLIENT_MESSAGE_DISPLAY_NAME = "'Another client' message";
        final String ANOTHER_CLIENT_MESSAGE_IMAGE_NAME = "another_client_message";
        final String ANOTHER_CLIENT_BUTTON_DISPLAY_NAME = "'Another client' button";
        final String ANOTHER_CLIENT_BUTTON_IMAGE_NAME = "another_client_button";

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
                Optional<Match> matchAnotherClientButton = find(
                        ValidationManager.class,
                        "validation",
                        ANOTHER_CLIENT_BUTTON_DISPLAY_NAME,
                        ANOTHER_CLIENT_BUTTON_IMAGE_NAME);
                if (matchAnotherClientButton.isPresent()) {
                    matchAnotherClientButton.get().click();
                }
            }
        }
    }
}
