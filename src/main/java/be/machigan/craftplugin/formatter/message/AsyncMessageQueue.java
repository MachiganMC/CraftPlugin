package be.machigan.craftplugin.formatter.message;

import be.machigan.craftplugin.formatter.message.sender.Sender;
import be.machigan.craftplugin.lambda.ParameterRunnable;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.LinkedList;

public class AsyncMessageQueue implements Sender {
    private final LinkedList<AsyncMessage> messagesQueue = new LinkedList<>();

    @Contract("_ -> this")
    public AsyncMessageQueue addMessage(@NotNull AsyncMessage message) {
        this.messagesQueue.add(message);
        return this;
    }

    private void applyAndSend(ParameterRunnable<AsyncMessage> typeOfSend) {
        this.messagesQueue.forEach(message -> {
            message.applyModifiers();
            typeOfSend.run(message);
        });
    }

    @Override
    public void send(@NotNull Player player) {
        this.applyAndSend(message -> message.send(player));
    }

    @Override
    public void send(@NotNull Collection<Player> players) {
        this.applyAndSend(message -> message.send(players));
    }

    @Override
    public void broadcast() {
        this.applyAndSend(AsyncMessage::broadcast);
    }
}
