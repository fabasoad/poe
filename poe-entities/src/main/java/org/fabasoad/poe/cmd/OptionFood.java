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
        super(COMMAND, "food", true, buildFoodDescription());
    }

    public static boolean has(CommandLine cmd) {
        return cmd.hasOption(COMMAND);
    }

    private static String buildFoodDescription() {
        return String.format("Command to grow the food. Usage: <command> grow=<food_1>;collect=<food_1,food_2,...>" +
                "%1$sDefault to grow: %2$s.%1$sDefault to collect: %3$s.%1$sPossible values: %4$s.",
                System.getProperty("line.separator"), FoodType.defaultToGrowAsString(),
                FoodType.defaultToCollectAsString(), FoodType.valuesAsString());
    }

    public static Pair<Collection<FoodType>, FoodType> parse(CommandLine cmd) {
        Collection<FoodType> foodToCollect = null;
        FoodType foodToGrow = null;
        boolean applyDefaultValues = false;
        if (cmd.hasOption("fo")) {
            final String KEYWORD_COLLECT = "collect";
            final String KEYWORD_GROW = "grow";

            Optional<String[]> resultCollect = find(cmd, KEYWORD_COLLECT);
            Optional<String[]> resultGrow = find(cmd, KEYWORD_GROW);

            if (resultCollect.isPresent() && resultGrow.isPresent()) {
                try {
                    foodToCollect = Arrays.stream(resultCollect.get())
                            .map(v -> FoodType.valueOf(v.trim().toUpperCase()))
                            .collect(Collectors.toList());
                    foodToGrow = FoodType.valueOf(resultGrow.get()[0].trim().toUpperCase());
                } catch (IllegalArgumentException e) {
                    Logger.getInstance().warning(OptionFood.class, e.getMessage() + ". Default values applied.");
                    applyDefaultValues = true;
                }
            } else {
                Logger.getInstance().warning(
                        OptionFood.class, "'fo' argument is found but value is incorrect. Default values applied.");
                applyDefaultValues = true;
            }
        } else {
            applyDefaultValues = true;
        }
        if (applyDefaultValues) {
            foodToCollect = FoodType.defaultToCollectAsCollection();
            foodToGrow = FoodType.defaultToGrow();
        }
        return Pair.of(foodToCollect, foodToGrow);
    }

    private static Optional<String[]> find(CommandLine cmd, String key) {
        Matcher matcher = Pattern.compile(String.format("%s=([a-zA-Z,]+)", key)).matcher(cmd.getOptionValue("fo"));
        return Optional.ofNullable(matcher.find() ? matcher.group(0).split("=")[1].split(",") : null);
    }
}
