package org.fabasoad.poe.statistics;

import org.fabasoad.poe.core.Logger;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class printing statistics from all classes annotated with {@link SupportedStatistics}.
 * Those annotated classes should be a Singleton.
 *
 * @author Yevhen Fabizhevskyi
 * @date 19.04.2016.
 */
public final class StatisticsCollector {

    private static final StatisticsCollector instance = new StatisticsCollector();
    private final Reflections reflections;

    public static StatisticsCollector getInstance() {
        return instance;
    }

    private StatisticsCollector() {
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

    /**
     * Collecting all statistics from classes annotated with {@link SupportedStatistics}
     * using {@link SupportedStatistics#methodGetStatistics()} method.
     *
     * <example>
     *     We have the following classes:
     *     <pre>
     *         <code>
     *             &#064;SupportedStatistics(methodGetInstance = "getMe", methodGetStatistics = "getInfo")
     *             public class MyClass1 {
     *                 public String getInfo() {
     *                     return "Statistics from MyClass1";
     *                 }
     *
     *                 public MyClass1 getMe() {
     *                     return instance;
     *                 }
     *             }
     *
     *             &#064;SupportedStatistics(methodGetInstance = "getInstance", methodGetStatistics = "getResults")
     *             public class MyClass2 {
     *                 public String getResults() {
     *                     return "Statistics from MyClass2";
     *                 }
     *
     *                 public MyClass2 getInstance() {
     *                     return instance;
     *                 }
     *             }
     *         </code>
     *     </pre>
     *
     *     Result of this method would be a collection with 2 elements:
     *         [ "Statistics from MyClass1", "Statistics from MyClass2" ]
     * </example>
     *
     * @return Collection of statistics results
     */
    private Collection<String> collectStatistics() {
        Collection<String> result = new ArrayList<>();
        reflections.getTypesAnnotatedWith(SupportedStatistics.class).forEach(type -> {
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
        });
        return result;
    }
}
