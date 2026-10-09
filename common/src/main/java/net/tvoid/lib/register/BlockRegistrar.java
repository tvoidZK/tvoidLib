package net.tvoid.lib.register;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.tvoid.lib.helper.BlockLayout;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class BlockRegistrar {
    private final String modId;
    private final DeferredRegister<Block> blocks;
    private final ItemRegistrar items;
    private final Map<String, BlockLayout> layouts = new HashMap<>();

    public BlockRegistrar(String modId, ItemRegistrar items) {
        this.modId = modId;
        this.items = items;
        this.blocks = DeferredRegister.create(modId, Registries.BLOCK);
    }

    public void reg() {
        blocks.register();
    }

    public RegistrySupplier<Block> reg(String name) {
        return reg(name, Block::new);
    }

    public RegistrySupplier<Block> reg(String name, BlockLayout layout) {
        return reg(name, layout, Block::new);
    }

    public <T extends Block> RegistrySupplier<T> reg(
            String name, Function<BlockBehaviour.Properties, T> factory) {
        return reg(name, UnaryOperator.identity(), factory);
    }

    public <T extends Block> RegistrySupplier<T> reg(
            String name, BlockLayout layout, Function<BlockBehaviour.Properties, T> factory) {
        return reg(name, layout, UnaryOperator.identity(), factory);
    }

    public <T extends Block> RegistrySupplier<T> reg(
            String name, UnaryOperator<BlockBehaviour.Properties> properties,
            Function<BlockBehaviour.Properties, T> factory) {
        return registerBlock(name, BlockLayout.CUBE, properties, factory);
    }

    public <T extends Block> RegistrySupplier<T> reg(
            String name, BlockLayout layout, UnaryOperator<BlockBehaviour.Properties> properties,
            Function<BlockBehaviour.Properties, T> factory) {
        return registerBlock(name, layout, properties, factory);
    }

    protected <T extends Block> RegistrySupplier<T> registerBlock(
            String name,
            BlockLayout layout,
            UnaryOperator<BlockBehaviour.Properties> properties,
            Function<BlockBehaviour.Properties, T> factory) {
        RegistrySupplier<T> supplier = blocks.register(name, () -> {
            ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK,
                    Identifier.fromNamespaceAndPath(modId, name));
            return factory.apply(properties.apply(BlockBehaviour.Properties.of().setId(key)));
        });
        layouts.put(name, layout);
        items.regBlock(name, p -> p.useBlockDescriptionPrefix(),
                p -> new BlockItem(supplier.get(), p));
        return supplier;
    }

    public Map<String, BlockLayout> layouts() { return layouts; }
}