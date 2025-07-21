package be.machigan.craftplugin.command.arg;

import be.machigan.craftplugin.command.PermissionExecutor;
import be.machigan.craftplugin.command.data.CommandData;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Getter
public class CommandArgument extends ArgumentHolder<CommandSender> {
    protected CommandArgument(
            @NotNull String name,
            @Nullable PermissionExecutor<CommandSender> executor,
            @Nullable Consumer<CommandData<CommandSender>> onArgumentNotExist,
            @Nullable Consumer<CommandData<CommandSender>> onNoArg,
            @NotNull CommandArgument... subArguments
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

    public static CommandArgument withAllPreventions(
            @NotNull String name,
            @Nullable Consumer<CommandData<CommandSender>> onExecute,
            @Nullable String requiredPermission,
            @Nullable Consumer<CommandData<CommandSender>> onNoPerm,
            @Nullable Consumer<CommandData<CommandSender>> onArgumentNotExist,
            @Nullable Consumer<CommandData<CommandSender>> onNoArg,
            @NotNull CommandArgument... arguments
    ) {
        return new CommandArgument(
                name,
                new PermissionExecutor<>(requiredPermission, onExecute, onNoPerm),
                onArgumentNotExist,
                onNoArg,
                arguments
        );
    }

    public static CommandArgument withNextArgumentPreventions(
            @NotNull String name,
            @Nullable Consumer<CommandData<CommandSender>> onExecute,
            @Nullable Consumer<CommandData<CommandSender>> onArgumentNotExist,
            @Nullable Consumer<CommandData<CommandSender>> onNoArg,
            @NotNull CommandArgument... arguments
    ) {
        return new CommandArgument(
                name,
                consumerToPermissionExecutor(onExecute),
                onArgumentNotExist,
                onNoArg,
                arguments
        );
    }

    public static CommandArgument withOnlyExecutor(
            @NotNull String name,
             @Nullable Consumer<CommandData<CommandSender>> onExecute,
             @NotNull CommandArgument... arguments
    ) {
        return new CommandArgument(name, consumerToPermissionExecutor(onExecute), null, null,  arguments);
    }

    public static CommandArgument withOnlyArguments(@NotNull String name, @NotNull CommandArgument... arguments) {
        return new CommandArgument(name, new PermissionExecutor<>(null, null, null), null, null, arguments);
    }

    @Override
    public CommandSender convertToSender(CommandSender sender) {
        return sender;
    }

    private CommandArgument doAndReturnThis(Runnable runnable) {
        runnable.run();
        return this;
    }

    public CommandArgument setExecutor(PermissionExecutor<CommandSender> executor) {
        return this.doAndReturnThis(() -> this.executor = executor);
    }

    public CommandArgument setOnArgumentNotExist(Consumer<CommandData<CommandSender>> onArgumentNotExist) {
        return this.doAndReturnThis(() -> this.onArgumentNotExist = onArgumentNotExist);
    }

    public CommandArgument setOnNoArg(Consumer<CommandData<CommandSender>> onNoArg) {
        return this.doAndReturnThis(() -> this.onNoArg = onNoArg);
    }

    public CommandArgument setTabCompleterType(ArgumentTabCompleterType tabCompleterType) {
        return this.doAndReturnThis(() -> this.tabCompleterType = tabCompleterType);
    }

    public CommandArgument setCustomTabCompleterGetter(Supplier<List<String>> customTabCompleterGetter) {
        return this.doAndReturnThis(() -> this.customTabCompleterGetter = customTabCompleterGetter);
    }
}
