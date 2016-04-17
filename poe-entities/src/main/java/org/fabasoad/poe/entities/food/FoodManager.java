package org.fabasoad.poe.entities.food;

import com.google.common.collect.Iterators;
import org.fabasoad.poe.entities.ViewAware;
import org.fabasoad.poe.entities.ViewType;
import org.fabasoad.poe.entities.buttons.ButtonType;
import org.fabasoad.poe.entities.ElementsManager;
import org.fabasoad.poe.entities.buttons.ButtonsManager;
import org.sikuli.script.Match;
import org.sikuli.script.Region;

import java.util.Iterator;
import java.util.Optional;

import static org.fabasoad.poe.entities.food.FoodType.*;

/**
 * @author Yevhen Fabizhevskyi
 * @date 05.04.2016.
 */
@ViewAware(type = ViewType.CITY)
public final class FoodManager extends ElementsManager {

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
        trySwitchView();
        findAllFoodToCollect(foodToCollect).ifPresent(i -> i.forEachRemaining(Region::click));

        findEmptyFields().ifPresent(i -> {
            while (i.hasNext()) {
                i.next().click();
                ButtonsManager.getInstance().click(
                        ButtonType.COLLECT_FOOD,
                        () -> findToGrow(foodToGrow).ifPresent(Region::click));
            }
        });
    }

    private Optional<Match> findToGrow(FoodType foodType) {
        return find(FoodType.getFolderName(), foodType.getDisplayName(), foodType.getGrowImageName());
    }

    private Optional<Iterator<Match>> findEmptyFields() {
        final String EMPTY_FIELD_IMAGE_NAME = "empty_field";
        final String EMPTY_FIELD_DISPLAY_NAME = "Empty field";

        return findAll("food", EMPTY_FIELD_DISPLAY_NAME, EMPTY_FIELD_IMAGE_NAME);
    }

    private Optional<Iterator<Match>> findAllFoodToCollect(FoodType[] foodTypes) {
        @SuppressWarnings("unchecked")
        final Iterator<Match>[] result = new Iterator[1];
        for (FoodType foodType : foodTypes) {
            findAll(FoodType.getFolderName(), foodType.getDisplayName(), foodType.getCollectImageName()).ifPresent(i ->
                    result[0] = Optional.ofNullable(result[0]).map(r -> Iterators.concat(r, i)).orElse(i));
        }
        return Optional.ofNullable(result[0]);
    }
}
