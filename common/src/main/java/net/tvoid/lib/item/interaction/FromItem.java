package net.tvoid.lib.item.interaction;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;
import java.util.function.Supplier;

public record FromItem(
        Predicate<ItemStack> main,
        Predicate<ItemStack> off,
        Supplier<? extends Item> result,
        int count) {}