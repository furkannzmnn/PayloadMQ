package org.example;

@FunctionalInterface
public interface Converter<F, K, R> {
    R convert(F from, K to);
}
