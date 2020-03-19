package io.github.fabasoad.poe.statistics;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SupportedStatistics {

    String methodGetInstance() default "getInstance";

    String methodGetStatistics() default "getStatistics";
}
