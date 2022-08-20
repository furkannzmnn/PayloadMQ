package org.example;

import org.example.annotation.PayloadListener;

import java.io.*;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public final class ListenerInvoker {

    private ListenerInvoker() {
    }

    public static void invoke(String obj) {

        // load all class
        final InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream("org.example".replaceAll("[.]", "/"));
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream));
        final Set<Class> classSet = bufferedReader.lines()
                .filter(line -> line.endsWith(".class"))
                .map(ListenerInvoker::getClass)
                .collect(Collectors.toSet());

        final Set<Method> methodSet = classSet.stream()
                .flatMap(clazz -> Arrays.stream(clazz.getDeclaredMethods()))
                .collect(Collectors.toSet());

        methodSet.stream()
                .filter(method -> method.isAnnotationPresent(PayloadListener.class))
                .forEach(method -> {
                    try {
                        method.invoke(method.getDeclaringClass().newInstance(),obj);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }
    private static Class getClass(String className) {
        try {
            return Class.forName("org.example" + "."
                    + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            // handle the exception
        }
        return null;
    }
}