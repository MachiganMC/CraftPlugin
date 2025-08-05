package be.machigan.craftplugin.formatter.color;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class CustomColor {
    private static final Map<String, String> CUSTOM_COLORS = new HashMap<>();
    // the use of arrays is done to prevent the convertion of the map to arrays each time a replacement color is called
    // that happens very often unlike the modifications of the map.
    private static String[] tempCodes = new String[] {};
    private static String[] tempCustomColors = new String[] {};

    public static void add(@NotNull String code, @NotNull String customColor) {
        CUSTOM_COLORS.put(formatColorCode(code), customColor);
        updateArrays();
    }

    public static void add(@NotNull Map<String, String> customColors) {
        customColors.forEach((code, color) -> CUSTOM_COLORS.put(formatColorCode(code), color));
        updateArrays();
    }

    public static void remove(@NotNull String code) {
        CUSTOM_COLORS.remove(formatColorCode(code));
        updateArrays();
    }

    public static void clearCustomColors() {
        CUSTOM_COLORS.clear();
        updateArrays();
    }

    private static void updateArrays() {
        tempCodes = CUSTOM_COLORS.keySet().toArray(new String[0]);
        tempCustomColors = CUSTOM_COLORS.values().toArray(new String[0]);
    }

    private static String formatColorCode(@NotNull String code) {
        if (!code.startsWith("&"))
            code = "&" + code;
        return code;
    }

    @ApiStatus.Internal
    public static String replace(@NotNull String text) {
        return StringUtils.replaceEach(text, tempCodes, tempCustomColors);
    }
}
