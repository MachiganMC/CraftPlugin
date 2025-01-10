package be.machigan.craftplugin.utils;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;

import java.security.SecureRandom;
import java.util.Collection;

public class Random {
    private static final SecureRandom RANDOM = new SecureRandom();

    public static int getRandom(int min, int max) {
        return RANDOM.nextInt(max - min + 1) + min;
    }

    public static double getRandom(double min, double max, int digit) {
        Preconditions.checkArgument(digit > 0, "Digit must be greater than 0 (%s)", digit);
        double multiplier = Math.pow(10, digit);
        int minInt = (int) (min * multiplier);
        int maxInt = (int) (max * multiplier);
        return getRandom(minInt, maxInt) / multiplier;
    }

    public static double getRandom(double min, double max) {
        return getRandom(min, max, 2);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getRandom(@NotNull Collection<T> collection) {
        Preconditions.checkArgument(!collection.isEmpty(), "Collection cannot be empty");
        return (T) collection.toArray()[RANDOM.nextInt(collection.size())];
    }
}
