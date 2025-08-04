package be.machigan.craftplugin.command.arg.builder;

import be.machigan.craftplugin.command.CommandUtils;
import be.machigan.craftplugin.command.arg.ArgumentHolder;
import be.machigan.craftplugin.command.arg.NamedArgument;
import be.machigan.craftplugin.command.arg.sub.AnonymousSubArgument;
import be.machigan.craftplugin.command.arg.sub.NamedSubArgumentHolder;
import be.machigan.craftplugin.command.data.CommandData;
import lombok.AllArgsConstructor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Function;

@AllArgsConstructor
public class ArgumentBuilder<S extends CommandSender, A extends ArgumentHolder<S>> {
    private final Function<ArgumentBuilderDto<S>, A> getter;

    @SafeVarargs
    public final A withNamedArgument(
            @Nullable Consumer<CommandData<S>> executor,
            @Nullable Consumer<CommandData<S>> onNoArg,
            @Nullable Consumer<CommandData<S>> onArgNotExists,
            @NotNull NamedArgument<S>... arguments
    ) {
        return this.getter.apply(new ArgumentBuilderDto<>(
                CommandUtils.consumerToPermissionExecutor(executor),
                new NamedSubArgumentHolder<>(
                        CommandUtils.argumentsArrayToMap(arguments),
                        onArgNotExists
                ),
                onNoArg
        ));
    }

    @SafeVarargs
    public final A withNamedArgument(
            @Nullable Consumer<CommandData<S>> executor,
            @NotNull NamedArgument<S>... arguments
    ) {
        return this.withNamedArgument(executor, null, null, arguments);
    }

    @SafeVarargs
    public final A withNamedArgument(
            @NotNull NamedArgument<S>... arguments
    ) {
        return this.withNamedArgument(null, null, null, arguments);
    }

    public <B extends ArgumentHolder<S>> A withAnonymousArgument(
            @Nullable Consumer<CommandData<S>> executor,
            @Nullable Consumer<CommandData<S>> onNoArg,
            @Nullable B nextArgument
    ) {
        return this.getter.apply(new ArgumentBuilderDto<>(
                CommandUtils.consumerToPermissionExecutor(executor),
                new AnonymousSubArgument<>(nextArgument),
                onNoArg
        ));
    }

    public <B extends ArgumentHolder<S>> A withAnonymousArgument(
            @Nullable Consumer<CommandData<S>> executor,
            @Nullable B nextArgument
    ) {
        return this.withAnonymousArgument(executor, null, nextArgument);
    }

    public <B extends ArgumentHolder<S>> A withAnonymousArgument(
            @Nullable B nextArgument
    ) {
        return this.withAnonymousArgument(null, null, nextArgument);
    }

    public A withOnlyExecutor(@Nullable Consumer<CommandData<S>> executor) {
        return this.withNamedArgument(executor);
    }
}
