package be.machigan.craftplugin.internal.exception;

public class PluginNotRegisteredException extends RuntimeException {
    public PluginNotRegisteredException() {
        super("You're plugin isn't registered. Use CraftPlugin#registerPlugin() to register it !");
    }
}
