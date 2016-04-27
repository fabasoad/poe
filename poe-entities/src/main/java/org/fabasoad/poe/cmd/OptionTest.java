package org.fabasoad.poe.cmd;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.fabasoad.poe.cmd.config.OptionGroup;
import org.fabasoad.poe.cmd.config.OptionGroupBy;
import org.fabasoad.poe.core.UsedViaReflection;

/**
 * @author Yevhen Fabizhevskyi
 * @date 21.04.2016.
 */
@UsedViaReflection
@OptionGroupBy(OptionGroup.CONFIG)
public class OptionTest extends Option {

    private static final String COMMAND = "t";

    OptionTest() {
        super(COMMAND, "test", false, "Command to test the lib.");
    }

    public static boolean has(CommandLine cmd) {
        return cmd.hasOption(COMMAND);
    }
}
