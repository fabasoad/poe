package org.fabasoad.poe.function;

/**
 * @author Yevhen Fabizhevskyi
 * @date 23.04.2016.
 */
@FunctionalInterface
public interface FunctionThrowable<T, R, E extends Throwable> {

    R apply(T obj) throws E;
}
