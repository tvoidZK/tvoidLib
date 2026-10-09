package net.tvoid.lib.register;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.tvoid.lib.helper.ArmorSet;
import net.tvoid.lib.helper.SpearStats;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class ItemRegistrar {
    private final String modId;
    private final DeferredRegister<Item> items;
    private final List<RegistrySupplier<? extends Item>> block = new ArrayList<>();
    private Consumer<RegistrySupplier<? extends Item>> mainTab = s -> {};
    private Consumer<RegistrySupplier<? extends Item>> equipmentTab = s -> {};
    public List<RegistrySupplier<? extends Item>> flat = new ArrayList<>();
    public List<RegistrySupplier<? extends Item>> handheld = new ArrayList<>();

    public ItemRegistrar(String modId) {
        this.modId = modId;
        this.items = DeferredRegister.create(modId, Registries.ITEM);
    }

    public void reg() {
        items.register();
    }

    public RegistrySupplier<Item> reg(String name) {
        return reg(name, Item::new);
    }

    public <T extends Item> RegistrySupplier<T> reg(String name, Function<Item.Properties, T> factory) {
        return reg(name, UnaryOperator.identity(), factory);
    }

    public RegistrySupplier<Item> sword(String prefix, ToolMaterial mat, Float dmg, Float speed) {
        return regTool(prefix + "_sword", p -> p.sword(mat, dmg, speed), Item::new);
    }

    public RegistrySupplier<Item> pickaxe(String prefix, ToolMaterial mat, Float dmg, Float speed) {
        return regTool(prefix + "_pickaxe", p -> p.pickaxe(mat, dmg, speed), Item::new);
    }

    public RegistrySupplier<Item> axe(String prefix, ToolMaterial mat, Float dmg, Float speed) {
        return regTool(prefix + "_axe", p -> p.axe(mat, dmg, speed), Item::new);
    }

    public RegistrySupplier<Item> shovel(String prefix, ToolMaterial mat, Float dmg, Float speed) {
        return regTool(prefix + "_shovel", p -> p.shovel(mat, dmg, speed), Item::new);
    }

    public RegistrySupplier<Item> hoe(String prefix, ToolMaterial mat, Float dmg, Float speed) {
        return regTool(prefix + "_hoe", p -> p.hoe(mat, dmg, speed), Item::new);
    }

    public RegistrySupplier<Item> spear(String prefix, ToolMaterial mat, SpearStats s) {
        return regTool(prefix + "_spear", p -> p.spear(
                mat, s.atkDuration(), s.dmgMultiplier(), s.delay(), s.dismountTime(), s.dismountThresh(),
                s.knockbackTime(), s.knockbackThresh(), s.dmgTime(), s.dmgThresh()), Item::new);
    }

    public RegistrySupplier<Item> helmet(String prefix, ArmorMaterial material) {
        return regArmor(prefix + "_helmet", p -> p.humanoidArmor(material, ArmorType.HELMET), Item::new);
    }

    public RegistrySupplier<Item> chestplate(String prefix, ArmorMaterial material) {
        return regArmor(prefix + "_chestplate", p -> p.humanoidArmor(material, ArmorType.CHESTPLATE), Item::new);
    }

    public RegistrySupplier<Item> leggings(String prefix, ArmorMaterial material) {
        return regArmor(prefix + "_leggings", p -> p.humanoidArmor(material, ArmorType.LEGGINGS), Item::new);
    }

    public RegistrySupplier<Item> boots(String prefix, ArmorMaterial material) {
        return regArmor(prefix + "_boots", p -> p.humanoidArmor(material, ArmorType.BOOTS), Item::new);
    }

    public ArmorSet armorSet(String prefix, ArmorMaterial material) {
        return new ArmorSet(
                helmet(prefix, material),
                chestplate(prefix, material),
                leggings(prefix, material),
                boots(prefix, material));
    }

    public <T extends Item> RegistrySupplier<T> reg(String name, UnaryOperator<Item.Properties> properties, Function<Item.Properties, T> factory) {
        return registerItem(name, properties, factory, flat, mainTab);
    }

    public <T extends Item> RegistrySupplier<T> regTool(String name, UnaryOperator<Item.Properties> properties, Function<Item.Properties, T> factory) {
        return registerItem(name, properties, factory, handheld, equipmentTab);
    }

    public <T extends Item> RegistrySupplier<T> regArmor(String name, UnaryOperator<Item.Properties> properties, Function<Item.Properties, T> factory) {
        return registerItem(name, properties, factory, flat, equipmentTab);
    }

    public <T extends Item> RegistrySupplier<T> regBlock(String name, UnaryOperator<Item.Properties> properties, Function<Item.Properties, T> factory) {
        return registerItem(name, properties, factory, block, mainTab);
    }

    protected <T extends Item> RegistrySupplier<T> registerItem(
            String name,
            UnaryOperator<Item.Properties> properties,
            Function<Item.Properties, T> factory,
            List<RegistrySupplier<? extends Item>> modelList,
            Consumer<RegistrySupplier<? extends Item>> tab) {
        RegistrySupplier<T> supplier = items.register(name, () -> {
            ResourceKey<Item> key = ResourceKey.create(Registries.ITEM,
                        Identifier.fromNamespaceAndPath(modId, name));
                    return factory.apply(properties.apply(new Item.Properties().setId(key)));
        });
        modelList.add(supplier);
        tab.accept(supplier);
        return supplier;
        }

    public List<RegistrySupplier<? extends Item>> blockItems() { return block; }
    public List<RegistrySupplier<? extends Item>> flatItems() {return flat;}
    public List<RegistrySupplier<? extends Item>> handheldItems() {return handheld;}

    public ItemRegistrar tabs(TabRegistrar registrar,
                              RegistrySupplier<CreativeModeTab> main,
                              RegistrySupplier<CreativeModeTab> equipment) {
        this.mainTab = s -> registrar.add(main, s);
        this.equipmentTab = s -> registrar.add(equipment, s);
        return this;
    }

    protected Consumer<RegistrySupplier<? extends Item>> mainTab() { return mainTab; }
    protected Consumer<RegistrySupplier<? extends Item>> equipmentTab() { return equipmentTab; }
}