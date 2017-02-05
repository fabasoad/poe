package org.fabasoad.poe.core;

import org.fabasoad.log.Logger;
import org.fabasoad.log.LoggerImpl;
import org.fabasoad.log.LoggerType;

/**
 * @author Yevhen Fabizhevskyi
 * @date 04.02.2017.
 */
public class LoggerInstance {

    public static Logger get() {
        return LoggerImpl.getInstance(LoggerType.CONSOLE);
    }
}
