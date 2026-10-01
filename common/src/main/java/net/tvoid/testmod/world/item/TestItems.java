package net.tvoid.testmod.world.item;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.Item;
import net.tvoid.lib.world.item.ItemRegistrar;
import net.tvoid.testmod.TestMod;

public class TestItems {
    private static final ItemRegistrar REG = new ItemRegistrar(TestMod.modId);

    public static final RegistrySupplier<Item> ITEM_SHORT = REG.item("item_0");
    public static final RegistrySupplier<Item> ITEM_NEW = REG.item("item_1", Item::new);
    public static final RegistrySupplier<Item> ITEM_LONG = REG.item("item_2", p -> p.stacksTo(16), Item::new);

    public static void reg() {
        REG.Items();
    }
}