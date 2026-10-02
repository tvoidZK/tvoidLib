package net.tvoid.testmod.world.item;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.Item;
import net.tvoid.lib.world.item.ItemRegistrar;
import net.tvoid.testmod.TestMod;

public class TestItems {
    private static final ItemRegistrar ITEM = new ItemRegistrar(TestMod.modId);

    public static final RegistrySupplier<Item> TEST_ITEM = ITEM.reg("test_item_0");
    public static final RegistrySupplier<Item> TEST_ITEM_SHORT = ITEM.reg("test_item_1");
    public static final RegistrySupplier<Item> TEST_ITEM_NEW = ITEM.reg("test_item_2", Item::new);
    public static final RegistrySupplier<Item> TEST_ITEM_LONG = ITEM.reg("test_item_3", p -> p.stacksTo(16), Item::new);

    public static final RegistrySupplier<Item> ITEM_SHORT = REG.item("item_0");
    public static final RegistrySupplier<Item> ITEM_NEW = REG.item("item_1", Item::new);
    public static final RegistrySupplier<Item> ITEM_LONG = REG.item("item_2", p -> p.stacksTo(16), Item::new);

    public static void init() {
        ITEM.register();
    }
}