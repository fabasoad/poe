package org.fabasoad.poe.entities.fleet;

import com.google.common.collect.Iterators;
import org.fabasoad.poe.core.UsedViaReflection;
import org.fabasoad.poe.entities.views.ViewAwareElementsManager;
import org.fabasoad.poe.entities.views.ViewType;
import org.fabasoad.poe.entities.buttons.ButtonType;
import org.fabasoad.poe.entities.buttons.ButtonsManager;
import org.fabasoad.poe.entities.monsters.Monster;
import org.fabasoad.poe.statistics.SupportedStatistics;
import org.sikuli.script.Match;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

/**
 * @author Yevhen Fabizhevskyi
 * @date 07.04.2016.
 */
@SupportedStatistics
public final class FleetManager extends ViewAwareElementsManager {

    private static final FleetManager instance = new FleetManager();

    public static FleetManager getInstance() {
        return instance;
    }

    private FleetManager() {
    }

    private int statistics = 0;

    @Override
    protected ViewType getViewType() {
        return ViewType.OCEAN;
    }

    @UsedViaReflection
    public String getStatistics() {
        return "Attacked monsters count: " + statistics;
    }

    public void sendFleets(Collection<Monster> monsters) {
        trySwitchView();
        // Check if repair required
        ButtonsManager.getInstance().clickMany(ButtonType.REPAIR);

        sendFleetsInternal(monsters);
    }

    private void sendFleetsInternal(Collection<Monster> monsters) {
        Collection<Match> foundMonsters = new ArrayList<>();
        findAllMonsters(monsters).ifPresent(i -> i.forEachRemaining(foundMonsters::add));

        findFreeFleets().ifPresent(fleetsIterator -> {
            if (foundMonsters.isEmpty()) {
                ButtonsManager.getInstance().click(ButtonType.RANDOM_SECTOR);
                sendFleetsInternal(monsters);
            } else {
                Iterator<Match> monstersIterator = foundMonsters.iterator();
                while (fleetsIterator.hasNext() && monstersIterator.hasNext()) {
                    attack(fleetsIterator.next(), monstersIterator.next());
                }
            }
        });
    }

    private void attack(Match fleet, Match monster) {
        statistics++;

        fleet.click();
        monster.click();
        ButtonsManager.getInstance().click(ButtonType.ATTACK);
    }

    private Optional<Iterator<Match>> findAllMonsters(Collection<Monster> monsters) {
        @SuppressWarnings("unchecked")
        final Iterator<Match>[] result = new Iterator[1];
        for (Monster monster : monsters) {
            findAll(Monster.getFolderName(), monster.getDisplayName(), monster.getImageName()).ifPresent(i ->
                    result[0] = Optional.ofNullable(result[0]).map(r -> Iterators.concat(r, i)).orElse(i));
        }
        return Optional.ofNullable(result[0]);
    }

    private Optional<Iterator<Match>> findFreeFleets() {
        return findAll(Fleet.getFolderName(), Fleet.FREE.getDisplayName(), Fleet.FREE.getImageName());
    }
}
