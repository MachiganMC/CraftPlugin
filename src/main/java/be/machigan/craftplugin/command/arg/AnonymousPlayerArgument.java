package be.machigan.craftplugin.command.arg;

import be.machigan.craftplugin.command.PermissionExecutor;
import be.machigan.craftplugin.command.arg.sub.SubArgumentHolder;
import be.machigan.craftplugin.command.data.CommandData;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

@ApiStatus.Internal
public class AnonymousPlayerArgument extends ArgumentHolder<Player> {
    public AnonymousPlayerArgument(
            @Nullable PermissionExecutor<Player> executor,
            @NotNull SubArgumentHolder<Player> subArguments,
            @Nullable Consumer<CommandData<Player>> onNoArg,
            @NotNull ArgumentTabCompleterType tabCompleterType,
            @Nullable Function<Player, List<Object>> customTabCompleterGetter
    ) {
        super(executor, subArguments, onNoArg, tabCompleterType, customTabCompleterGetter);
    }

    @Override
    public Player convertToSender(CommandSender sender) {
        return (Player) sender;
    }
}
