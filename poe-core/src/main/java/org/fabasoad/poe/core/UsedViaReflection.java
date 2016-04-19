package org.fabasoad.poe.core;

import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.METHOD;

/**
 * @author Yevhen Fabizhevskyi
 * @date 19.04.2016.
 */
@Target({TYPE, METHOD})
public @interface UsedViaReflection {
}
