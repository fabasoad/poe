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
import org.fabasoad.poe.entities.food.FoodType;
import org.fabasoad.poe.entities.monsters.Monster;
import org.fabasoad.poe.entities.resources.ResourceManager;
import org.fabasoad.poe.entities.temp.TempManager;
import org.fabasoad.poe.entities.validation.ValidationManager;
import org.fabasoad.poe.statistics.StatisticsCollector;
import org.fabasoad.poe.utils.ReflectionUtils;
import org.fabasoad.poe.utils.StreamUtils;
import org.sikuli.script.ImagePath;

import java.util.ArrayList;
import java.util.Collection;

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

        Collection<Runnable> actions = new ArrayList<>();
        if (OptionTest.has(cmd)) {
            actions.add(TempManager.getInstance()::test);
        } else {
            if (!OptionSkipValidation.has(cmd)) {
                actions.add(ValidationManager.getInstance()::validateAll);
            }

            if (OptionResources.has(cmd)) {
                actions.add(ResourceManager.getInstance()::collect);
            }
            if (OptionFood.has(cmd)) {
                Collection<FoodType> foodToCollect = OptionCollect.parse(cmd);
                FoodType foodToGrow = OptionGrow.parse(cmd);
                actions.add(() -> FoodManager.getInstance().collectAndGrow(foodToCollect, foodToGrow));
            }
            if (OptionFleet.has(cmd)) {
                Collection<Monster> monsters = OptionMonsters.parse(cmd);
                actions.add(() -> FleetManager.getInstance().sendFleets(monsters));
            }
        }

        while (true) {
            actions.forEach(Runnable::run);
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
