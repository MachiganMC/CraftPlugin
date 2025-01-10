package be.machigan.craftplugin.formatter.color;

import org.jetbrains.annotations.NotNull;

public class StringColor extends ColorBuilder<String> {
    @Override
    public @NotNull String toColoredComponent(String toColor) {
        return this.replaceColor(toColor);
    }
}
