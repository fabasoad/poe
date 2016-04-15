package org.fabasoad.poe.entities.validation;

import org.fabasoad.poe.entities.ButtonType;
import org.fabasoad.poe.entities.ElementsManager;
import org.sikuli.script.Region;

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

        find("validation",
             ValidationType.ANOTHER_CLIENT.getDisplayName(),
             ValidationType.ANOTHER_CLIENT.getImageName()).ifPresent(m -> {
            sleep(ANOTHER_CLIENT_WAIT_TIME);

            find("buttons", ButtonType.RELOAD.getDisplayName(), ButtonType.RELOAD.getImageName())
                    .ifPresent(Region::click);
        });
    }

    public void validateServerConnectionError() {
        validate(ValidationType.SERVER_CONNECTION, ButtonType.REPEAT);
    }

    public void validateError17() {
        validate(ValidationType.ERROR_17, ButtonType.RELOAD);
    }

    public void validateLevelUp() {
        validate(ValidationType.LEVEL_UP, ButtonType.OK);
    }

    private void validate(ValidationType validationType,
                          ButtonType buttonType) {
        find("validation", validationType.getDisplayName(), validationType.getImageName())
                .ifPresent(m -> find("buttons", buttonType.getDisplayName(), buttonType.getImageName())
                        .ifPresent(Region::click));
    }
}
