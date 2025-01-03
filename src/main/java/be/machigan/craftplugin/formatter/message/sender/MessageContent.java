package be.machigan.craftplugin.formatter.message.sender;

import be.machigan.craftplugin.formatter.message.MessagePart;
import be.machigan.craftplugin.formatter.message.MessageSettings;
import be.machigan.craftplugin.utils.Tools;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;

@ApiStatus.Internal
public class MessageContent {
    @Getter
    @Setter
    private String content;
    private final EnumMap<MessagePart, String> additionalContent = new EnumMap<>(MessagePart.class);

    public MessageContent(MessageSettings settings, String path, Player player) {
        this.content = settings.getContent(path, player);
        for (MessagePart part : MessagePart.values()) {
            this.additionalContent.put(part, settings.getAdditionalContent(path, part, player));
        }
    }

    public MessageContent(String content) {
        this.content = content;
    }

    public void replace(String from, String to) {
        this.content = Tools.safeReplace(this.content, from, to);
        for (MessagePart part : MessagePart.values()) {
            this.additionalContent.put(part, Tools.safeReplace(this.additionalContent.get(part), from, to));
        }
    }


    public String getAdditionalContent(MessagePart part) {
        return this.additionalContent.get(part);
    }

    public void setAdditionalContent(@NotNull MessagePart part, @Nullable String value) {
        this.additionalContent.put(part, value);
    }
}
