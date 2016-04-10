package org.poe.entities.fleet.exceptions;

import org.poe.entities.fleet.Fleet;

/**
 * @author Yevhen Fabizhevskyi
 * @date 07.04.2016.
 */
public class FleetNotFoundException extends FleetException {

    public FleetNotFoundException(Fleet fleet) {
        super(fleet.getDisplayName() + " is not found.");
    }
}
