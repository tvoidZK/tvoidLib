package net.tvoid.lib.register;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class BlockRegistrar {
    private final String modId;
    private final DeferredRegister<Block> blocks;
    private final ItemRegistrar items;

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
        return registerBlock(name, UnaryOperator.identity(), factory);
    }

    public <T extends Block> RegistrySupplier<T> registerBlock(
            String name,
            UnaryOperator<BlockBehaviour.Properties> properties,
            Function<BlockBehaviour.Properties, T> factory) {
        RegistrySupplier<T> block = blocks.register(name, () -> {
            ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK,
                    Identifier.fromNamespaceAndPath(modId, name));
            return factory.apply(properties.apply(BlockBehaviour.Properties.of().setId(key)));
        });
        items.reg(name, p -> p.useBlockDescriptionPrefix(),
                p -> new BlockItem(block.get(), p));
        return block;
    }
}