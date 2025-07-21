package be.machigan.craftplugin.command;

import be.machigan.craftplugin.command.data.CommandData;
import be.machigan.craftplugin.formatter.message.Message;
import org.bukkit.command.CommandSender;

import java.util.function.Consumer;

public interface MessageConsumer<T extends CommandSender> extends Consumer<CommandData<T>> {
    Message getMessage(CommandData<T> commandData);

    @Override
    default void accept(CommandData<T> commandData) {
        this.getMessage(commandData).send(commandData.getSender());
    }
}
