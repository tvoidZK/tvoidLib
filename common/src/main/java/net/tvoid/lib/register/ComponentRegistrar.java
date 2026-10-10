package net.tvoid.lib.register;

import com.mojang.serialization.Codec;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

import java.util.function.UnaryOperator;

public class ComponentRegistrar {
    private DeferredRegister<DataComponentType<?>> components;

    public ComponentRegistrar(String modId) {
        this.components = DeferredRegister.create(modId, Registries.DATA_COMPONENT_TYPE);
    }

    public void reg() {
        components.register();
    }

    // saved and synced to client
    public <T> RegistrySupplier<DataComponentType<T>> reg(
            String name, Codec<T> codec, StreamCodec<? super RegistryFriendlyByteBuf, T> network) {
        return reg(name, b -> b.persistent(codec).networkSynchronized(network));
    }

    public <T> RegistrySupplier<DataComponentType<T>> reg(
            String name, UnaryOperator<DataComponentType.Builder<T>> builder) {
        return components.register(name, () -> builder.apply(DataComponentType.<T>builder()).build());
    }
}