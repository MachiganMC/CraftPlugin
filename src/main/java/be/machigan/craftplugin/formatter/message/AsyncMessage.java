package be.machigan.craftplugin.formatter.message;

import be.machigan.craftplugin.CraftPlugin;
import be.machigan.craftplugin.internal.exception.AsyncMessageBeingModifiedException;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

public class AsyncMessage implements MessageRecipient {
    private Message message;
    private final LinkedList<Runnable> asynchronousMessageModifiersQueue = new LinkedList<>();
    private boolean isMessageBeingModified = false;

    @Contract("_ -> new")
    public static @NotNull AsyncMessage getMessage(@NotNull String path) {
        return getMessage(path, null, false);
    }

    @Contract("_, _ -> new")
    public static @NotNull AsyncMessage getMessage(@NotNull String path, @Nullable Player player) {
        return getMessage(path, player, false);
    }

    @Contract("_, _ -> new")
    public static @NotNull AsyncMessage getMessage(@NotNull String path, boolean isNecessary) {
        return getMessage(path, null, isNecessary);
    }

    @Contract("_, _, _ -> new")
    public static @NotNull AsyncMessage getMessage(@NotNull String path, @Nullable Player player, boolean isNecessary) {
        AsyncMessage asyncMessage = new AsyncMessage();
        asyncMessage.asynchronousMessageModifiersQueue.add(() -> asyncMessage.message = new Message(path, player, isNecessary));
        return asyncMessage;
    }

    @Contract("_ -> new")
    public static @NotNull AsyncMessage getStaticMessage(@NotNull String content) {
        AsyncMessage asyncMessage = new AsyncMessage();
        asyncMessage.asynchronousMessageModifiersQueue.add(() -> asyncMessage.message = new Message(content));
        return asyncMessage;
    }

    private AsyncMessage addToMessageModifierList(Runnable runnable) {
        this.asynchronousMessageModifiersQueue.add(runnable);
        return this;
    }

    @Override
    public @NotNull AsyncMessage replace(@NotNull String from, @NotNull String to) {
        return this.addToMessageModifierList(() -> this.message.replace(from, to));
    }

    @Override
    public @NotNull MessageRecipient replace(@NotNull Map<String, String> fromToMap) {
        return this.addToMessageModifierList(() -> this.replace(fromToMap));
    }

    @Override
    public @NotNull MessageRecipient replace(@NotNull MessagePlaceholder messagePlaceholder) {
        return this.addToMessageModifierList(() -> this.message.replace(messagePlaceholder));
    }

    @Override
    public @NotNull MessageRecipient replace(@NotNull MessagePlaceholder... messagePlaceholders) {
        return this.addToMessageModifierList(() -> this.message.replace(messagePlaceholders));
    }

    @Override
    public @NotNull MessageRecipient disableHoverContent() {
        return this.addToMessageModifierList(() -> this.message.disableHoverContent());
    }

    @Override
    public @NotNull MessageRecipient disableHotbarContent() {
        return this.addToMessageModifierList(() -> this.message.disableHotbarContent());
    }

    @Override
    public @NotNull MessageRecipient disableRunCommandContent() {
        return this.addToMessageModifierList(() -> this.message.disableRunCommandContent());
    }

    @Override
    public @NotNull MessageRecipient disableSuggestCommandContent() {
        return this.addToMessageModifierList(() -> this.message.disableSuggestCommandContent());
    }

    @Override
    public @NotNull MessageRecipient disableCopyToClipboard() {
        return this.addToMessageModifierList(() -> this.message.disableCopyToClipboard());
    }

    @Override
    public @NotNull MessageRecipient disableCustomPlaceholders() {
        return this.addToMessageModifierList(() -> this.message.disableCustomPlaceholders());
    }

    @Override
    public @NotNull MessageRecipient setHoverContent(@NotNull String hoverContent) {
        return this.addToMessageModifierList(() -> this.message.setHoverContent(hoverContent));
    }

    @Override
    public @NotNull MessageRecipient setHotbarContent(@NotNull String hotbarContent) {
        return this.addToMessageModifierList(() -> this.message.setHotbarContent(hotbarContent));
    }

    @Override
    public @NotNull MessageRecipient setRunCommandContent(@NotNull String runCommandContent) {
        return this.addToMessageModifierList(() -> this.message.setRunCommandContent(runCommandContent));
    }

    @Override
    public @NotNull MessageRecipient setSuggestedCommandContent(@NotNull String suggestedCommandContent) {
        return this.addToMessageModifierList(() -> this.message.setSuggestedCommandContent(suggestedCommandContent));
    }

    @Override
    public @NotNull MessageRecipient setCopyToClipboardContent(@NotNull String copyToClipboardContent) {
        return this.addToMessageModifierList(() -> this.message.setCopyToClipboardContent(copyToClipboardContent));
    }

    void applyModifiers() throws AsyncMessageBeingModifiedException {
        if (this.isMessageBeingModified)
            throw new AsyncMessageBeingModifiedException();
        this.isMessageBeingModified = true;
        this.asynchronousMessageModifiersQueue.forEach(Runnable::run);
        this.asynchronousMessageModifiersQueue.clear();
        this.isMessageBeingModified = false;
    }

    private void sendAndApplyModifier(Runnable sendRunnable) {
        this.addToMessageModifierList(sendRunnable);
        Bukkit.getScheduler().runTaskAsynchronously(CraftPlugin.getPlugin(), this::applyModifiers);
    }

    @Override
    public void send(@NotNull Player player) {
        this.sendAndApplyModifier(() -> this.message.send(player));
    }

    @Override
    public void send(@NotNull Collection<Player> players) {
        this.sendAndApplyModifier(() -> this.message.send(players));
    }

    @Override
    public void broadcast() {
        this.sendAndApplyModifier(() -> this.message.broadcast());
    }

    @Override
    public void mail(@NotNull OfflinePlayer player) {
        this.sendAndApplyModifier(() -> this.message.mail(player));
    }

    @Override
    public void send(@NotNull CommandSender sender) {
        this.sendAndApplyModifier(() -> this.message.send(sender));
    }

    @Override
    public void mail(@NotNull Collection<OfflinePlayer> players) {
        this.sendAndApplyModifier(() -> this.message.mail(players));
    }

    public static void reload() {
        Message.reload();
    }

    public @NotNull AsyncMessageQueue toQueue() {
        return new AsyncMessageQueue().addMessage(this);
    }
}
