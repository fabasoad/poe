package org.poe.entities.food;

import org.poe.entities.ElementsManager;
import org.sikuli.script.Match;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.poe.entities.food.FoodType.*;

/**
 * @author Yevhen Fabizhevskyi
 * @date 05.04.2016.
 */
public class FoodManager extends ElementsManager {

    private final static long COLLECT_WAIT_TIME = TimeUnit.SECONDS.toMillis(1);
    private final static long GROW_WAIT_TIME = TimeUnit.SECONDS.toMillis(1);

    @SuppressWarnings("unused")
    public static void grow() {
        grow(FoodType.values(), CARROT);
    }

    public static void growCarrot() {
        grow(new FoodType[] { CARROT }, CARROT);
    }

    private static void grow(FoodType[] foodToCollect, FoodType foodToGrow) {
        int index = 0;
        while (index < foodToCollect.length) {
            while (true) {
                Optional<Match> matchToCollect = findToCollect(foodToCollect[index]);
                if (matchToCollect.isPresent()) {
                    matchToCollect.get().click();
                    if (!sleep(FoodManager.class, COLLECT_WAIT_TIME)) {
                        break;
                    }
                } else {
                    break;
                }
            }
            while (true) {
                Optional<Match> emptyField = findEmptyField();
                if (emptyField.isPresent()) {
                    emptyField.get().click();
                    Optional<Match> buttonCollect = findButtonCollect();
                    if (buttonCollect.isPresent()) {
                        buttonCollect.get().click();
                        Optional<Match> matchToGrow = findToGrow(foodToGrow);
                        if (matchToGrow.isPresent()) {
                            matchToGrow.get().click();
                        }
                    } else {
                        break;
                    }
                    if (!sleep(FoodManager.class, GROW_WAIT_TIME)) {
                        break;
                    }
                } else {
                    break;
                }
            }
            index++;
        }
    }

    private static Optional<Match> findToGrow(FoodType foodType) {
        return find(FoodManager.class, "food", foodType.getDisplayName(), foodType.getGrowImageName());
    }

    private static Optional<Match> findToCollect(FoodType foodType) {
        return find(FoodManager.class, "food", foodType.getDisplayName(), foodType.getCollectImageName());
    }

    private static Optional<Match> findEmptyField() {
        final String EMPTY_FIELD_IMAGE_NAME = "empty_field";
        final String EMPTY_FIELD_DISPLAY_NAME = "Empty field";

        return find(FoodManager.class, "food", EMPTY_FIELD_DISPLAY_NAME, EMPTY_FIELD_IMAGE_NAME);
    }

    private static Optional<Match> findButtonCollect() {
        final String BUTTON_COLLECT_IMAGE_NAME = "button_collect";
        final String BUTTON_COLLECT_DISPLAY_NAME = "'Collect' button";

        return find(FoodManager.class, "food", BUTTON_COLLECT_DISPLAY_NAME, BUTTON_COLLECT_IMAGE_NAME);
    }
}
