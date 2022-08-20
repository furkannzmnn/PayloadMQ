package org.example;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Loggers {
    private Loggers() {
    }
    public static final Logger LOGGER = Logger.getLogger(Loggers.class.getName());

    public static void log(Level level, String message, Object... args) {
        LOGGER.log(level, message, args);
    }
}
