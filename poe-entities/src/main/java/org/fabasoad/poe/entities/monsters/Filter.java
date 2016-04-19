package org.fabasoad.poe.entities.monsters;

import com.google.common.collect.Sets;
import org.fabasoad.poe.entities.resources.ResourceType;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

/**
 * @author Yevhen Fabizhevskyi
 * @date 19.04.2016.
 */
class Filter {

    private ResourceType[] resourceTypes = {};
    private DangerLevel[] dangerLevels = {};

    void setResourceTypes(ResourceType... resourceTypes) {
        this.resourceTypes = resourceTypes;
    }

    void setDangerLevels(DangerLevel... dangerLevels) {
        this.dangerLevels = dangerLevels;
    }

    boolean match(Monster monster) {
        Set<ResourceType> intersection = Sets.intersection(
                Sets.newHashSet(monster.getResourceTypes()),
                Sets.newHashSet(resourceTypes)
        );

        return !intersection.isEmpty() && Arrays.stream(dangerLevels)
                .anyMatch(dangerLevel -> Objects.equals(dangerLevel, monster.getDangerLevel()));
    }
}
