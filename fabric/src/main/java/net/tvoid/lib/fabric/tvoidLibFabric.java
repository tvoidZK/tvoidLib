package net.tvoid.lib.fabric;

import net.fabricmc.api.ModInitializer;
import net.tvoid.lib.tvoidLib;

public final class tvoidLibFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        tvoidLib.init();
    }
}