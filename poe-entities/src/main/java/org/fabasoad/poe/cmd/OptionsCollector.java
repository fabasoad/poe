package org.fabasoad.poe.cmd;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.fabasoad.poe.core.Logger;
import org.fabasoad.poe.utils.ReflectionUtils;
import org.fabasoad.poe.utils.StreamUtils;

import java.lang.reflect.Modifier;

/**
 * @author Eugene Fabizhevsky
 * @date 4/27/2016
 */
public class OptionsCollector {

    private static OptionsCollector instance = new OptionsCollector();

    public static OptionsCollector getInstance() {
        return instance;
    }

    private OptionsCollector() {
    }

    public Options collect() {
        Options options = new Options();
        ReflectionUtils.getSubTypesOf(Option.class).stream()
                .filter(c -> !Modifier.isAbstract(c.getModifiers()))
                .map(c -> StreamUtils.map(c, Class::newInstance, OptionsCollector::logException))
                .forEach(o -> o.ifPresent(options::addOption));
        return options;
    }

    private static void logException(Throwable e) {
        Logger.getInstance().error(OptionsCollector.class, e.getMessage());
    }
}
