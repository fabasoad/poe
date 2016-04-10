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

            if (cmd.hasOption("resources")) {
                ResourceManager.collect();
            }
            if (cmd.hasOption("food")) {
                FoodManager.growCarrot();
            }
            if (cmd.hasOption("fleet")) {
                if (cmd.getOptionValue("fleet", "free").equals("free")) {
                    FleetManager.sendFreeFleets();
                } else {
                    FleetManager.sendFleets();
                }
            }
        }
    }

    private static Options buildOptions() {
        Options options = new Options();
        options.addOption("fl", "fleet", true, "Command to send the fleet.");
        options.addOption("fo", "food", false, "Command to grow the food.");
        options.addOption("r", "resources", false, "Command to collect the resources.");
        return options;
    }
}
