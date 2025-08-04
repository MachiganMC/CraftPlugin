package be.machigan.craftplugin.command.arg.sub;

import be.machigan.craftplugin.command.arg.ArgumentHolder;
import be.machigan.craftplugin.command.data.CommandData;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@ApiStatus.Internal
public interface SubArgumentHolder<T extends CommandSender> {
    void execute(CommandData<T> commandData);
    @NotNull List<String> getArgumentTabCompleter();
    @Nullable ArgumentHolder<T> next(String nextArgument);
}
