package net.tvoid.lib.reference;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public record BlockItemId(ResourceKey<Block> block, ResourceKey<Item> item) {
    public static BlockItemId create(final Identifier blockId, final Identifier itemId) {
        return new BlockItemId(ResourceKey.create(Registries.BLOCK, blockId), ResourceKey.create(Registries.ITEM, itemId));
    }

    public static net.minecraft.references.BlockItemId create(final String blockName, final String itemName) {
        return create(Identifier.fromNamespaceAndPath(modId, blockName), Identifier.fromNamespaceAndPath(itemName));
    }

    public static net.minecraft.references.BlockItemId create(final String name) {
        Identifier id = Identifier.fromNamespaceAndPath(modId, name);
        return create(id, id);
    }
}