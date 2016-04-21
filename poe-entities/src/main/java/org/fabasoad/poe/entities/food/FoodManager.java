package org.fabasoad.poe.entities.food;

import com.google.common.collect.Iterators;
import org.apache.commons.lang3.tuple.Pair;
import org.fabasoad.poe.core.UsedViaReflection;
import org.fabasoad.poe.entities.views.ViewAwareElementsManager;
import org.fabasoad.poe.entities.views.ViewType;
import org.fabasoad.poe.entities.buttons.ButtonType;
import org.fabasoad.poe.entities.buttons.ButtonsManager;
import org.fabasoad.poe.statistics.SupportedStatistics;
import org.sikuli.script.Match;
import org.sikuli.script.Region;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

import static org.fabasoad.poe.entities.food.FoodType.*;

/**
 * @author Yevhen Fabizhevskyi
 * @date 05.04.2016.
 */
@SupportedStatistics
public final class FoodManager extends ViewAwareElementsManager {

    private static final FoodManager instance = new FoodManager();

    public static FoodManager getInstance() {
        return instance;
    }

    private FoodManager() {
    }

    private int statistics = 0;

    @Override
    protected ViewType getViewType() {
        return ViewType.CITY;
    }

    @UsedViaReflection
    public String getStatistics() {
        return "Collected food count: " + statistics;
    }

    public void grow(Pair<Collection<FoodType>, FoodType> food) {
        trySwitchView();
        findAllFoodToCollect(food.getLeft()).ifPresent(i -> i.forEachRemaining(Region::click));

        findEmptyFields().ifPresent(i -> {
            while (i.hasNext()) {
                i.next().click();
                statistics++;
                ButtonsManager.getInstance().click(
                        ButtonType.COLLECT_FOOD,
                        () -> findToGrow(food.getRight()).ifPresent(Region::click));
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

    private Optional<Iterator<Match>> findAllFoodToCollect(Collection<FoodType> foodTypes) {
        @SuppressWarnings("unchecked")
        final Iterator<Match>[] result = new Iterator[1];
        for (FoodType foodType : foodTypes) {
            findAll(FoodType.getFolderName(), foodType.getDisplayName(), foodType.getCollectImageName()).ifPresent(i ->
                    result[0] = Optional.ofNullable(result[0]).map(r -> Iterators.concat(r, i)).orElse(i));
        }
        return Optional.ofNullable(result[0]);
    }
}
