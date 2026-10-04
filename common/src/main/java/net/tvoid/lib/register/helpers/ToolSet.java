package net.tvoid.lib.register.helpers;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.Item;

public record ToolSet(RegistrySupplier<Item> sword,
                      RegistrySupplier<Item> pickaxe,
                      RegistrySupplier<Item> axe,
                      RegistrySupplier<Item> shovel,
                      RegistrySupplier<Item> hoe,
                      RegistrySupplier<Item> spear) {
}