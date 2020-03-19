package io.github.fabasoad.poe.entities.monsters;

import com.google.common.collect.Sets;
import io.github.fabasoad.poe.entities.resources.ResourceType;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

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
