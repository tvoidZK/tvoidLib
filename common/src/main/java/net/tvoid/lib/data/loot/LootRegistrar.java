package net.tvoid.lib.data.loot;

import dev.architectury.event.events.common.LootEvent;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProvider;
import net.minecraft.world.level.storage.loot.providers.number.ints.UniformGenerator;
import net.minecraft.world.level.storage.loot.providers.number.ints.ConstantValue;
import net.tvoid.lib.helper.LootAddition;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class LootRegistrar {
    private final List<LootAddition> additions = new ArrayList<>();

    private static Holder<ContextIntProvider> constant(int value) {
        return Holder.<ContextIntProvider>direct(new ConstantValue(value));
    }

    private static Holder<ContextIntProvider> uniform(int min, int max) {
        return Holder.<ContextIntProvider>direct(new UniformGenerator(constant(min), constant(max)));
    }

    public LootAddition addItem(Object table, Object item, float chance) {
        return addItem(table, item, chance, 1, 1);
    }

    public LootAddition addItem(Object table, Object item, float chance, int min, int max) {
        LootAddition a = new LootAddition(tableKey(table), itemSupplier(item), chance, min, max);
        additions.add(a);
        return a;
    }

    @SuppressWarnings("unchecked")
    private static ResourceKey<LootTable> tableKey(Object o) {
        return switch (o) {
            case ResourceKey<?> k -> (ResourceKey<LootTable>) k;
            case Block b -> b.getLootTable()
                    .orElseThrow(() -> new IllegalArgumentException("Block has no loot table: " + b));
            case EntityType<?> e -> e.getDefaultLootTable()
                    .orElseThrow(() -> new IllegalArgumentException("Entity has no loot table: " + e));
            default -> throw new IllegalArgumentException("Not a valid loot table: " + o);
        };
    }

    private static Supplier<Item> itemSupplier(Object o) {
        return switch (o) {
            case Item i -> () -> i;
            case Supplier<?> s -> () -> (Item) s.get();
            default -> throw new IllegalArgumentException("Not a valid item: " + o);
        };
    }

    public void reg() {
        LootEvent.MODIFY_LOOT_TABLE.register((provider, id, context, builtin) -> {
            for (LootAddition a : additions) {
                if (!a.table().equals(id)) continue;

                LootItem.Builder<?> entry = LootItem.lootTableItem(a.item().get());
                if (a.min() != a.max() || a.min() != 1) {
                    entry.apply(SetItemCountFunction.setCount(uniform(a.min(), a.max())));
                }

                context.addPool(LootPool.lootPool()
                        .setRolls(constant(1))
                        .when(LootItemRandomChanceCondition.randomChance(a.chance()))
                        .add(entry));
            }
        });
    }
}
