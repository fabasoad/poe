package io.github.fabasoad.poe.cmd;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import io.github.fabasoad.annotations.UsedViaReflection;

@UsedViaReflection
public class OptionHelp extends Option {

    private static final String COMMAND = "h";

    OptionHelp() {
        super(COMMAND, "help", false, "Help information.");
    }

    public static boolean has(CommandLine cmd) {
        return cmd.hasOption(COMMAND);
    }

    public static void print(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.setWidth(formatter.getWidth() + 20);
        formatter.printHelp("java -jar <jar_name> <options>", options);
    }
}
