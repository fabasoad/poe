package io.github.fabasoad.poe.cmd;

import org.apache.commons.cli.CommandLine;
import io.github.fabasoad.annotations.UsedViaReflection;
import io.github.fabasoad.poe.entities.food.FoodType;

@UsedViaReflection
public class OptionGrow extends OptionBase {

    private static final String COMMAND = "g";

    OptionGrow() {
        super(COMMAND, "grow", true, buildDescription());
    }

    private static String buildDescription() {
        return String.format("Command to grow the food. Used only in combination with -fo command.%1$sUsage: -fo " +
                "-%2$s <food>.%1$sDefault: %3$s.%1$sPossible values: %4$s", System.getProperty("line.separator"),
                COMMAND, DEFAULT.getProperty(COMMAND, FoodType.defaultToGrowAsString()), FoodType.valuesAsString());
    }

    public static FoodType parse(CommandLine cmd) {
        return FoodType.valueOf(getPropertyOrDefault(cmd, COMMAND, FoodType.defaultToGrowAsString()).trim().toUpperCase());
    }
}
