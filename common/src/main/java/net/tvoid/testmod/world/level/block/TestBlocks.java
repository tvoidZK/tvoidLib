package net.tvoid.testmod.world.level.block;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.level.block.Block;
import net.tvoid.lib.register.BlockRegistrar;
import net.tvoid.testmod.TestMod;
import net.tvoid.testmod.world.item.TestItems;

public class TestBlocks {
    private static final BlockRegistrar BLOCK = new BlockRegistrar(TestMod.modId, TestItems.ITEM);

    public static final RegistrySupplier<Block> TEST_BLOCK = BLOCK.reg("test_block_0");

    public static void reg() {
        BLOCK.reg();
    }
}
