package io.github.fabasoad.poe.cmd;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import io.github.fabasoad.annotations.UsedViaReflection;

@UsedViaReflection
public class OptionFleet extends Option {

    private static String COMMAND = "fl";

    OptionFleet() {
        super(COMMAND, "fleet", false, "Command to send the fleet.");
    }

    public static boolean has(CommandLine cmd) {
        return cmd.hasOption(COMMAND);
    }
}
