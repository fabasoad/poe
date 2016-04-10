package org.poe.entities.fleet.exceptions;

import com.sun.deploy.util.StringUtils;
import org.poe.entities.fleet.Monster;

import java.util.Arrays;

/**
 * @author Yevhen Fabizhevskyi
 * @date 07.04.2016.
 */
public class MonsterNotFoundException extends FleetException {

    public MonsterNotFoundException(Monster[] monsters) {
        super(StringUtils.join(Arrays.asList(monsters), ", ") + " is (are) not found.");
    }
}
