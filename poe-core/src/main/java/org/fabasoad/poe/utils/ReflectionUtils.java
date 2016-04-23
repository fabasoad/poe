package org.fabasoad.poe.utils;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.annotation.Annotation;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author Yevhen Fabizhevskyi
 * @date 23.04.2016.
 */
public class ReflectionUtils {

    private static Reflections reflections = new Reflections(new ConfigurationBuilder()
            .setUrls(ClasspathHelper.forPackage("org.fabasoad.poe"))
            .setScanners(new SubTypesScanner(), new TypeAnnotationsScanner()));

    public static <T extends Annotation> Set<Class<?>> getTypesAnnotatedWith(Class<T> annotation) {
        return reflections.getTypesAnnotatedWith(annotation);
    }

    public static <T> Set<Class<? extends T>> getSubTypesOf(Class<T> clazz) {
        return reflections.getSubTypesOf(clazz);
    }

    public static <T, R> Optional<R> invokeThrowable(
            T obj, String methodName, Class<R> returnedType, Consumer<Throwable> handleException) {
        Optional<R> result = Optional.empty();
        try {
            result = Optional.ofNullable(returnedType.cast(obj.getClass().getMethod(methodName).invoke(obj)));
        } catch (Throwable e) {
            handleException.accept(e);
        }
        return result;
    }
}
