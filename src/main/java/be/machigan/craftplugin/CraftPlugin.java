package be.machigan.craftplugin;

import be.machigan.craftplugin.command.CommandHandler;
import be.machigan.craftplugin.internal.exception.PluginNotRegisteredException;
import be.machigan.craftplugin.menu.event.InventoryEventHandler;
import be.machigan.craftplugin.utils.version.ServerVersion;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CraftPlugin {
    private static JavaPlugin plugin;
    
    public static void registerPlugin(@NotNull JavaPlugin javaPlugin) {
        plugin = javaPlugin;
        InventoryEventHandler.registerEvents();
        ServerVersion.setTools();
    }

    public static void forceSpigotTools() {
        ServerVersion.setPaperServer(false);
        ServerVersion.setTools();
    }

    public static void registerCommand(@NotNull String commandStr, @NotNull CommandExecutor executor, @Nullable TabCompleter completer) {
        PluginCommand command = getPlugin().getCommand(commandStr);
        if (command == null)return;
        command.setExecutor(executor);
        if (completer != null)
            command.setTabCompleter(completer);
    }

    public static void registerCommand(@NotNull CommandHandler commandHandler) {
        commandHandler.register();
    }

    public static void registerCommand(@NotNull String commandStr, @NotNull CommandExecutor executor) {
        registerCommand(commandStr, executor, null);
    }

    public static @NotNull JavaPlugin getPlugin() throws PluginNotRegisteredException {
        if (plugin == null)
            throw new PluginNotRegisteredException();
        return plugin;
    }
}
