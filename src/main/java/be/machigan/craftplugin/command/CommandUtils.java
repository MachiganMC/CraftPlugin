package be.machigan.craftplugin.command;

import be.machigan.craftplugin.command.arg.ArgumentHolder;
import be.machigan.craftplugin.command.arg.ArgumentTabCompleterType;
import be.machigan.craftplugin.command.arg.NamedArgument;
import be.machigan.craftplugin.command.data.CommandData;
import lombok.experimental.UtilityClass;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@UtilityClass
@ApiStatus.Internal
public class CommandUtils {
    public static <T extends CommandSender> void executeIfNotNull(@Nullable Consumer<CommandData<T>> consumer, CommandData<T> commandData) {
        if (consumer != null)
            consumer.accept(commandData);
    }

    public static <
            T extends CommandSender,
            N extends NamedArgument<T>
    > Map<String, N> argumentsArrayToMap(N[] subArguments) {
        return Arrays.stream(subArguments).collect(Collectors.toMap(NamedArgument::getName, argument -> argument));
    }

    public static <T extends CommandSender> PermissionExecutor<T> consumerToPermissionExecutor(Consumer<CommandData<T>> consumer) {
        if (consumer instanceof PermissionExecutor)
            return  (PermissionExecutor<T>) consumer;
        return new PermissionExecutor<>(null, consumer, null);
    }

    public static <T extends CommandSender> ArgumentTabCompleterType tabCompleterTypeFromSubArguments(ArgumentHolder<T>[] subArguments) {
        return subArguments.length == 0 ? ArgumentTabCompleterType.EMPTY : ArgumentTabCompleterType.AUTO;
    }
}
