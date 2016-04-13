package org.poe.entities.food;

import com.google.common.collect.Iterators;
import org.poe.entities.ButtonType;
import org.poe.entities.ElementsManager;
import org.sikuli.script.Match;
import org.sikuli.script.Region;

import java.util.Iterator;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.poe.entities.food.FoodType.*;

/**
 * @author Yevhen Fabizhevskyi
 * @date 05.04.2016.
 */
public class FoodManager extends ElementsManager {

    private static final FoodManager instance = new FoodManager();

    public static FoodManager getInstance() {
        return instance;
    }

    private FoodManager() {
    }

    public void growCarrot() {
        grow(new FoodType[] { CARROT }, CARROT);
    }

    private void grow(FoodType[] foodToCollect, FoodType foodToGrow) {
        findAllFoodToCollect(foodToCollect).ifPresent(iterator -> {
            while (iterator.hasNext()) {
                iterator.next().click();
            }
        });

        findEmptyFields().ifPresent(emptyFieldIterator -> {
            while (emptyFieldIterator.hasNext()) {
                emptyFieldIterator.next().click();
                findCollectButton().ifPresent(matchCollectButton -> {
                    matchCollectButton.click();
                    findToGrow(foodToGrow).ifPresent(Region::click);
                });
            }
        });
    }

    private Optional<Match> findToGrow(FoodType foodType) {
        return find("food", foodType.getDisplayName(), foodType.getGrowImageName());
    }

    private Optional<Iterator<Match>> findEmptyFields() {
        final String EMPTY_FIELD_IMAGE_NAME = "empty_field";
        final String EMPTY_FIELD_DISPLAY_NAME = "Empty field";

        return findAll("food", EMPTY_FIELD_DISPLAY_NAME, EMPTY_FIELD_IMAGE_NAME);
    }

    private Optional<Match> findCollectButton() {
        return find("buttons", ButtonType.COLLECT_FOOD.getDisplayName(), ButtonType.COLLECT_FOOD.getImageName());
    }

    private Optional<Iterator<Match>> findAllFoodToCollect(FoodType[] foodTypes) {
        @SuppressWarnings("unchecked")
        final Iterator<Match>[] result = new Iterator[1];
        for (FoodType foodType : foodTypes) {
            findAll("food", foodType.getDisplayName(), foodType.getCollectImageName()).ifPresent(iterator ->
                    result[0] = result[0] == null ? iterator : Iterators.concat(result[0], iterator));
        }
        return Optional.ofNullable(result[0]);
    }
}
