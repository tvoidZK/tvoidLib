package net.tvoid.lib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.tvoid.fixes.tagFixes;
import net.tvoid.testmod.TestMod;

public final class tvoidLib {
    public static final String libId = "tvoidlib";
    private static final Logger LOGGER = LoggerFactory.getLogger(tvoidLib.libId);

//todo
//  public static String getModId() {
//        return modId;
//    }
//todo
//    public static void getModId(String modId) {
//        modId = modId;
//    }

    public static void init() {
        TestMod.init();
        LOGGER.info("Fixing tags. just kidding it's not implemented yet");
        tagFixes.init();
    }

//todo
//  public void registerItems() {
//      ModItems.init();
//  }
}