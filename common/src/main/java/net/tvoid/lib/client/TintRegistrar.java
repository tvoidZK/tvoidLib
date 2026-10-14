package net.tvoid.lib.client;

import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.tvoid.lib.register.BlockRegistrar;

public class TintRegistrar {
    public static void registerTints(BlockRegistrar blocks) {
        for (BlockRegistrar.TintedBlock t : blocks.tintedBlocks()) {
            int argb = 0xFF000000 | t.color();
            ColorHandlerRegistry.registerBlockColors(new BlockTintSource() {
                @Override public int color(BlockState state) { return -1; }
                @Override public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
                    return argb;
                }
            }, t.block().get());
        }
    }
}