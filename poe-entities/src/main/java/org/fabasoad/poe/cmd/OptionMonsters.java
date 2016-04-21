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

    public static boolean has(CommandLine cmd) {
        return cmd.hasOption(COMMAND);
    }

    private static String buildMonstersDescription() {
        return String.format("List of monsters to attack. Used only in combination with -fl command.%1$s" +
                "Default: %2$s.%1$sPossible values: %3$s.", System.getProperty("line.separator"),
                Monster.defaultAsString(), Monster.valuesAsString());
    }

    public static Collection<Monster> parse(CommandLine cmd) {
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
}
