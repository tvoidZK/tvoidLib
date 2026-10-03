package net.tvoid.lib.register;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;

public class ToolRegistrar extends ItemRegistrar {
    public ToolRegistrar(String modId) { super(modId); }

    public RegistrySupplier<Item> sword(String name, ToolMaterial mat, float dmg, float speed) {
        return regTool(name, p -> p.sword(mat, dmg, speed), Item::new);
    }

    public RegistrySupplier<Item> pickaxe(String name, ToolMaterial mat, float dmg, float speed) {
        return regTool(name, p -> p.pickaxe(mat, dmg, speed), Item::new);
    }

    public RegistrySupplier<Item> axe(String name, ToolMaterial mat, float dmg, float speed) {
        return regTool(name, p -> p.axe(mat, dmg, speed), Item::new);
    }

    public RegistrySupplier<Item> shovel(String name, ToolMaterial mat, float dmg, float speed) {
        return regTool(name, p -> p.shovel(mat, dmg, speed), Item::new);
    }

    public RegistrySupplier<Item> hoe(String name, ToolMaterial mat, float dmg, float speed) {
        return regTool(name, p -> p.hoe(mat, dmg, speed), Item::new);
    }

    public RegistrySupplier<Item> spear(String name, ToolMaterial mat, float atkDuration, float dmgMultiplier,
                                        float delay, final float dismountTime, float dismountThresh, float knockbackTime,
                                        float knockbackThresh, float dmgTime, float dmgThresh) {
        return regTool(name, p -> p.spear(mat, atkDuration, dmgMultiplier, delay, dismountTime, dismountThresh,
                knockbackTime,knockbackThresh, dmgTime, dmgThresh), Item::new);
    }
}