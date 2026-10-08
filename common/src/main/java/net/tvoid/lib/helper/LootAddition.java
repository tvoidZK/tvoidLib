package net.tvoid.lib.helper;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.function.Supplier;

public record LootAddition(
        ResourceKey<LootTable> table,
        Supplier<? extends Item> item,
        float chance,
        int min,
        int max) {}
