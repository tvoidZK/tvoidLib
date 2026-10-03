package net.tvoid.lib.register;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;

public class ArmorRegistrar extends ItemRegistrar {
    public ArmorRegistrar(String modId) { super(modId); }

    public RegistrySupplier<Item> helmet(String name, ArmorMaterial material, ArmorType type) {
        return reg(name, p -> new Item(p.humanoidArmor(material, ArmorType.HELMET)));
    }

    public RegistrySupplier<Item> chestplate(String name, ArmorMaterial material, ArmorType type) {
        return reg(name, p -> new Item(p.humanoidArmor(material, ArmorType.CHESTPLATE)));
    }

    public RegistrySupplier<Item> leggings(String name, ArmorMaterial material, ArmorType type) {
        return reg(name, p -> new Item(p.humanoidArmor(material, ArmorType.LEGGINGS)));
    }

    public RegistrySupplier<Item> boots(String name, ArmorMaterial material, ArmorType type) {
        return reg(name, p -> new Item(p.humanoidArmor(material, ArmorType.BOOTS)));
    }
}