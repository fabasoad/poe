package org.fabasoad.poe;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.fabasoad.poe.cmd.OptionCollect;
import org.fabasoad.poe.cmd.OptionFleet;
import org.fabasoad.poe.cmd.OptionFood;
import org.fabasoad.poe.cmd.OptionGrow;
import org.fabasoad.poe.cmd.OptionHelp;
import org.fabasoad.poe.cmd.OptionMonsters;
import org.fabasoad.poe.cmd.OptionResources;
import org.fabasoad.poe.cmd.OptionSkipValidation;
import org.fabasoad.poe.cmd.OptionTest;
import org.fabasoad.poe.core.Logger;
import org.fabasoad.poe.entities.fleet.FleetManager;
import org.fabasoad.poe.entities.food.FoodManager;
import org.fabasoad.poe.entities.resources.ResourceManager;
import org.fabasoad.poe.entities.temp.TempManager;
import org.fabasoad.poe.entities.validation.ValidationManager;
import org.fabasoad.poe.statistics.StatisticsCollector;
import org.fabasoad.poe.utils.ReflectionUtils;
import org.fabasoad.poe.utils.StreamUtils;
import org.sikuli.script.ImagePath;

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

        if (OptionHelp.has(cmd)) {
            OptionHelp.print(cmdOptions);
            return;
        }

        setUp();

        while (true) {
            if (OptionTest.has(cmd)) {
                TempManager.getInstance().test();
                continue;
            }
            if (!OptionSkipValidation.has(cmd)) {
                ValidationManager.getInstance().validateAll();
            }

            if (OptionResources.has(cmd)) {
                ResourceManager.getInstance().collect();
            }
            if (OptionFood.has(cmd)) {
                FoodManager.getInstance().collectAndGrow(OptionCollect.parse(cmd), OptionGrow.parse(cmd));
            }
            if (OptionFleet.has(cmd)) {
                FleetManager.getInstance().sendFleets(OptionMonsters.parse(cmd));
            }

            StatisticsCollector.getInstance().print();
        }
    }

    private static Options buildOptions() {
        Options options = new Options();
        ReflectionUtils.getSubTypesOf(Option.class).stream()
                .map(c -> StreamUtils.map(c, Class::newInstance, Runner::logException))
                .forEach(o -> o.ifPresent(options::addOption));
        return options;
    }

    private static void logException(Throwable e) {
        Logger.getInstance().error(Runner.class, e.getMessage());
    }
}
