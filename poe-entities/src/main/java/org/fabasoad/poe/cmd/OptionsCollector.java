package org.fabasoad.poe.cmd;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.fabasoad.poe.cmd.config.OptionGroup;
import org.fabasoad.poe.cmd.config.OptionGroupBy;
import org.fabasoad.poe.core.Logger;
import org.fabasoad.poe.utils.ReflectionUtils;
import org.fabasoad.poe.utils.StreamUtils;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

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
        Options result = new Options();
        ReflectionUtils.getSubTypesOf(Option.class).stream()
                .filter(c -> !Modifier.isAbstract(c.getModifiers()))
                .map(c -> StreamUtils.map(c, Class::newInstance, OptionsCollector::logException))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.groupingBy(OptionsCollector::extractGrouping))
                .forEach((ignored, options) -> {
                    org.apache.commons.cli.OptionGroup optionGroup = new org.apache.commons.cli.OptionGroup();
                    options.forEach(optionGroup::addOption);
                    result.addOptionGroup(optionGroup);
                });
        return result;
    }

    private static void logException(Throwable e) {
        Logger.getInstance().error(OptionsCollector.class, e.getMessage());
    }

    private static OptionGroup extractGrouping(Option option) {
        OptionGroup[] result = { OptionGroup.UNKNOWN };
        Arrays.stream(option.getClass().getAnnotations())
                .filter(a -> a instanceof OptionGroupBy)
                .findFirst()
                .ifPresent(a -> result[0] = ((OptionGroupBy) a).value());
        return result[0];
    }
}
