package net.tvoid.lib.register;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class BlockRegistrar {
    private final String modId;
    private final DeferredRegister<Block> blocks;
    private final ItemRegistrar items;
    private final List<RegistrySupplier<? extends Block>> cubes = new ArrayList<>();
    private final List<RegistrySupplier<? extends Block>> custom = new ArrayList<>();

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

    public <T extends Block> RegistrySupplier<T> reg(String name, Function<BlockBehaviour.Properties, T> factory) {
        return registerBlock(name, UnaryOperator.identity(), factory, cubes);
    }

    public <T extends Block> RegistrySupplier<T> regCustom(
            String name, UnaryOperator<BlockBehaviour.Properties> properties,
            Function<BlockBehaviour.Properties, T> factory) {
        return registerBlock(name, properties, factory, custom);
    }

    public <T extends Block> RegistrySupplier<T> registerBlock(
            String name,
            UnaryOperator<BlockBehaviour.Properties> properties,
            Function<BlockBehaviour.Properties, T> factory,
            List<RegistrySupplier<? extends Block>> modelList) {
        RegistrySupplier<T> supplier = blocks.register(name, () -> {
            ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK,
                    Identifier.fromNamespaceAndPath(modId, name));
            return factory.apply(properties.apply(BlockBehaviour.Properties.of().setId(key)));
        });
        modelList.add(supplier);
        items.regBlock(name, p -> p.useBlockDescriptionPrefix(),
                p -> new BlockItem(supplier.get(), p));
        return supplier;
    }

    public List<RegistrySupplier<? extends Block>> cubes() { return cubes; }
    public List<RegistrySupplier<? extends Block>> custom() { return custom; }
}