package net.tvoid.lib.register;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class BlockRegistrar {
    private final String modId;
    private final DeferredRegister<Block> blocks;

    public BlockRegistrar(String modId) {
        this.modId = modId;
        this.blocks = DeferredRegister.create(modId, Registries.BLOCK);
    }

    public void Blocks() {
        blocks.register();
    }

    public <T extends Block> RegistrySupplier<T> block(
            String name, Function<BlockBehaviour.Properties, T> factory) {
        return block(name, UnaryOperator.identity(), factory);
    }

    public RegistrySupplier<Block> block(String name) {
        return block(name, Block::new);
    }

    public <T extends Block> RegistrySupplier<T> block(
            String name,
            UnaryOperator<BlockBehaviour.Properties> properties,
            Function<BlockBehaviour.Properties, T> factory) {
        return blocks.register(name, () -> {
            ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK,
                    Identifier.fromNamespaceAndPath(modId, name));
            return factory.apply(properties.apply(BlockBehaviour.Properties.of().setId(key)));
        });
    }
}