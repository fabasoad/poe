package io.github.fabasoad.poe;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import io.github.fabasoad.poe.cmd.*;
import io.github.fabasoad.poe.entities.fleet.farm.FarmType;
import io.github.fabasoad.poe.entities.fleet.FleetManager;
import io.github.fabasoad.poe.entities.food.FoodManager;
import io.github.fabasoad.poe.entities.food.FoodType;
import io.github.fabasoad.poe.entities.monsters.Monster;
import io.github.fabasoad.poe.entities.resources.ResourceManager;
import io.github.fabasoad.poe.entities.temp.TempManager;
import io.github.fabasoad.poe.entities.validation.ValidationManager;
import io.github.fabasoad.poe.statistics.StatisticsCollector;
import org.sikuli.script.ImagePath;

import java.util.ArrayList;
import java.util.Collection;

public class Runner {

    private static void setUp() {
        ImagePath.add("io.github.fabasoad.poe.Runner/img");
        Runtime.getRuntime().addShutdownHook(new Thread(StatisticsCollector.getInstance()::print));
    }

    public static void main(String[] args) throws ParseException {
        Options cmdOptions = OptionsCollector.getInstance().collect();
        final CommandLine cmd = new BasicParser().parse(cmdOptions, args);

        if (OptionHelp.has(cmd)) {
            OptionHelp.print(cmdOptions);
            return;
        }

        setUp();

        Collection<Runnable> actions = collectActions(cmd);

        while (true) {
            actions.forEach(Runnable::run);
            StatisticsCollector.getInstance().print();
        }
    }

    private static Collection<Runnable> collectActions(CommandLine cmd) {
        final Collection<Runnable> result = new ArrayList<>();
        if (OptionTest.has(cmd)) {
            result.add(TempManager.getInstance()::test);
        } else {
            if (!OptionSkipValidation.has(cmd)) {
                result.add(ValidationManager.getInstance()::validateAll);
            }
            if (OptionResources.has(cmd)) {
                result.add(ResourceManager.getInstance()::collect);
            }
            if (OptionFood.has(cmd)) {
                final Collection<FoodType> foodToCollect = OptionCollect.parse(cmd);
                final FoodType foodToGrow = OptionGrow.parse(cmd);
                result.add(() -> FoodManager.getInstance().collectAndGrow(foodToCollect, foodToGrow));
            }
            if (OptionFleet.has(cmd)) {
                final Collection<Monster> monsters = OptionMonsters.parse(cmd);
                final FarmType farmType = OptionFarmType.parse(cmd);
                result.add(() -> FleetManager.getInstance().sendFleets(farmType, monsters));
            }
        }
        return result;
    }
}
