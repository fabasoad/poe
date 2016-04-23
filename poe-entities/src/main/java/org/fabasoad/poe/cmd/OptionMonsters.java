package org.fabasoad.poe.cmd;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.fabasoad.poe.entities.monsters.Monster;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Yevhen Fabizhevskyi
 * @date 21.04.2016.
 */
public class OptionMonsters extends Option {

    private static final String COMMAND = "m";

    public OptionMonsters() {
        super(COMMAND, "monsters", true, buildMonstersDescription());
    }

    private static String buildMonstersDescription() {
        return String.format("List of monsters to attack. Used only in combination with -fl command.%1$s" +
                "Usage: -fl -m <monster_1,monster2,...>.%1$sDefault: %2$s.%1$sPossible values: %3$s.",
                System.getProperty("line.separator"), Monster.defaultAsString(), Monster.valuesAsString());
    }

    public static Collection<Monster> parse(CommandLine cmd) {
        return Arrays.stream(cmd.getOptionValue(COMMAND, Monster.defaultAsString()).split(","))
                    .map(v -> Monster.valueOf(v.trim().toUpperCase()))
                    .collect(Collectors.toList());
    }
}