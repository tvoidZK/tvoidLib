package net.tvoid.lib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.tvoid.fixes.tagFixes;

public final class tvoidLib {
    public static final String libId = "tvoidlib";
    private static final Logger LOGGER = LoggerFactory.getLogger(tvoidLib.libId);

    public static void init() {
        LOGGER.info("Fixing tags. just kidding it's not implemented yet");
        tagFixes.init();
    }
}