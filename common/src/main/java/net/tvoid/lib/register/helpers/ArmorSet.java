package net.tvoid.lib.register.helpers;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.Item;

public record ArmorSet(RegistrySupplier<Item> helmet,
                       RegistrySupplier<Item> chestplate,
                       RegistrySupplier<Item> leggings,
                       RegistrySupplier<Item> boots) {}
