package be.machigan.craftplugin.internal.exception;

public class DriverNotFoundException extends RuntimeException {
    public DriverNotFoundException(String driverType) {
        super("The " + driverType + " driver has not been found");
    }
}
