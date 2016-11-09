package org.fabasoad.poe.entities.fleet.farm;

import org.fabasoad.poe.entities.ElementsManager;
import org.fabasoad.poe.entities.buttons.ButtonType;
import org.fabasoad.poe.entities.buttons.ButtonsManager;
import org.fabasoad.poe.entities.fleet.FleetStatistics;
import org.fabasoad.poe.entities.monsters.Monster;
import org.sikuli.script.Match;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author efabizhevsky
 * @date 11/8/2016.
 */
public abstract class FarmStrategy {

    final ElementsManager elementsManager;
    Optional<FleetStatistics> statistics;

    FarmStrategy(ElementsManager elementsManager) {
        this.elementsManager = elementsManager;
    }

    public abstract void sendFleets(Collection<Monster> monsters);

    public void attachStatistics(FleetStatistics statistics) {
        this.statistics = Optional.ofNullable(statistics);
    }

    Optional<Iterator<Match>> findAllMonsters(Collection<Monster> monsters) {
        return elementsManager.findAll(monsters.stream().map(Monster::asElement).collect(Collectors.toList()));
    }

    void attack(Match fleet, Match monster) {
        statistics.ifPresent(FleetStatistics::updateAttackStatistics);

        fleet.click();
        monster.click();
        ButtonsManager.getInstance().click(ButtonType.ATTACK);
    }
}
