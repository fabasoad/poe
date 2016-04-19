package org.fabasoad.poe.statistics;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Yevhen Fabizhevskyi
 * @date 19.04.2016.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SupportedStatistics {

    String methodGetInstance() default "getInstance";

    String methodGetStatistics() default "getStatistics";
}
