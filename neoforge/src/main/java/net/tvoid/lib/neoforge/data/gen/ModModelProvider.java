package net.tvoid.lib.neoforge.data.gen;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.tvoid.lib.helper.BlockLayout;
import net.tvoid.lib.register.BlockRegistrar;
import net.tvoid.lib.register.ItemRegistrar;

import java.util.stream.Stream;

public class ModModelProvider extends ModelProvider {
    private final ItemRegistrar items;
    private final BlockRegistrar blocks;

    private BlockLayout layoutOf(Block block) {
        return blocks.layouts().getOrDefault(
                BuiltInRegistries.BLOCK.getKey(block).getPath(), BlockLayout.CUBE);
    }

    private boolean isCustom(Block block) {
        return layoutOf(block) == BlockLayout.CUSTOM;
    }

    public ModModelProvider(PackOutput output, String modId, ItemRegistrar items, BlockRegistrar blocks) {
        super(output, modId);
        this.items = items;
        this.blocks = blocks;
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        genItems(itemModels, items);
        genBlocks(blockModels, blocks);
    }

    public static void genItems(ItemModelGenerators gen, ItemRegistrar items) {
        items.flatItems().forEach(i -> gen.generateFlatItem(i.get(), ModelTemplates.FLAT_ITEM));
        items.handheldItems().forEach(i -> gen.generateFlatItem(i.get(), ModelTemplates.FLAT_HANDHELD_ITEM));
    }

    public void genBlocks(BlockModelGenerators gen, BlockRegistrar blocks) {
        BuiltInRegistries.BLOCK.stream()
                .filter(b -> BuiltInRegistries.BLOCK.getKey(b).getNamespace().equals(modId))
                .filter(b -> !isCustom(b))
                .forEach(b -> {
                    switch (layoutOf(b)) {
                        case CUBE -> gen.createTrivialCube(b);
                        case PILLAR -> gen.createTrivialBlock(b, TexturedModel.COLUMN);
                        case GRASS -> gen.createTrivialBlock(b, TexturedModel.CUBE_BOTTOM_TOP);
                        case CUSTOM -> {
                            return;
                        }
                    }
                });
    }

    @Override
    protected Stream<? extends Holder<Block>> getKnownBlocks() {
        return super.getKnownBlocks().filter(h -> !isCustom(h.value()));
    }
}