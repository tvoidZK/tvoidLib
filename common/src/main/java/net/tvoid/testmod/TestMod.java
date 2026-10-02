package net.tvoid.testmod;

import net.tvoid.lib.tvoidLib;
import net.tvoid.testmod.world.item.TestItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class TestMod {
        public static String modId = "tvoidlib";
        public static final Logger LOGGER = LoggerFactory.getLogger(tvoidLib.libId);

        public static void init() {
                LOGGER.info("tvoid lib test mod loaded. Was that supposed to happen?");
                TestItems.init();
        }
}