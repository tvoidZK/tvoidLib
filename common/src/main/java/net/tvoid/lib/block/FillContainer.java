package net.tvoid.lib.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.tvoid.lib.item.interaction.InteractionRegistrar;

import java.util.function.BiConsumer;

public class FillContainer {

    public static BiConsumer<Level, BlockPos> fillContainerSlot(int slot, ItemStack stack) {
        return (level, pos) -> {
            if (level.getBlockEntity(pos) instanceof Container c) c.setItem(slot, stack.copy());
        };
    }

    public static BiConsumer<Level, BlockPos> fillFirstSlot(ItemLike item) {
        return fillContainerSlot(0, new ItemStack(item));
    }

    public static BiConsumer<Level, BlockPos> fillFirstSlot(ItemLike item, int count) {
        return fillContainerSlot(0, new ItemStack(item, count));
    }

    public static InteractionRegistrar.AfterAction fillOnConvert() {
        return (level, pos, old, held) -> {
            if (level.getBlockEntity(pos) instanceof Container c && old.getBlock().asItem() != Items.AIR) {
                fillFirstSlot(new ItemStack(old.getBlock()).getItem());
            }
        };
    }

    public static InteractionRegistrar.AfterAction fillHeldItem() {
        return (level, pos, old, held) -> {
            if (level.getBlockEntity(pos) instanceof Container c) {
                fillFirstSlot(held.copyWithCount(1).getItem());
            }
        };
    }
}