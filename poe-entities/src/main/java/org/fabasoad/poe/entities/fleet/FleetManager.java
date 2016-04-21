package org.fabasoad.poe.entities.fleet;

import com.google.common.collect.Iterators;
import org.apache.commons.lang3.tuple.Pair;
import org.fabasoad.poe.core.UsedViaReflection;
import org.fabasoad.poe.entities.views.ViewAwareElementsManager;
import org.fabasoad.poe.entities.views.ViewType;
import org.fabasoad.poe.entities.buttons.ButtonType;
import org.fabasoad.poe.entities.buttons.ButtonsManager;
import org.fabasoad.poe.entities.monsters.Monster;
import org.fabasoad.poe.statistics.SupportedStatistics;
import org.sikuli.script.Match;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @SuppressWarnings("unchecked")
    private FleetManager() {
        Pair<String, Integer> attackPair = Pair.of("Attacked monsters count: ", 0);
        Pair<String, Integer> repairPair = Pair.of("Repairing count: ", 0);
        statistics = new Pair[] { attackPair, repairPair };
    }

    private final Pair<String, Integer>[] statistics;

    @Override
    protected ViewType getViewType() {
        return ViewType.OCEAN;
    }

    @UsedViaReflection
    public String getStatistics() {
        return Arrays.stream(statistics)
                .filter(pair -> pair.getValue() > 0)
                .map(pair -> pair.getKey() + pair.getValue() + ".")
                .collect(Collectors.joining(System.getProperty("line.separator")));
    }

    public void sendFleets(Collection<Monster> monsters) {
        trySwitchView();
        // Check if repair required
        ButtonsManager.getInstance().findAll(ButtonType.REPAIR).ifPresent(i -> {
            while (i.hasNext()) {
                statistics[1].setValue(statistics[1].getValue() + 1);
                i.next().click();
            }
        });

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
        statistics[0].setValue(statistics[0].getValue() + 1);

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
