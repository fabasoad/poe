package io.github.fabasoad.poe.entities.food;

import io.github.fabasoad.annotations.UsedViaReflection;
import io.github.fabasoad.poe.entities.views.ViewAwareElementsManager;
import io.github.fabasoad.poe.entities.views.ViewType;
import io.github.fabasoad.poe.entities.buttons.ButtonType;
import io.github.fabasoad.poe.entities.buttons.ButtonsManager;
import io.github.fabasoad.poe.statistics.SupportedStatistics;
import org.sikuli.script.Match;
import org.sikuli.script.Region;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@SupportedStatistics
public final class FoodManager extends ViewAwareElementsManager {

    private static final FoodManager instance = new FoodManager();

    @UsedViaReflection
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

        forEachEmptyField(field -> {
            field.click();
            statistics++;
            ButtonsManager.getInstance().click(
                    ButtonType.COLLECT_FOOD,
                    () -> find(foodToGrow.asElementToGrow()).ifPresent(Region::click));
        });
    }

    private Optional<Iterator<Match>> findAllFoodToCollect(Collection<FoodType> foodTypes) {
        return findAll(foodTypes.stream().map(FoodType::asElementToCollect).collect(Collectors.toList()));
    }

    private void forEachEmptyField(Consumer<? super Match> consumer) {
        findAll(FieldType.EMPTY.asElement()).ifPresent(i -> i.forEachRemaining(consumer));
    }
}
