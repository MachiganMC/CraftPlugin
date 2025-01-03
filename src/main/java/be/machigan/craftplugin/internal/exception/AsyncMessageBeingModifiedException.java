package be.machigan.craftplugin.internal.exception;

public class AsyncMessageBeingModifiedException extends RuntimeException {
    public AsyncMessageBeingModifiedException() {
        super("You cannot send an async message while the message is being modified by another thread.");
    }
}
