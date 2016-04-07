package org.poe;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.poe.entities.fleet.FleetManager;
import org.poe.entities.food.FoodManager;
import org.poe.entities.resources.ResourceManager;
import org.poe.entities.validation.ValidationManager;

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

            if (cmd.hasOption("c")) {
                ResourceManager.collect();
            }
            if (cmd.hasOption("g")) {
                FoodManager.growCarrot();
            }
            if (cmd.hasOption("f")) {
                FleetManager.sendFreeFleets();
            }
        }
    }

    private static Options buildOptions() {
        Options options = new Options();
        options.addOption("f", "fleet", false, "Command to send the fleet.");
        options.addOption("c", "collect", false, "Command to collect the resources.");
        options.addOption("g", "grow", false, "Command to grow the food.");
        return options;
    }
}
