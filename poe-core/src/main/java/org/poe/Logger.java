package org.poe;

import java.io.PrintStream;

/**
 * @author Yevhen Fabizhevskyi
 * @date 02.04.2016.
 */
public class Logger {

    private static Logger instance = new Logger();

    public static Logger getInstance() {
        return instance;
    }

    private Logger() {
    }

    public void error(Class clazz, String message) {
        log(System.err, clazz, "ERROR", message);
    }

    public void warning(Class clazz, String message) {
        log(System.out, clazz, "WARNING", message);
    }

    public void info(Class clazz, String message) {
        log(System.out, clazz, "INFO", message);
    }

    private static void log(PrintStream printStream, Class clazz, String logType, String message) {
        printStream.println(String.format("[%s] [%s] %s", logType, clazz.getSimpleName(), message));
    }
}