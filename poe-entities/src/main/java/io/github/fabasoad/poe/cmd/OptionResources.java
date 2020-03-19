package io.github.fabasoad.poe.cmd;

import org.apache.commons.cli.CommandLine;
import io.github.fabasoad.annotations.UsedViaReflection;

@UsedViaReflection
public class OptionResources extends OptionBase {

    private static final String COMMAND = "r";

    OptionResources() {
        super(COMMAND, "resources", false, "Command to collect the resources.");
    }

    public static boolean has(CommandLine cmd) {
        return cmd.hasOption(COMMAND);
    }
}
