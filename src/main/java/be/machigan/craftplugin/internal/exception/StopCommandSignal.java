package be.machigan.craftplugin.internal.exception;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class StopCommandSignal extends RuntimeException {
    public StopCommandSignal() {
        super("The command has been stopped");
    }
}
