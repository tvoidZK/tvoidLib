package net.tvoid.lib.register;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class ItemRegistrar {
    private final String modId;
    private final DeferredRegister<Item> items;
    public List<RegistrySupplier<? extends Item>> flat = new ArrayList<>();
    public List<RegistrySupplier<? extends Item>> handheld = new ArrayList<>();

    public ItemRegistrar(String modId) {
        this.modId = modId;
        this.items = DeferredRegister.create(modId, Registries.ITEM);
    }

    public void reg() {
        items.register();
    }

    public List<RegistrySupplier<? extends Item>> flatItems() {return flat;}
    public List<RegistrySupplier<? extends Item>> handheldItems() {return handheld;}

    public <T extends Item> RegistrySupplier<T> reg(String name, Function<Item.Properties, T> factory) {
        return reg(name, UnaryOperator.identity(), factory);
    }

    public RegistrySupplier<Item> reg(String name) {
        return reg(name, Item::new);
    }

    public <T extends Item> RegistrySupplier<T> reg(String name, UnaryOperator<Item.Properties> properties, Function<Item.Properties, T> factory) {
        return registerItem(name, properties, factory, flat, mainTab);
    }

    public <T extends Item> RegistrySupplier<T> regTool(
            String name,
            UnaryOperator<Item.Properties> properties,
            Function<Item.Properties, T> factory) {
        return registerItem(name, properties, factory, handheld, equipmentTab);
    }

    public <T extends Item> RegistrySupplier<T> regArmor(
            String name,
            UnaryOperator<Item.Properties> properties,
            Function<Item.Properties, T> factory) {
        return registerItem(name, properties, factory, flat, equipmentTab);
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

    private Consumer<RegistrySupplier<? extends Item>> mainTab = s -> {};
    private Consumer<RegistrySupplier<? extends Item>> equipmentTab = s -> {};

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

 //   RegistrySupplier<T> item = (RegistrySupplier<T>) itemFactory.apply(properties.setId(id));
//        if (item instanceof BlockItem blockItem) {
//            blockItem.registerBlocks(Item.BY_BLOCK, item);
//        }
    //    return (RegistrySupplier<Item>) Registry.register(BuiltInRegistries.ITEM, id, item);
//}
