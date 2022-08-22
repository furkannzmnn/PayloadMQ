package org.example;

import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Loggers {

    private static final Logger LOGGER = Logger.getAnonymousLogger();
    private Loggers() {
    }

    public static void log(Level level, String message, Object... args) {
        LOGGER.log(level, message, args);
    }

    public static void trace(Supplier<? extends CharSequence> log) {
        LOGGER.log(Level.WARNING, log.get().toString());
    }
}
