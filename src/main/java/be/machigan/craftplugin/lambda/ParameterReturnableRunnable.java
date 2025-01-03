package be.machigan.craftplugin.lambda;

public interface ParameterReturnableRunnable<T> {
    T run(T parameter);
}
