package org.fabasoad.poe.entities.fleet;

import com.google.common.collect.Iterators;
import org.fabasoad.poe.entities.buttons.ButtonType;
import org.fabasoad.poe.entities.ElementsManager;
import org.fabasoad.poe.entities.buttons.ButtonsManager;
import org.fabasoad.poe.entities.views.ViewType;
import org.fabasoad.poe.entities.views.ViewsManager;
import org.sikuli.script.Match;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

/**
 * @author Yevhen Fabizhevskyi
 * @date 07.04.2016.
 */
public class FleetManager extends ViewsManager {

    private static final FleetManager instance = new FleetManager();

    public static FleetManager getInstance() {
        return instance;
    }

    private FleetManager() {
    }

    @Override
    protected ViewType getCurrentView() {
        return ViewType.FLEET;
    }

    private final Collection<Match> attackedMonsters = new ArrayList<>();

    public void sendFleets(Collection<Monster> monsters) {
        // Check if we are on a 'Fleet' view
        goToCurrentView();
        // Check if repair required
        ButtonsManager.getInstance().clickMany(ButtonType.REPAIR);

        Collection<Match> foundMonsters = new ArrayList<>();
        findAllMonsters(monsters).ifPresent(monstersIterator -> {
            while (monstersIterator.hasNext()) {
                Match matchMonster = monstersIterator.next();
                if (!attackedMonsters.contains(matchMonster)) {
                    foundMonsters.add(matchMonster);
                }
            }
        });

        if (foundMonsters.isEmpty()) {
            ButtonsManager.getInstance().click(ButtonType.RANDOM_SECTOR);
        } else {
            findFreeFleets().ifPresent(fleetsIterator -> {
                attackedMonsters.clear();
                Iterator<Match> monstersIterator = foundMonsters.iterator();
                while (fleetsIterator.hasNext() && monstersIterator.hasNext()) {
                    attack(fleetsIterator.next(), monstersIterator.next());
                }
            });
        }
    }

    private void attack(Match fleet, Match monster) {
        attackedMonsters.add(monster);

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
