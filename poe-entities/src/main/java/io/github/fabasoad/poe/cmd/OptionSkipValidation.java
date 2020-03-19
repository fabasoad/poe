package io.github.fabasoad.poe.cmd;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import io.github.fabasoad.annotations.UsedViaReflection;

@UsedViaReflection
public class OptionSkipValidation extends Option {

    private static final String COMMAND = "sv";

    OptionSkipValidation() {
        super(COMMAND, "skip-validation", false, "Command to skip validation.");
    }

    public static boolean has(CommandLine cmd) {
        return cmd.hasOption(COMMAND);
    }
}
