package org.fabasoad.poe;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.fabasoad.poe.entities.fleet.FleetManager;
import org.fabasoad.poe.entities.monsters.Monster;
import org.fabasoad.poe.entities.food.FoodManager;
import org.fabasoad.poe.entities.resources.ResourceManager;
import org.fabasoad.poe.entities.temp.TempManager;
import org.fabasoad.poe.entities.validation.ValidationManager;
import org.fabasoad.poe.statistics.StatisticsCollector;
import org.sikuli.script.ImagePath;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Yevhen Fabizhevskyi
 * @date 26.03.2016.
 */
public class Runner {

    private static Options cmdOptions;

    private static void setUp() {
        ImagePath.add("org.fabasoad.poe.Runner/img");

        Runtime.getRuntime().addShutdownHook(new Thread(StatisticsCollector.getInstance()::print));

        cmdOptions = buildOptions();
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java jar -<jar_name> <options>", cmdOptions);
    }

    public static void main(String[] args) throws ParseException {
        setUp();

        CommandLineParser parser = new BasicParser();
        CommandLine cmd = parser.parse(cmdOptions, args);

        while (true) {
            if (cmd.hasOption("test")) {
                TempManager.getInstance().test();
                continue;
            }
            if (!cmd.hasOption("sv")) {
                ValidationManager.getInstance().validateAll();
            }

            if (cmd.hasOption("resources")) {
                ResourceManager.getInstance().collect();
            }
            if (cmd.hasOption("food")) {
                FoodManager.getInstance().growCarrot();
            }
            if (cmd.hasOption("fleet")) {
                FleetManager.getInstance().sendFleets(getMonsters(cmd));
            }

            StatisticsCollector.getInstance().print();
        }
    }

    private static Collection<Monster> getMonsters(CommandLine cmd) {
        Collection<Monster> monsters;
        if (cmd.hasOption("monsters")) {
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
        options.addOption("fl", "fleet", false, "Command to send the fleet.");
        String monstersDescription = String.format("List of monsters to attack.%1$sDefault: %2$s.%1$sPossible values: %3$s.",
                System.getProperty("line.separator"), Monster.defaultAsString(), Monster.valuesAsString());
        options.addOption("m", "monsters", true, monstersDescription);
        options.addOption("fo", "food", false, "Command to grow the food.");
        options.addOption("r", "resources", false, "Command to collect the resources.");
        options.addOption("t", "test", false, "Command to test the lib.");
        options.addOption("sv", "skipValidation", false, "Command to skip validation.");
        return options;
    }
}
