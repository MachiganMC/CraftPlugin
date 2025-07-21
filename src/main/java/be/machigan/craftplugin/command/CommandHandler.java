package be.machigan.craftplugin.command;

import be.machigan.craftplugin.CraftPlugin;
import be.machigan.craftplugin.command.arg.ArgumentHolder;
import be.machigan.craftplugin.internal.exception.StopCommandSignal;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.ApiStatus;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

public abstract class CommandHandler<T extends CommandSender> {

    @ApiStatus.Internal
    public void register() {
        Command command = this.buildCommand();
        try {
            Field commandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMap.setAccessible(true);
            CommandMap cmdMap = (CommandMap) commandMap.get(Bukkit.getServer());
            cmdMap.register(CraftPlugin.getPlugin().getName(), command);

        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            CraftPlugin.getPlugin().getLogger().severe("Unable to register command " + command.getName());
            CraftPlugin.getPlugin().getLogger().severe(e.getMessage());
        }
    }

    private Command buildCommand() {
        ArgumentHolder<T> pluginCommand = this.getCommand();
        Command command = new BukkitCommand<>(pluginCommand.getName(), pluginCommand);
        command.setAliases(this.getAliases());
        command.setDescription(this.getDescription());
        command.setUsage(this.getUsage());
        return command;
    }

    public static void stopCommand() {
        throw new StopCommandSignal();
    }

    public abstract ArgumentHolder<T> getCommand();

    public List<String> getAliases() {
        return Collections.emptyList();
    }

    public String getDescription() {
        return "";
    }

    public String getUsage() {
        return "";
    }
}
