package org.fabasoad.poe.cmd;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.fabasoad.annotations.UsedViaReflection;
import org.fabasoad.poe.entities.food.FoodType;

/**
 * @author Yevhen Fabizhevskyi
 * @date 21.04.2016.
 */
@UsedViaReflection
public class OptionFood extends Option {

    private static final String COMMAND = "fo";

    OptionFood() {
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
