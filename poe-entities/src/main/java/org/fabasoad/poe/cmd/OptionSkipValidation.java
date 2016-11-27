package org.fabasoad.poe.cmd;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.fabasoad.annotations.UsedViaReflection;

/**
 * @author Yevhen Fabizhevskyi
 * @date 21.04.2016.
 */
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
