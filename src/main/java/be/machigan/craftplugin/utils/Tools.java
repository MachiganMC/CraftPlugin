package be.machigan.craftplugin.utils;

import org.jetbrains.annotations.Nullable;

public class Tools {
    public static void doIfNotNull(@Nullable Object o, Runnable r) {
        if (o != null)
            r.run();
    }

    public static boolean isNull(Object... objects) {
        for (Object o : objects)
            if (o == null) return true;
        return false;
    }

    @Nullable
    public static String safeReplace(String s, String from, String to) {
        if (isNull(s, from, to)) return s;
        return s.replace(from, to);
    }
}
