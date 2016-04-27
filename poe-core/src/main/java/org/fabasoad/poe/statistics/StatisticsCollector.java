package org.fabasoad.poe.statistics;

import org.apache.commons.lang3.StringUtils;
import org.fabasoad.poe.core.Logger;
import org.fabasoad.poe.utils.ReflectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * This class printing statistics from all classes annotated with {@link SupportedStatistics}.
 * Those annotated classes should be a Singleton.
 *
 * @author Yevhen Fabizhevskyi
 * @date 19.04.2016.
 */
public final class StatisticsCollector {

    private static final StatisticsCollector instance = new StatisticsCollector();

    public static StatisticsCollector getInstance() {
        return instance;
    }

    private StatisticsCollector() {
    }

    public void print() {
        String newLine = System.getProperty("line.separator");
        System.out.println();
        String statistics = collectStatistics().stream()
                .filter(s -> StringUtils.isNotEmpty(s) && !s.equals(newLine))
                .collect(Collectors.joining(newLine));
        if (StringUtils.isEmpty(StringUtils.strip(statistics, newLine))) {
            System.out.println("=============== Statistics is empty ===============");
        } else {
            System.out.println("=============== Statistics ===============");
            System.out.println(statistics);
        }
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
        ReflectionUtils.getTypesAnnotatedWith(SupportedStatistics.class).forEach(type -> {
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
