package be.machigan.craftplugin.config;

public interface ConfigChecker<T> {
    ConfigChecker<Integer> IS_PERCENTAGE_INT = value -> value > 0 && value < 100;
    ConfigChecker<Double> IS_PERCENTAGE_DOUBLE = value -> value > 0 && value < 100;
    ConfigChecker<Integer> BIGGER_THAN_ZERO_INT = value -> value > 0;
    ConfigChecker<Double> BIGGER_THAN_ZERO_DOUBLE = value -> value > 0;

    boolean check(T value);
}
