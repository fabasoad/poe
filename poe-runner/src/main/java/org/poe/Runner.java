package org.poe;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.poe.entities.fleet.Fleet;
import org.poe.entities.fleet.FleetManager;
import org.poe.entities.fleet.Monster;
import org.poe.entities.food.FoodManager;
import org.poe.entities.resources.ResourceManager;
import org.poe.entities.validation.ValidationManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Yevhen Fabizhevskyi
 * @date 26.03.2016.
 */
public class Runner {

    public static void main(String[] args) throws ParseException {
        CommandLineParser parser = new BasicParser();
        CommandLine cmd = parser.parse(buildOptions(), args);

        while (true) {
            ValidationManager.validateAnotherClient();
            ValidationManager.validateServerConnectionError();
            ValidationManager.validateError17();

            if (cmd.hasOption("resources")) {
                ResourceManager.collect();
            }
            if (cmd.hasOption("food")) {
                FoodManager.growCarrot();
            }
            if (cmd.hasOption("fleet")) {
                Collection<Monster> monsters = Arrays.asList(Monster.FISHANGER_1, Monster.GIANT_AGLA_KILLER_1);
                if (cmd.getOptionValue("fleet", "free").equals("free")) {
                    FleetManager.sendFleets(Collections.singletonList(Fleet.FREE), monsters);
                } else {
                    FleetManager.sendFleets(Stream.of(Fleet.values())
                            .filter(f -> f != Fleet.FREE).collect(Collectors.toList()), monsters);
                }
            }
        }
    }

    private static Options buildOptions() {
        Options options = new Options();
        options.addOption("fl", "fleet", true, "Command to send the fleet.");
        options.addOption("m", "monsters", true, "Command to send the fleet.");
        options.addOption("fo", "food", false, "Command to grow the food.");
        options.addOption("r", "resources", false, "Command to collect the resources.");
        return options;
    }
}
