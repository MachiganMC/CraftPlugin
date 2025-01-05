package be.machigan.craftplugin.internal;

import be.machigan.craftplugin.formatter.message.Message;
import be.machigan.craftplugin.formatter.message.sender.PaperMessageSender;
import be.machigan.craftplugin.formatter.message.sender.SpigotMessageSender;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ServerVersionChooser {
    @Getter
    @Setter
    private static boolean isPaperServer;

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
}
