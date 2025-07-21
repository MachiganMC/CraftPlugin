package be.machigan.craftplugin.command.arg;

import be.machigan.craftplugin.command.PermissionExecutor;
import be.machigan.craftplugin.command.data.CommandData;
import com.google.common.base.Preconditions;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Getter
public abstract class ArgumentHolder<T extends CommandSender> {
    protected final String name;
    protected PermissionExecutor<T> executor;
    protected Consumer<CommandData<T>> onArgumentNotExist;
    protected Consumer<CommandData<T>> onNoArg;
    protected final Map<String, ? extends ArgumentHolder<T>> subArguments;
    protected ArgumentTabCompleterType tabCompleterType;
    protected Supplier<List<String>> customTabCompleterGetter;

    protected ArgumentHolder(
            @NotNull String name,
            @Nullable PermissionExecutor<T> executor,
            @Nullable Consumer<CommandData<T>> onArgumentNotExist,
            @Nullable Consumer<CommandData<T>> onNoArg,
            @NotNull Map<String, ArgumentHolder<T>> subArguments,
            @NotNull ArgumentTabCompleterType tabCompleterType,
            @Nullable Supplier<List<String>> customTabCompleterGetter
    ) {
        name = name.toLowerCase().strip();
        Preconditions.checkArgument(!name.isEmpty(), "Name of an argument/command cannot be empty");
        this.name = name;
        this.executor = executor;
        this.onArgumentNotExist = onArgumentNotExist;
        this.onNoArg = onNoArg;
        this.subArguments = subArguments;
        this.tabCompleterType = tabCompleterType;
        this.customTabCompleterGetter = customTabCompleterGetter;
    }

    public void execute(CommandData<T> commandData, LinkedList<String> leftArguments) {
        executeIfNotNull(this.executor, commandData);
        if (leftArguments.isEmpty()) {
            executeIfNotNull(this.onNoArg, commandData);
            return;
        }
        String nextArgumentString = leftArguments.removeFirst();
        ArgumentHolder<T> nextArgument = this.subArguments.get(nextArgumentString);
        if (nextArgument == null) {
            executeIfNotNull(this.onArgumentNotExist, commandData);
            return;
        }
        nextArgument.execute(new CommandData<>(
                commandData.getSender(),
                commandData.getCurrentArgument(),
                this,
                leftArguments,
                commandData.getArguments()
        ), leftArguments);
    }

    private static <T extends CommandSender> void executeIfNotNull(@Nullable Consumer<CommandData<T>> consumer, CommandData<T> commandData) {
        if (consumer != null)
            consumer.accept(commandData);
    }

    protected static <T extends CommandSender> PermissionExecutor<T> consumerToPermissionExecutor(Consumer<CommandData<T>> consumer) {
        if (consumer instanceof PermissionExecutor)
            return  (PermissionExecutor<T>) consumer;
        return new PermissionExecutor<>(null, consumer, null);
    }

    protected static <T extends CommandSender> Map<String, ArgumentHolder<T>> argumentsArrayToMap(ArgumentHolder<T>[] subArguments) {
        return Arrays.stream(subArguments).collect(Collectors.toMap(ArgumentHolder::getName, argument -> argument));
    }

    protected static <T extends CommandSender> ArgumentTabCompleterType tabCompleterTypeFromSubArguments(ArgumentHolder<T>[] subArguments) {
        return subArguments.length == 0 ? ArgumentTabCompleterType.EMPTY : ArgumentTabCompleterType.ARGUMENTS_LIST;
    }

    public abstract T convertToSender(CommandSender sender);
}
