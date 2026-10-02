package net.tvoid.lib.register;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class ItemRegistrar {
    private final String modId;
    private final DeferredRegister<Item> items;

    public ItemRegistrar(String modId) {
        this.modId = modId;
        this.items = DeferredRegister.create(modId, Registries.ITEM);
    }

    public void register() {
        items.register();
    }

    public <T extends Item> RegistrySupplier<T> reg(String name, Function<Item.Properties, T> factory) {
        return reg(name, UnaryOperator.identity(), factory);
    }

    public RegistrySupplier<Item> reg(String name) {
        return reg(name, Item::new);
    }

    public <T extends Item> RegistrySupplier<T> reg(
            String name,
            UnaryOperator<Item.Properties> properties,
            Function<Item.Properties, T> factory) {
        return items.register(name, () -> {
            ResourceKey<Item> key = ResourceKey.create(Registries.ITEM,
                    Identifier.fromNamespaceAndPath(modId, name));
            return factory.apply(properties.apply(new Item.Properties().setId(key)));
        });
    }

 //   RegistrySupplier<T> item = (RegistrySupplier<T>) itemFactory.apply(properties.setId(id));
//        if (item instanceof BlockItem blockItem) {
//            blockItem.registerBlocks(Item.BY_BLOCK, item);
//        }
    //    return (RegistrySupplier<Item>) Registry.register(BuiltInRegistries.ITEM, id, item);
//}
}