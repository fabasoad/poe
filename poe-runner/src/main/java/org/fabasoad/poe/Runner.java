package org.fabasoad.poe;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.tuple.Pair;
import org.fabasoad.poe.core.Logger;
import org.fabasoad.poe.entities.fleet.FleetManager;
import org.fabasoad.poe.entities.food.FoodType;
import org.fabasoad.poe.entities.monsters.Monster;
import org.fabasoad.poe.entities.food.FoodManager;
import org.fabasoad.poe.entities.resources.ResourceManager;
import org.fabasoad.poe.entities.temp.TempManager;
import org.fabasoad.poe.entities.validation.ValidationManager;
import org.fabasoad.poe.statistics.StatisticsCollector;
import org.sikuli.script.ImagePath;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Yevhen Fabizhevskyi
 * @date 26.03.2016.
 */
public class Runner {

    private static void setUp() {
        ImagePath.add("org.fabasoad.poe.Runner/img");
        Runtime.getRuntime().addShutdownHook(new Thread(StatisticsCollector.getInstance()::print));
    }

    public static void main(String[] args) throws ParseException {
        CommandLineParser parser = new BasicParser();
        Options cmdOptions = buildOptions();
        CommandLine cmd = parser.parse(cmdOptions, args);

        if (cmd.hasOption("h")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("java -jar <jar_name> <options>", cmdOptions);
            return;
        }

        setUp();

        while (true) {
            if (cmd.hasOption("test")) {
                TempManager.getInstance().test();
                continue;
            }
            if (!cmd.hasOption("sv")) {
                ValidationManager.getInstance().validateAll();
            }

            if (cmd.hasOption("r")) {
                ResourceManager.getInstance().collect();
            }
            if (cmd.hasOption("fo")) {
                FoodManager.getInstance().grow(getFood(cmd));
            }
            if (cmd.hasOption("fl")) {
                FleetManager.getInstance().sendFleets(getMonsters(cmd));
            }

            StatisticsCollector.getInstance().print();
        }
    }

    private static Pair<Collection<FoodType>, FoodType> getFood(CommandLine cmd) {
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
                            .map(v -> FoodType.valueOf(v.toUpperCase()))
                            .collect(Collectors.toList());
                    foodToGrow = FoodType.valueOf(resultGrow.get()[0].toUpperCase());
                } catch (IllegalArgumentException e) {
                    Logger.getInstance().warning(Runner.class, e.getMessage() + ". Default values applied.");
                    applyDefaultValues = true;
                }
            } else {
                Logger.getInstance().warning(
                        Runner.class, "'fo' argument is found but value is incorrect. Default values applied.");
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

    private static Collection<Monster> getMonsters(CommandLine cmd) {
        Collection<Monster> monsters;
        if (cmd.hasOption("m")) {
            String defaultMonsters = Monster.defaultAsString();
            monsters = Arrays.stream(cmd.getOptionValue("monsters", defaultMonsters).split(","))
                    .map(v -> Monster.valueOf(v.trim().toUpperCase()))
                    .collect(Collectors.toList());
        } else {
            monsters = Monster.defaultAsCollection();
        }
        return monsters;
    }

    private static Options buildOptions() {
        Options options = new Options();
        options.addOption("h", "help", false, "Help information.");
        options.addOption("fl", "fleet", false, "Command to send the fleet.");
        String monstersDescription = String.format("List of monsters to attack.%1$sDefault: %2$s.%1$sPossible values: %3$s.",
                System.getProperty("line.separator"), Monster.defaultAsString(), Monster.valuesAsString());
        options.addOption("m", "monsters", true, monstersDescription);
        String foodDescription = String.format("Command to grow the food. Usage: <command> grow=<food_1>;collect=" +
                "<food_1,food_2,...>%1$sDefault to grow: %2$s.%1$sDefault to collect: %3$s.%1$sPossible values: %4$s.",
                System.getProperty("line.separator"), FoodType.defaultToGrowAsString(),
                FoodType.defaultToCollectAsString(), FoodType.valuesAsString());
        options.addOption("fo", "food", true, foodDescription);
        options.addOption("r", "resources", false, "Command to collect the resources.");
        options.addOption("t", "test", false, "Command to test the lib.");
        options.addOption("sv", "skipValidation", false, "Command to skip validation.");
        return options;
    }
}
