package io.github.fabasoad.poe.statistics;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import io.github.fabasoad.utils.ReflectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * This class printing statistics from all classes annotated with {@link SupportedStatistics}.
 * Those annotated classes should be a Singleton.
 */
@Slf4j
public final class StatisticsCollector {

    private static final StatisticsCollector instance = new StatisticsCollector();

    public static StatisticsCollector getInstance() {
        return instance;
    }

    private StatisticsCollector() {
    }

    public void print() {
        final String newLine = System.getProperty("line.separator");
        String statistics = collectStatistics().stream()
                .filter(s -> StringUtils.isNotEmpty(s) && !s.equals(newLine))
                .collect(Collectors.joining(newLine));
        System.out.println();
        if (StringUtils.isEmpty(StringUtils.strip(statistics, newLine))) {
            System.out.println("============= Statistics is empty =============");
        } else {
            System.out.println("================= Statistics =================");
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
        ReflectionUtils.getTypesAnnotatedWith("io.github.fabasoad.poe", SupportedStatistics.class).forEach(type -> {
            SupportedStatistics annotation = type.getAnnotation(SupportedStatistics.class);

            try {
                /*
                 * The code below is equivalent to:
                 *   AnyClass.getInstance().getStatistics();
                 */
                Object obj = type.getMethod(annotation.methodGetInstance()).invoke(null);
                result.add(type.getMethod(annotation.methodGetStatistics()).invoke(obj).toString());
            } catch (Exception e) {
                log.error("Failed to collect statistics", e);
            }
        });
        return result;
    }
}
