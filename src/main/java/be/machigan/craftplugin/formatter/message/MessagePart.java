package be.machigan.craftplugin.formatter.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessagePart {
    HOVER("Hover"),
    RUN_COMMAND("Run"),
    SUGGESTED_COMMAND("Suggested"),
    COPY_TO_CLIPBOARD("Copy"),
    HOTBAR("Hotbar");

    private final String defaultPath;
}
