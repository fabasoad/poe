package org.poe.entities.food;

import org.poe.entities.ElementsManager;
import org.sikuli.script.Match;

import java.util.Optional;

import static org.poe.entities.food.FoodType.*;

/**
 * @author Yevhen Fabizhevskyi
 * @date 05.04.2016.
 */
public class FoodManager extends ElementsManager {

    private final static FoodType[] FOOD_TYPES = { CARROT, CABBAGE, WHEAT, GRAPE, RICE, OLIVES };

    public static void grow() {
        grow(FOOD_TYPES, CARROT);
    }

    public static void growCarrot() {
        grow(new FoodType[] { CARROT }, CARROT);
    }

    private static void grow(FoodType[] foodTypes, FoodType foodToGrow) {
        int index = 0;
        while (index < foodTypes.length) {
            Optional<Match> matchToCollect = findToCollect(foodTypes[index]);
            if (matchToCollect.isPresent()) {
                matchToCollect.get().click();
            } else {
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
                    }
                }
                index++;
            }
        }
    }

    private static Optional<Match> findToGrow(FoodType foodType) {
        return find(FoodManager.class, "garden", foodType.getDisplayName(), foodType.getGrowImageName());
    }

    private static Optional<Match> findToCollect(FoodType foodType) {
        return find(FoodManager.class, "garden", foodType.getDisplayName(), foodType.getCollectImageName());
    }

    private static Optional<Match> findEmptyField() {
        final String EMPTY_FIELD_IMAGE_NAME = "empty_field";
        final String EMPTY_FIELD_DISPLAY_NAME = "Empty field";

        return find(FoodManager.class, "garden", EMPTY_FIELD_DISPLAY_NAME, EMPTY_FIELD_IMAGE_NAME);
    }

    private static Optional<Match> findButtonCollect() {
        final String BUTTON_COLLECT_IMAGE_NAME = "button_collect";
        final String BUTTON_COLLECT_DISPLAY_NAME = "Button 'Collect'";

        return find(FoodManager.class, "garden", BUTTON_COLLECT_DISPLAY_NAME, BUTTON_COLLECT_IMAGE_NAME);
    }
}
