package org.fabasoad.poe.statistics;

import org.fabasoad.poe.core.Logger;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/**
 * @author Yevhen Fabizhevskyi
 * @date 19.04.2016.
 */
public final class StatisticsStorage {

    private static final StatisticsStorage instance = new StatisticsStorage();
    private final Reflections reflections;

    public static StatisticsStorage getInstance() {
        return instance;
    }

    private StatisticsStorage() {
        reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage("org.fabasoad.poe"))
                .setScanners(new SubTypesScanner(), new TypeAnnotationsScanner()));
    }

    public void print() {
        System.out.println();
        System.out.println("=============== Statistics ===============");
        collectStatistics().forEach(System.out::println);
        System.out.println();
    }

    private Collection<String> collectStatistics() {
        Collection<String> result = new ArrayList<>();
        Set<Class<?>> types = reflections.getTypesAnnotatedWith(SupportedStatistics.class);
        for (Class<?> type : types) {
            SupportedStatistics annotation = type.getAnnotation(SupportedStatistics.class);

            try {
                /**
                 * The code below is equivalent to:
                 *   AnyClass.getInstance().getStatistics();
                 */
                Object obj = type.getMethod(annotation.methodGetInstance()).invoke(null);
                result.add(type.getMethod(annotation.methodGetStatistics()).invoke(obj).toString());
            } catch (Exception e) {
                Logger.getInstance().error(getClass(), e.getMessage());
            }
        }
        return result;
    }
}
