package net.tvoid.testmod;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.tvoid.lib.register.TabRegistrar;
import net.tvoid.testmod.world.item.TestItems;

public class TestTabs {
    public static final TabRegistrar TABS = new TabRegistrar(TestMod.modId);

    public static final RegistrySupplier<CreativeModeTab> MAIN =
            TABS.create("main", () -> new ItemStack(TestItems.TEST_ITEM.get()));

    public static final RegistrySupplier<CreativeModeTab> EQUIPMENT =
            TABS.create("equipment", () -> new ItemStack(TestItems.TEST_PICKAXE.get()));

    public static void reg() {
        TABS.register();
    }
}
