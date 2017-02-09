package org.fabasoad.poe.entities.validation;

import org.fabasoad.annotations.UsedViaReflection;
import org.fabasoad.poe.ScreenInstance;
import org.fabasoad.poe.core.LoggerInstance;
import org.fabasoad.poe.entities.ElementsManager;
import org.fabasoad.poe.entities.buttons.ButtonType;
import org.fabasoad.poe.entities.buttons.ButtonsManager;
import org.fabasoad.poe.statistics.SupportedStatistics;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Location;
import org.sikuli.script.Match;
import org.sikuli.script.Region;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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
    private static final String PROCESS_NAME =
            new String(new byte[] { 77, 77, 71, 97, 109, 101, 46, 85, 65, 80, 46, 101, 120, 101});

    private final Runnable[] validators = {
        this::validateProcessIsRunning,
        this::validateScreenPlace,
        this::validateAnotherClient,
        this::validateServerConnectionError,
        this::validateError17,
        this::validateLevelUp,
        this::validateRating,
        this::validateInternetConnectionError,
        this::validateBotMessageShown,
        this::validateAnyButtonStillNotClicked
    };

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
        int counter = 0;
        while (counter < validators.length) {
            try {
                validators[counter].run();
                counter++;
            } catch (RerunValidationException ignored) {
                killApplication();
                counter = 0;
            }
        }
    }

    private void validateProcessIsRunning() {
        final Path taskListPath = Paths.get(System.getenv("windir"), "system32", "tasklist.exe");
        try {
            Process taskListProcess = Runtime.getRuntime().exec(taskListPath.toString());

            String pidInfo;
            try (BufferedReader input = new BufferedReader(new InputStreamReader(taskListProcess.getInputStream()))) {
                pidInfo = input.lines().collect(Collectors.joining());
            }

            if (pidInfo.contains(PROCESS_NAME)) {
                LoggerInstance.get().flow(getClass(), String.format("'%s' process exists.", PROCESS_NAME));
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
            LoggerInstance.get().error(getClass(), e.getMessage());
        }
    }

    private void validateScreenPlace() {
        final int SCREEN_CHECK_WIDTH = 150;
        final int SCREEN_CHECK_HEIGHT = 150;

        final Rectangle screenBounds = ScreenInstance.get().getBounds();
        final int SCREEN_DIFF_X = (int) (screenBounds.getWidth() - SCREEN_CHECK_WIDTH) / 2;
        final int SCREEN_DIFF_Y = (int) (screenBounds.getHeight() - SCREEN_CHECK_HEIGHT) / 2;

        final Region region = ScreenInstance.get()
                .newRegion(new Location(SCREEN_DIFF_X, SCREEN_DIFF_Y), SCREEN_CHECK_WIDTH, SCREEN_CHECK_HEIGHT);
        if (!find(ValidationType.SCREEN_POINT.asElement(), region).isPresent()) {
            saveStatistics("validateScreenPlace");
            Optional<Match> point = find(ValidationType.SCREEN_POINT.asElement());
            if (point.isPresent()) {
                try {
                    ScreenInstance.get().dragDrop(point, region);
                } catch (FindFailed e) {
                    LoggerInstance.get().error(getClass(), e.getMessage());
                }
            } else {
                throw new RerunValidationException();
            }
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

    private static void killApplication() {
        try {
            Runtime.getRuntime().exec("taskkill /F /IM " + PROCESS_NAME);
        } catch (IOException e) {
            LoggerInstance.get().error(ValidationManager.class, e.getMessage());
        }
    }
}
