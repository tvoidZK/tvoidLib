package net.tvoid.lib.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class TagsHelper {
    private final String namespace;

    public TagsHelper(String namespace) {
        this.namespace = namespace;
    }

    public TagKey<Item> item(String path) {
        return TagKey.create(
                Registries.ITEM,
                Identifier.fromNamespaceAndPath(namespace, path));
    }

    public TagKey<Block> block(String path) {
        return TagKey.create(
                Registries.BLOCK,
                Identifier.fromNamespaceAndPath(namespace, path));
    }

    public TagKey<Biome> biome(String path) {
        return TagKey.create(
                Registries.BIOME,
                Identifier.fromNamespaceAndPath(namespace, path));
    }
}