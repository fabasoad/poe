package org.fabasoad.poe.cmd;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Eugene Fabizhevsky
 * @date 4/26/2016
 */
abstract class OptionBase extends Option {

    protected static Properties DEFAULT = new Properties();

    static {
        try (InputStream stream = OptionBase.class.getClassLoader().getResourceAsStream("default.properties")) {
            DEFAULT.load(stream);
        } catch (IOException e) {
            DEFAULT.clear();
        }
    }

    protected OptionBase(String opt, String longOpt, boolean hasArg, String description) throws IllegalArgumentException {
        super(opt, longOpt, hasArg, description);
    }

    protected static String getPropertyOrDefault(CommandLine cmd, String key, String defaultValue) {
        return cmd.getOptionValue(key, DEFAULT.getProperty(key, defaultValue));
    }
}
