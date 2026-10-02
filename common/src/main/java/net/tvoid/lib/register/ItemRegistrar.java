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

    public void Items() {
        items.register();
    }

    public <T extends Item> RegistrySupplier<T> item(String name, Function<Item.Properties, T> factory) {
        return item(name, UnaryOperator.identity(), factory);
    }

    public RegistrySupplier<Item> item(String name) {
        return item(name, Item::new);
    }

    public <T extends Item> RegistrySupplier<T> item(
            String name,
            UnaryOperator<Item.Properties> properties,
            Function<Item.Properties, T> factory) {
        return items.register(name, () -> {
            ResourceKey<Item> key = ResourceKey.create(Registries.ITEM,
                    Identifier.fromNamespaceAndPath(modId, name));
            return factory.apply(properties.apply(new Item.Properties().setId(key)));
        });
    }
}