package org.fabasoad.poe.entities.validation;

import org.fabasoad.poe.core.Logger;
import org.fabasoad.poe.core.UsedViaReflection;
import org.fabasoad.poe.entities.ElementsManager;
import org.fabasoad.poe.entities.buttons.ButtonType;
import org.fabasoad.poe.entities.buttons.ButtonsManager;
import org.fabasoad.poe.statistics.SupportedStatistics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.fabasoad.poe.entities.buttons.ButtonType.NO_THANKS;
import static org.fabasoad.poe.entities.buttons.ButtonType.OK;
import static org.fabasoad.poe.entities.buttons.ButtonType.RELOAD;
import static org.fabasoad.poe.entities.buttons.ButtonType.REPEAT;

/**
 * @author Yevhen Fabizhevskyi
 * @date 05.04.2016.
 */
@SupportedStatistics
public final class ValidationManager extends ElementsManager {

    private static final ValidationManager instance = new ValidationManager();

    @UsedViaReflection
    public static ValidationManager getInstance() {
        return instance;
    }

    private ValidationManager() {
    }

    private final Map<String, Integer> statistics = new HashMap<>();

    @UsedViaReflection
    public String getStatistics() {
        final String template = "Validation '%s' positives count: %s";
        return statistics.entrySet().stream()
                .map(e -> String.format(template, e.getKey(), e.getValue()))
                .collect(Collectors.joining(System.getProperty("line.separator")));
    }

    private void saveStatistics(String methodName) {
        statistics.merge(methodName, 1, Integer::sum);
    }

    public void validateAll() {
        validateProcessIsRunning();
        validateAnotherClient();
        validateServerConnectionError();
        validateError17();
        validateLevelUp();
        validateRating();
        validateInternetConnectionError();
        validateBotMessageShown();
        validateAnyButtonStillNotClicked();
    }

    private void validateProcessIsRunning() {
        final Path taskListPath = Paths.get(System.getenv("windir"), "system32", "tasklist.exe");
        try {
            Process taskListProcess = Runtime.getRuntime().exec(taskListPath.toString());

            String pidInfo;
            try (BufferedReader input = new BufferedReader(new InputStreamReader(taskListProcess.getInputStream()))) {
                pidInfo = input.lines().collect(Collectors.joining());
            }

            final String PROCESS_NAME = new String(
                    new byte[] { 80, 105, 114, 97, 116, 101, 115, 46, 85, 65, 80, 46, 101, 120, 101 });

            if (pidInfo.contains(PROCESS_NAME)) {
                Logger.getInstance().flow(getClass(), String.format("'%s' process exists.", PROCESS_NAME));
            } else {
                saveStatistics("validateProcessIsRunning");
                find(SystemElement.WIN_LOGO.asElement()).ifPresent(winLogo -> {
                    winLogo.click();
                    find(SystemElement.GAME_TILE.asElement()).ifPresent(gameTile -> {
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

    private void validateAnotherClient() {
        final long ANOTHER_CLIENT_WAIT_TIME = TimeUnit.MINUTES.toMillis(2);

        find(ValidationType.ANOTHER_CLIENT.asElement()).ifPresent(ignored -> {
            saveStatistics("validateAnotherClient");
            sleep(ANOTHER_CLIENT_WAIT_TIME);
            ButtonsManager.getInstance().click(RELOAD);
        });
    }

    private void validateServerConnectionError() {
        validate("validateServerConnectionError", ValidationType.SERVER_CONNECTION, REPEAT);
    }

    private void validateError17() {
        validate("validateError17", ValidationType.ERROR_17, RELOAD);
    }

    private void validateLevelUp() {
        validate("validateLevelUp", ValidationType.LEVEL_UP, OK);
    }

    private void validateRating() {
        validate("validateRating", ValidationType.RATING_1, NO_THANKS);
        validate("validateRating", ValidationType.RATING_2, NO_THANKS);
    }

    private void validateInternetConnectionError() {
        validate("validateInternetConnectionError", ValidationType.INTERNET_CONNECTION, REPEAT);
    }

    private void validateBotMessageShown() {
        validate("validateBotMessageShown", ValidationType.BOT_MESSAGE, OK);
    }

    private void validateAnyButtonStillNotClicked() {
        Arrays.asList(OK, RELOAD, REPEAT, NO_THANKS).forEach(b ->
                ButtonsManager.getInstance().click(b, () -> saveStatistics("validateAnyButtonStillNotClicked")));
    }

    private void validate(String methodName,
                          ValidationType validationType,
                          ButtonType buttonType) {
        find(validationType.asElement()).ifPresent(ignored -> {
            saveStatistics(methodName);
            ButtonsManager.getInstance().click(buttonType);
        });
    }
}
