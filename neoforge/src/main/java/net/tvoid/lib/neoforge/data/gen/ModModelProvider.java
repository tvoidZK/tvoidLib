package net.tvoid.lib.neoforge.data.gen;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.tvoid.lib.helper.BlockLayout;
import net.tvoid.lib.register.BlockRegistrar;
import net.tvoid.lib.register.ItemRegistrar;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class ModModelProvider extends ModelProvider {
    private final ItemRegistrar items;
    private final BlockRegistrar blocks;

    private BlockLayout layoutOf(Block block) {
        return blocks.layouts().getOrDefault(
                BuiltInRegistries.BLOCK.getKey(block).getPath(), BlockLayout.CUBE);
    }

    private boolean isCustom(Block block) {
        BlockLayout l = layoutOf(block);
        return l == BlockLayout.CUSTOM || l == BlockLayout.CUSTOM_ITEM;
    }

    private boolean hasHandWrittenItem(Item item) {
        String root = System.getProperty("data.gen.resources");
        if (root == null) return false;
        String name = BuiltInRegistries.ITEM.getKey(item).getPath();
        return Files.exists(Path.of(root, "assets", modId, "items", name + ".json"));
    }

    private Material material(String name) {
        return new Material(Identifier.fromNamespaceAndPath(modId, "item/" + name));
    }

    public ModModelProvider(PackOutput output, String modId, ItemRegistrar items, BlockRegistrar blocks) {
        super(output, modId);
        this.items = items;
        this.blocks = blocks;
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        genItems(itemModels, items);
        for (ItemRegistrar.TintedItem t : items.tintedItems()) {
            Item item = t.item().get();
            if (hasHandWrittenItem(item)) continue;

            Identifier model = blockModels.createTwoLayeredItemModel(item,
                    material(t.model()), material(t.model() + "_overlay"));

            blockModels.itemModelOutput.accept(item,
                    ItemModelUtils.tintedModel(model,
                            ItemModelUtils.constantTint(0xFFFFFF),
                            ItemModelUtils.constantTint(t.color())));
        }
        genBlocks(blockModels, blocks);
    }

    public void genItems(ItemModelGenerators gen, ItemRegistrar items) {
        BuiltInRegistries.BLOCK.stream()
                .filter(b -> BuiltInRegistries.BLOCK.getKey(b).getNamespace().equals(modId))
                .filter(b -> layoutOf(b) == BlockLayout.CUSTOM_ITEM)
                .forEach(b -> gen.generateFlatItem(b.asItem(), ModelTemplates.FLAT_ITEM));

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
                        case SHROOM -> genStaged(gen, b, BlockModelGenerators.PlantType.NOT_TINTED);
                        case SHROOM_TINTED -> genStaged(gen, b, BlockModelGenerators.PlantType.TINTED);
                        case CUSTOM_ITEM, CUSTOM -> {}
                    }
                });
    }

    public void genStaged(BlockModelGenerators gen, Block b, BlockModelGenerators.PlantType plant) {
        if (!(b.getStateDefinition().getProperty("age") instanceof IntegerProperty age)) {
            throw new IllegalStateException(
                    "Shroom layout needs age: "
                            + BuiltInRegistries.BLOCK.getKey(b));
        }
        int[] stages = age.getPossibleValues().stream().mapToInt(Integer::intValue).sorted().toArray();
        gen.createCrossBlock(b, plant, age, stages);
    }

    @Override
    protected Stream<? extends Holder<Block>> getKnownBlocks() {
        return super.getKnownBlocks().filter(h -> !isCustom(h.value()));
    }
}