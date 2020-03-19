package io.github.fabasoad.poe.cmd;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import io.github.fabasoad.utils.ReflectionUtils;
import io.github.fabasoad.utils.StreamUtils;

import java.lang.reflect.Modifier;

@Slf4j
public class OptionsCollector {

    private static OptionsCollector instance = new OptionsCollector();

    public static OptionsCollector getInstance() {
        return instance;
    }

    private OptionsCollector() {
    }

    public Options collect() {
        Options options = new Options();
        ReflectionUtils.getSubTypesOf("io.github.fabasoad.poe", Option.class).stream()
                .filter(c -> !Modifier.isAbstract(c.getModifiers()))
                .map(c -> StreamUtils.map(c, Class::newInstance, OptionsCollector::logException))
                .forEach(o -> o.ifPresent(options::addOption));
        return options;
    }

    private static void logException(Throwable e) {
        log.error("Failed to collect options.", e);
    }
}
