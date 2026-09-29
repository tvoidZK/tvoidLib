package net.tvoid.lib.neoforge;

import net.neoforged.fml.common.Mod;
import net.tvoid.lib.tvoidLib;

@Mod(tvoidLib.libId)
public final class tvoidLibNeoForge {
    public tvoidLibNeoForge() {
        tvoidLib.init();
    }
}