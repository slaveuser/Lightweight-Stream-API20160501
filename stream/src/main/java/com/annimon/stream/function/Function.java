package com.annimon.stream.function;

/**
 * Represents a function which produces result from input arguments.
 *
 * @param <T> the type of the input of the function
 * @param <R> the type of the result of the function
 */
@FunctionalInterface
public interface Function<T, R> {

    /**
     * Applies this function to the given argument.
     *
     * @param value  an argument
     * @return the function result
     */
    R apply(T value);

    class Util {

        private Util() { }

        /**
         * Composes {@code Function} calls.
         *
         * <p>{@code f1.apply(f2.apply(v)) }
         *
         * @param <V> the type of the input argument of first function
         * @param <T> the type of the result of {@code f2} and input argument of {@code f1}
         * @param <R> the type of the result of composed function {@code f1}
         * @param f1  the function for transform {@code Function f2} result to the type {@code R}
         * @param f2  the {@code Function} which is called first
         * @return the result of composed function
         * @throws NullPointerException if {@code f1} or {@code f2} or result of {@code Function f1} is null
         * @see #andThen(com.annimon.stream.function.Function, com.annimon.stream.function.Function)
         */
        public static <V, T, R> Function<V, R> compose(
                final Function<? super T, ? extends R> f1,
                final Function<? super V, ? extends T> f2) {
            return Util.<V, T, R>andThen(f2, f1);
        }

        /**
         * Composes {@code Function} calls.
         *
         * <p>{@code f2.apply(f1.apply(t)) }
         *
         * @param <T> the type of the input argument of first function
         * @param <R> the type of the result of {@code f1} and input argument of {@code f2}
         * @param <V> the type of the result of composed function {@code f2}
         * @param f1  the {@code Function} which is called first
         * @param f2  the function for transform {@code Function f1} result to the type {@code V}
         * @return the result of composed function
         * @throws NullPointerException if {@code f1} or {@code f2} or result of {@code Function f1} is null
         * @see #compose(com.annimon.stream.function.Function, com.annimon.stream.function.Function)
         */
        public static <T, R, V> Function<T, V> andThen(
                final Function<? super T, ? extends R> f1,
                final Function<? super R, ? extends V> f2) {
            return new Function<T, V>() {

                @Override
                public V apply(T t) {
                    return f2.apply(f1.apply(t));
                }
            };
        }
    }
}
