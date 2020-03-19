package io.github.fabasoad.poe.cmd;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import io.github.fabasoad.annotations.UsedViaReflection;

@UsedViaReflection
public class OptionTest extends Option {

    private static final String COMMAND = "t";

    OptionTest() {
        super(COMMAND, "test", false, "Command to test the lib.");
    }

    public static boolean has(CommandLine cmd) {
        return cmd.hasOption(COMMAND);
    }
}
