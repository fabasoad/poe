package org.fabasoad.poe.cmd.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Eugene Fabizhevsky
 * @date 4/27/2016
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface OptionGroupBy {

    OptionGroup value() default OptionGroup.UNKNOWN;
}
