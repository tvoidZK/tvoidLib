package net.tvoid.lib.compat;

import dev.architectury.platform.Platform;

public class ModCompat {
    private ModCompat() {}

    public static boolean isLoaded(String modId) {
        return modId.equals("minecraft") || Platform.isModLoaded(modId);
    }
}