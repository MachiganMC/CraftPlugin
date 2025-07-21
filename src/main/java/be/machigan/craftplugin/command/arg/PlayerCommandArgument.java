package be.machigan.craftplugin.command.arg;

import be.machigan.craftplugin.command.PermissionExecutor;
import be.machigan.craftplugin.command.data.CommandData;
import be.machigan.craftplugin.formatter.message.Message;
import be.machigan.craftplugin.internal.exception.StopCommandSignal;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Setter
@Accessors(chain = true)
public class PlayerCommandArgument extends ArgumentHolder<Player> {
    protected PlayerCommandArgument(
            @NotNull String name,
            @Nullable PermissionExecutor<Player> executor,
            @Nullable Consumer<CommandData<Player>> onArgumentNotExist,
            @Nullable Consumer<CommandData<Player>> onNoArg,
            @NotNull PlayerCommandArgument... subArguments
    ) {
        super(
                name,
                executor,
                onArgumentNotExist,
                onNoArg,
                argumentsArrayToMap(subArguments),
                tabCompleterTypeFromSubArguments(subArguments),
                null
        );
    }

    public static PlayerCommandArgument withAllPreventions(
            @NotNull String name,
            @Nullable Consumer<CommandData<Player>> onExecute,
            @Nullable String requiredPermission,
            @Nullable Consumer<CommandData<Player>> onNoPerm,
            @Nullable Consumer<CommandData<Player>> onArgumentNotExist,
            @Nullable Consumer<CommandData<Player>> onNoArg,
            @NotNull PlayerCommandArgument... arguments
    ) {
        return new PlayerCommandArgument(
                name,
                new PermissionExecutor<>(requiredPermission, onExecute, onNoPerm),
                onArgumentNotExist,
                onNoArg,
                arguments
        );
    }

    public static PlayerCommandArgument withNextArgumentPreventions(
            @NotNull String name,
            @Nullable Consumer<CommandData<Player>> onExecute,
            @Nullable Consumer<CommandData<Player>> onArgumentNotExist,
            @Nullable Consumer<CommandData<Player>> onNoArg,
            @NotNull PlayerCommandArgument... arguments
    ) {
        return new PlayerCommandArgument(
                name,
                consumerToPermissionExecutor(onExecute),
                onArgumentNotExist,
                onNoArg,
                arguments
        );
    }

    public static PlayerCommandArgument withOnlyExecutor(
            @NotNull String name,
            @Nullable Consumer<CommandData<Player>> onExecute,
            @NotNull PlayerCommandArgument... arguments
    ) {
        return new PlayerCommandArgument(name, consumerToPermissionExecutor(onExecute), null, null,  arguments);
    }

    public static PlayerCommandArgument withOnlyArguments(@NotNull String name, @NotNull PlayerCommandArgument... arguments) {
        return new PlayerCommandArgument(name, new PermissionExecutor<>(null, null, null), null, null, arguments);
    }

    @Override
    public Player convertToSender(CommandSender sender) {
        if (sender instanceof Player player)
            return player;
        Message.getStaticMessage("This command can only be executed by a player").send(sender);
        throw new StopCommandSignal();
    }

    private PlayerCommandArgument doAndReturnThis(Runnable runnable) {
        runnable.run();
        return this;
    }

    public PlayerCommandArgument setExecutor(PermissionExecutor<Player> executor) {
        return this.doAndReturnThis(() -> this.executor = executor);
    }

    public PlayerCommandArgument setOnArgumentNotExist(Consumer<CommandData<Player>> onArgumentNotExist) {
        return this.doAndReturnThis(() -> this.onArgumentNotExist = onArgumentNotExist);
    }

    public PlayerCommandArgument setOnNoArg(Consumer<CommandData<Player>> onNoArg) {
        return this.doAndReturnThis(() -> this.onNoArg = onNoArg);
    }

    public PlayerCommandArgument setTabCompleterType(ArgumentTabCompleterType tabCompleterType) {
        return this.doAndReturnThis(() -> this.tabCompleterType = tabCompleterType);
    }

    public PlayerCommandArgument setCustomTabCompleterGetter(Supplier<List<String>> customTabCompleterGetter) {
        return this.doAndReturnThis(() -> this.customTabCompleterGetter = customTabCompleterGetter);
    }
}
