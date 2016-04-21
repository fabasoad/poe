package org.fabasoad.poe.cmd;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

/**
 * @author Yevhen Fabizhevskyi
 * @date 21.04.2016.
 */
public class OptionSkipValidation extends Option {

    private static final String COMMAND = "sv";

    public OptionSkipValidation() {
        super(COMMAND, "skipValidation", false, "Command to skip validation.");
    }

    public static boolean has(CommandLine cmd) {
        return cmd.hasOption(COMMAND);
    }
}
