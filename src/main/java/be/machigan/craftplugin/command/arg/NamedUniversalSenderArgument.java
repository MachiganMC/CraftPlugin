package be.machigan.craftplugin.command.arg;

import be.machigan.craftplugin.command.PermissionExecutor;
import be.machigan.craftplugin.command.arg.sub.SubArgumentHolder;
import be.machigan.craftplugin.command.data.CommandData;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

@Getter
@ApiStatus.Internal
public class NamedUniversalSenderArgument extends NamedArgument<CommandSender> {
    public NamedUniversalSenderArgument(
            @NotNull String name,
            @Nullable PermissionExecutor<CommandSender> executor,
            @NotNull SubArgumentHolder<CommandSender> subArguments,
            @Nullable Consumer<CommandData<CommandSender>> onNoArg,
            @NotNull ArgumentTabCompleterType tabCompleterType,
            @Nullable Function<Player, List<Object>> customTabCompleterGetter
    ) {
        super(name, executor, subArguments, onNoArg, tabCompleterType, customTabCompleterGetter);
    }

    @Override
    public CommandSender convertToSender(CommandSender sender) {
        return sender;
    }
}
