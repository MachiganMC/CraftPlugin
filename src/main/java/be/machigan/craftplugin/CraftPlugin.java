package be.machigan.craftplugin;
import be.machigan.craftplugin.internal.ServerVersionChooser;
import be.machigan.craftplugin.internal.exception.PluginNotRegisteredException;
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
        ServerVersionChooser.setTools();
    }

    public static void forceSpigotTools() {
        ServerVersionChooser.setPaperServer(false);
        ServerVersionChooser.setTools();
    }

    public static void registerCommand(@NotNull String commandStr, @NotNull CommandExecutor executor, @Nullable TabCompleter completer) {
        PluginCommand command = getPlugin().getCommand(commandStr);
        if (command == null)return;
        command.setExecutor(executor);
        if (completer != null)
            command.setTabCompleter(completer);
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
