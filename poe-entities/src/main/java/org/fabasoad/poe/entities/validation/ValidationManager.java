package org.fabasoad.poe.entities.validation;

import org.fabasoad.poe.Logger;
import org.fabasoad.poe.entities.buttons.ButtonType;
import org.fabasoad.poe.entities.ElementsManager;
import org.fabasoad.poe.entities.buttons.ButtonsManager;
import org.sikuli.script.Match;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author Yevhen Fabizhevskyi
 * @date 05.04.2016.
 */
public final class ValidationManager extends ElementsManager {

    private static final ValidationManager instance = new ValidationManager();

    public static ValidationManager getInstance() {
        return instance;
    }

    private ValidationManager() {
    }

    private final String PROCESS_NAME = "Pirates.UAP.exe";

    public void validateAll() {
        validateProcessIsRunning();
        validateAnotherClient();
        validateServerConnectionError();
        validateError17();
        validateLevelUp();
        validateRating();
        validateInternetConnectionError();
    }

    private void validateProcessIsRunning() {
        Path taskListPath = Paths.get(System.getenv("windir"), "system32", "tasklist.exe");
        try {
            Process taskListProcess = Runtime.getRuntime().exec(taskListPath.toString());

            String line, pidInfo = "";
            try (BufferedReader input = new BufferedReader(new InputStreamReader(taskListProcess.getInputStream()))) {
                while ((line = input.readLine()) != null) {
                    pidInfo += line;
                }
            }

            if (pidInfo.contains(PROCESS_NAME)) {
                Logger.getInstance().flow(getClass(), String.format("'%s' process exists.", PROCESS_NAME));
            } else {
                findSystemElement(SystemElement.WIN_LOGO).ifPresent(winLogo -> {
                    winLogo.click();
                    findSystemElement(SystemElement.GAME_TILE).ifPresent(gameTile -> {
                        gameTile.click();

                        final long WAIT_TIME = TimeUnit.SECONDS.toMillis(10);
                        sleep(WAIT_TIME);
                    });
                });
            }
        } catch (IOException e) {
            Logger.getInstance().error(getClass(), e.getMessage());
        }
    }

    private Optional<Match> findSystemElement(SystemElement systemElement) {
        return find(SystemElement.getFolderName(), systemElement.getDisplayName(), systemElement.getImageName());
    }

    private void validateAnotherClient() {
        final long ANOTHER_CLIENT_WAIT_TIME = TimeUnit.MINUTES.toMillis(2);

        find(ValidationType.getFolderName(),
                ValidationType.ANOTHER_CLIENT.getDisplayName(),
                ValidationType.ANOTHER_CLIENT.getImageName()).ifPresent(m -> {
            sleep(ANOTHER_CLIENT_WAIT_TIME);
            ButtonsManager.getInstance().click(ButtonType.RELOAD);
        });
    }

    private void validateServerConnectionError() {
        validate(ValidationType.SERVER_CONNECTION, ButtonType.REPEAT);
    }

    private void validateError17() {
        validate(ValidationType.ERROR_17, ButtonType.RELOAD);
    }

    private void validateLevelUp() {
        validate(ValidationType.LEVEL_UP, ButtonType.OK);
    }

    private void validateRating() {
        validate(ValidationType.RATING_1, ButtonType.NO_THANKS);
        validate(ValidationType.RATING_2, ButtonType.NO_THANKS);
    }

    private void validateInternetConnectionError() {
        validate(ValidationType.INTERNET_CONNECTION, ButtonType.REPEAT);
    }

    private void validate(ValidationType validationType,
                          ButtonType buttonType) {
        find(ValidationType.getFolderName(), validationType.getDisplayName(), validationType.getImageName())
                .ifPresent(m -> ButtonsManager.getInstance().click(buttonType));
    }
}
