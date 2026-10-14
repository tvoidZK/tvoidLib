package net.tvoid.lib.register;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.tvoid.lib.helper.BlockLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class BlockRegistrar {
    private final String modId;
    private final DeferredRegister<Block> blocks;
    private final ItemRegistrar items;
    private final Map<String, BlockLayout> layouts = new HashMap<>();
    private final Map<String, String> tintModels = new HashMap<>();
    private final List<TintedBlock> tinted = new ArrayList<>();

    public record TintedBlock(Supplier<? extends Block> block, String model, int color) {}

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
            String name, BlockLayout layout, UnaryOperator<BlockBehaviour.Properties> properties,
            Function<BlockBehaviour.Properties, T> factory,
            BiFunction<Block, Item.Properties, ? extends BlockItem> itemFactory) {
        return registerBlock(name, layout, properties, factory, itemFactory);
    }

    public <T extends Block> RegistrySupplier<T> reg(
            String name, UnaryOperator<BlockBehaviour.Properties> properties,
            Function<BlockBehaviour.Properties, T> factory) {
        return registerBlock(name, BlockLayout.CUBE, properties, factory, BlockItem::new);
    }

    public <T extends Block> RegistrySupplier<T> reg(
            String name, BlockLayout layout, UnaryOperator<BlockBehaviour.Properties> properties,
            Function<BlockBehaviour.Properties, T> factory) {
        return registerBlock(name, layout, properties, factory, BlockItem::new);
    }

    public <T extends Block> RegistrySupplier<T> regTinted(
            String name, BlockLayout layout, String model, int color, UnaryOperator<BlockBehaviour.Properties> properties,
            Function<BlockBehaviour.Properties, T> factory) {
        RegistrySupplier<T> s = registerBlock(name, layout, properties, factory, BlockItem::new);
        items.tint(() -> s.get().asItem(), model, color);
        tinted.add(new TintedBlock(s, model, color));
        tintModels.put(name, model);
        return s;
    }

    protected <T extends Block> RegistrySupplier<T> registerBlock(
            String name,
            BlockLayout layout,
            UnaryOperator<BlockBehaviour.Properties> properties,
            Function<BlockBehaviour.Properties, T> factory,
            BiFunction<Block, Item.Properties, ? extends BlockItem> itemFactory) {
        RegistrySupplier<T> supplier = blocks.register(name, () -> {
            ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK,
                    Identifier.fromNamespaceAndPath(modId, name));
            return factory.apply(properties.apply(BlockBehaviour.Properties.of().setId(key)));
        });
        layouts.put(name, layout);
        items.regBlock(name, p -> p.useBlockDescriptionPrefix(),
                p -> itemFactory.apply(supplier.get(), p));
        return supplier;
    }

    public Map<String, BlockLayout> layouts() { return layouts; }
    public Map<String, String> tintModels() { return tintModels; }
    public List<TintedBlock> tintedBlocks() { return tinted; }
}