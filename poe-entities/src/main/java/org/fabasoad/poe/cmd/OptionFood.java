package org.fabasoad.poe.cmd;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.tuple.Pair;
import org.fabasoad.poe.core.Logger;
import org.fabasoad.poe.entities.food.FoodType;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Yevhen Fabizhevskyi
 * @date 21.04.2016.
 */
public class OptionFood extends Option {

    private static final String COMMAND = "fo";

    public OptionFood() {
        super(COMMAND, "food", false, buildFoodDescription());
    }

    public static boolean has(CommandLine cmd) {
        return cmd.hasOption(COMMAND);
    }

    private static String buildFoodDescription() {
        return String.format("Command to collect and grow the food.%1$sDefault to grow: %2$s.%1$s" +
                "Default to collect: %3$s.", System.getProperty("line.separator"), FoodType.defaultToGrowAsString(),
                FoodType.defaultToCollectAsString());
    }
}
