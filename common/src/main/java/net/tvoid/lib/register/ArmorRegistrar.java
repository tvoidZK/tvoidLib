package net.tvoid.lib.register;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;

public class ArmorRegistrar extends ItemRegistrar {
    public ArmorRegistrar(String modId) { super(modId); }

    public RegistrySupplier<Item> helmet(String name, ArmorMaterial material, ArmorType type) {
        return regArmor(name, p -> p.humanoidArmor(material, ArmorType.HELMET), Item::new);
    }

    public RegistrySupplier<Item> chestplate(String name, ArmorMaterial material, ArmorType type) {
        return regArmor(name, p -> p.humanoidArmor(material, ArmorType.CHESTPLATE), Item::new);
    }

    public RegistrySupplier<Item> leggings(String name, ArmorMaterial material, ArmorType type) {
        return regArmor(name, p -> p.humanoidArmor(material, ArmorType.LEGGINGS), Item::new);
    }

    public RegistrySupplier<Item> boots(String name, ArmorMaterial material, ArmorType type) {
        return regArmor(name, p -> p.humanoidArmor(material, ArmorType.BOOTS), Item::new);
    }
}