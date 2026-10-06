package net.tvoid.lib.register;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class BlockEntityRegistrar {
    private final DeferredRegister<BlockEntityType<?>> types;

    public BlockEntityRegistrar(String modId) {
        this.types = DeferredRegister.create(modId, Registries.BLOCK_ENTITY_TYPE);
    }

    public void reg() {
        types.register();
    }

    public <T extends BlockEntity> RegistrySupplier<BlockEntityType<T>> reg(
            String name,
            BiFunction<BlockPos, BlockState, T> factory,
            Supplier<? extends Block>... blocks) {
        return types.register(name, () -> new BlockEntityType<>(factory::apply,
                Arrays.stream(blocks).map(Supplier::get).collect(Collectors.toSet())));
    }
}