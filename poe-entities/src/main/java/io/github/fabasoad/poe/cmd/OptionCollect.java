package io.github.fabasoad.poe.cmd;

import org.apache.commons.cli.CommandLine;
import io.github.fabasoad.annotations.UsedViaReflection;
import io.github.fabasoad.poe.entities.food.FoodType;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@UsedViaReflection
public class OptionCollect extends OptionBase {

    private static final String COMMAND = "c";

    OptionCollect() {
        super(COMMAND, "collect", true, buildDescription());
    }

    private static String buildDescription() {
        return String.format("Command to collect the food. Used only in combination with -fo command.%1$s" +
                "Usage: -fo -%2$s <food_1,food_2,...>.%1$sDefault: %3$s.%1$sPossible values: %4$s",
                System.getProperty("line.separator"), COMMAND,
                DEFAULT.getProperty(COMMAND, FoodType.defaultToCollectAsString()), FoodType.valuesAsString());
    }

    public static Collection<FoodType> parse(CommandLine cmd) {
        return Arrays.stream(getPropertyOrDefault(cmd, COMMAND, FoodType.defaultToCollectAsString()).split(","))
                    .map(v -> FoodType.valueOf(v.trim().toUpperCase()))
                    .collect(Collectors.toList());
    }
}
