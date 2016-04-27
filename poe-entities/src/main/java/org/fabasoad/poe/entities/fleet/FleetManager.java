package org.fabasoad.poe.entities.fleet;

import org.fabasoad.poe.core.UsedViaReflection;
import org.fabasoad.poe.entities.buttons.ButtonType;
import org.fabasoad.poe.entities.buttons.ButtonsManager;
import org.fabasoad.poe.entities.monsters.Monster;
import org.fabasoad.poe.entities.views.ViewAwareElementsManager;
import org.fabasoad.poe.entities.views.ViewType;
import org.fabasoad.poe.statistics.SupportedStatistics;
import org.sikuli.script.Match;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Yevhen Fabizhevskyi
 * @date 07.04.2016.
 */
@SupportedStatistics
public final class FleetManager extends ViewAwareElementsManager {

    private final Map<String, Integer> statistics;

    private static final String ATTACK_STATISTICS_KEY = "Attacked monsters count: ";
    private static final String REPAIR_STATISTICS_KEY = "Repairing count: ";

    private static final FleetManager instance = new FleetManager();

    public static FleetManager getInstance() {
        return instance;
    }

    private FleetManager() {
        statistics = new HashMap<>();
        statistics.put(ATTACK_STATISTICS_KEY, 0);
        statistics.put(REPAIR_STATISTICS_KEY, 0);
    }

    @Override
    protected ViewType getViewType() {
        return ViewType.OCEAN;
    }

    @UsedViaReflection
    public String getStatistics() {
        return statistics.entrySet().stream()
                .filter(e -> e.getValue() > 0)
                .map(e -> e.getKey() + e.getValue())
                .collect(Collectors.joining(System.getProperty("line.separator")));
    }

    private void updateStatistics(String key) {
        statistics.merge(key, 1, Integer::sum);
    }

    public void sendFleets(Collection<Monster> monsters) {
        // Try switching view to 'OCEAN'
        trySwitchView();
        // Send fleets to the journey
        sendFleetsInternal(monsters);
    }

    private void sendFleetsInternal(Collection<Monster> monsters) {
        Collection<Match> foundMonsters = new ArrayList<>();
        findAllMonsters(monsters).ifPresent(i -> i.forEachRemaining(foundMonsters::add));

        findAll(Fleet.FREE.asElement()).ifPresent(fleetsIterator -> {
            if (foundMonsters.isEmpty()) {
                ButtonsManager.getInstance().click(ButtonType.RANDOM_SECTOR, () -> sendFleetsInternal(monsters));
            } else {
                // Check if repair required
                ButtonsManager.getInstance().clickMany(ButtonType.REPAIR, () -> updateStatistics(REPAIR_STATISTICS_KEY));

                Iterator<Match> monstersIterator = foundMonsters.iterator();
                while (fleetsIterator.hasNext() && monstersIterator.hasNext()) {
                    attack(fleetsIterator.next(), monstersIterator.next());
                }
            }
        });
    }

    private void attack(Match fleet, Match monster) {
        updateStatistics(ATTACK_STATISTICS_KEY);

        fleet.click();
        monster.click();
        ButtonsManager.getInstance().click(ButtonType.ATTACK);
    }

    private Optional<Iterator<Match>> findAllMonsters(Collection<Monster> monsters) {
        return findAll(monsters.stream().map(Monster::asElement).collect(Collectors.toList()));
    }
}
