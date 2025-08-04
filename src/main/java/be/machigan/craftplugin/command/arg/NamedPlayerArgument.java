package be.machigan.craftplugin.command.arg;

import be.machigan.craftplugin.command.PermissionExecutor;
import be.machigan.craftplugin.command.arg.sub.SubArgumentHolder;
import be.machigan.craftplugin.command.data.CommandData;
import be.machigan.craftplugin.formatter.message.Message;
import be.machigan.craftplugin.internal.exception.StopCommandSignal;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

@Setter
@Accessors(chain = true)
@ApiStatus.Internal
public class NamedPlayerArgument extends NamedArgument<Player> {
    @ApiStatus.Internal
    public NamedPlayerArgument(
            @NotNull String name,
            @Nullable PermissionExecutor<Player> executor,
            @NotNull SubArgumentHolder<Player> subArguments,
            @Nullable Consumer<CommandData<Player>> onNoArg,
            @NotNull ArgumentTabCompleterType tabCompleterType,
            @Nullable Function<Player, List<Object>> customTabCompleterGetter
    ) {
        super(name, executor, subArguments, onNoArg, tabCompleterType, customTabCompleterGetter);
    }

    @Override
    public Player convertToSender(CommandSender sender) {
        if (sender instanceof Player player)
            return player;
        Message.getStaticMessage("This command can only be executed by a player").send(sender);
        throw new StopCommandSignal();
    }
}
