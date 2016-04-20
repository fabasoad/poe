package org.fabasoad.poe;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.fabasoad.poe.entities.fleet.FleetManager;
import org.fabasoad.poe.entities.monsters.Monster;
import org.fabasoad.poe.entities.food.FoodManager;
import org.fabasoad.poe.entities.resources.ResourceManager;
import org.fabasoad.poe.entities.temp.TempManager;
import org.fabasoad.poe.entities.validation.ValidationManager;
import org.fabasoad.poe.statistics.StatisticsStorage;
import org.sikuli.script.ImagePath;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Yevhen Fabizhevskyi
 * @date 26.03.2016.
 */
public class Runner {

    private static void setUp() {
        ImagePath.add("org.fabasoad.poe.Runner/img");
        Runtime.getRuntime().addShutdownHook(new Thread(StatisticsStorage.getInstance()::print));
    }

    public static void main(String[] args) throws ParseException {
        setUp();

        CommandLineParser parser = new BasicParser();
        CommandLine cmd = parser.parse(buildOptions(), args);

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

            StatisticsStorage.getInstance().print();
        }
    }

    private static Collection<Monster> getMonsters(CommandLine cmd) {
        Collection<Monster> monsters;
        if (cmd.hasOption("monsters")) {
            String defaultMonsters = Monster.getDefaultAsString();
            monsters = Arrays.stream(cmd.getOptionValue("monsters", defaultMonsters).split(","))
                    .map(v -> Monster.valueOf(v.trim().toUpperCase()))
                    .collect(Collectors.toList());
        } else {
            monsters = Monster.getDefaultAsCollection();
        }
        return monsters;
    }

    private static Options buildOptions() {
        Options options = new Options();
        options.addOption("fl", "fleet", false, "Command to send the fleet.");
        options.addOption("m", "monsters", true, "List of monsters to attack.");
        options.addOption("fo", "food", false, "Command to grow the food.");
        options.addOption("r", "resources", false, "Command to collect the resources.");
        options.addOption("t", "test", false, "Command to test the lib.");
        options.addOption("sv", "skipValidation", false, "Command to skip validation.");
        return options;
    }
}
