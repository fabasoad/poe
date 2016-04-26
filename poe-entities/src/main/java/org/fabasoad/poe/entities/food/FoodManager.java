package org.fabasoad.poe.entities.food;

import com.google.common.collect.Iterators;
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
        return statistics > 0 ? "Collected food count: " + statistics : "";
    }

    public void collectAndGrow(Collection<FoodType> foodToCollect, FoodType foodToGrow) {
        trySwitchView();
        findAllFoodToCollect(foodToCollect).ifPresent(i -> i.forEachRemaining(Region::click));

        findAll(FieldType.EMPTY.asElement()).ifPresent(i -> {
            while (i.hasNext()) {
                i.next().click();
                statistics++;
                ButtonsManager.getInstance().click(
                        ButtonType.COLLECT_FOOD,
                        () -> find(foodToGrow.asElementToGrow()).ifPresent(Region::click));
            }
        });
    }

    private Optional<Iterator<Match>> findAllFoodToCollect(Collection<FoodType> foodTypes) {
        @SuppressWarnings("unchecked")
        final Iterator<Match>[] result = new Iterator[1];
        for (FoodType foodType : foodTypes) {
            findAll(foodType.asElementToCollect()).ifPresent(i ->
                    result[0] = Optional.ofNullable(result[0]).map(r -> Iterators.concat(r, i)).orElse(i));
        }
        return Optional.ofNullable(result[0]);
    }
}
