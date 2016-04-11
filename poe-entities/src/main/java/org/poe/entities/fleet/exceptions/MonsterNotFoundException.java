package org.poe.entities.fleet.exceptions;

import org.poe.entities.fleet.Monster;

/**
 * @author Yevhen Fabizhevskyi
 * @date 07.04.2016.
 */
public class MonsterNotFoundException extends FleetException {

    public MonsterNotFoundException(Monster monster) {
        super(monster.getDisplayName() + " is not found.");
    }
}
