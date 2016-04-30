package org.fabasoad.poe.cmd;

import org.apache.commons.cli.CommandLine;
import org.fabasoad.poe.core.UsedViaReflection;

/**
 * @author Yevhen Fabizhevskyi
 * @date 21.04.2016.
 */
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
