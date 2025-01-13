package be.machigan.craftplugin.utils.version;

import be.machigan.craftplugin.formatter.message.Message;
import be.machigan.craftplugin.formatter.message.sender.PaperMessageSender;
import be.machigan.craftplugin.formatter.message.sender.SpigotMessageSender;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ServerVersion {
    @Getter
    @Setter
    private static boolean isPaperServer;
    public static final MinecraftVersion CURRENT_MINECRAFT_VERSION = getMinecraftVersion();

    static {
        boolean isPaperServerTemp;
        try {
            Class.forName("io.papermc.paper.text.PaperComponents");
            isPaperServerTemp = true;
        } catch (ClassNotFoundException e) {
            isPaperServerTemp = false;
        }
        isPaperServer = isPaperServerTemp;
    }

    public static void setTools() {
        if (isPaperServer) {
            Message.registerSender(new PaperMessageSender());
        } else {
            Message.registerSender(new SpigotMessageSender());
        }
    }

    private static MinecraftVersion getMinecraftVersion() {
        String bukkitVersion = Bukkit.getBukkitVersion();
        Matcher matcher = Pattern.compile("(?<version>\\d+\\.\\d+)(?<patch>\\.\\d+)?").matcher(bukkitVersion);
        if (matcher.find()) {
            return MinecraftVersion.fromString(
                    matcher.group("version") +
                    Objects.requireNonNullElse(matcher.group("patch"), "0")
            );
        }
        throw new IllegalStateException("Minecraft version not found from " + bukkitVersion);
    }
}
