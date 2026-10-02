package net.tvoid.lib.references;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.item.equipment.trim.TrimPattern;
import net.minecraft.world.level.block.ColorCollection;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;

public class ModItemIds {
    private final String modId;
    public ModItemIds(String modId) {
        this.modId = modId;
    }

//    public static final String SMITHING_TEMPLATE_SUFFIX = "_smithing_template";

    public ResourceKey<Item> create(String name) {
        return ResourceKey.create(Registries.ITEM,
                Identifier.fromNamespaceAndPath(modId, name));
    }

    public ResourceKey<Item> createPotterySherd(ResourceKey<DecoratedPotPattern> sherd) {
        return sherd.dependent(Registries.ITEM,
                "_pottery_sherd");
    }

    public ResourceKey<Item> createArmorTrimSmithingTemplate(ResourceKey<TrimPattern> template) {
        return template.dependent(Registries.ITEM,
                "_armor_trim_smithing_template");
    }

    public ResourceKey<Item> createMusicDisc(ResourceKey<JukeboxSong> music) {
        return music.dependent(Registries.ITEM,
                (path) -> "music_disc_" + path);
    }

    public ResourceKey<Item> createSpawnEgg(ResourceKey<EntityType<?>> entity) {
        return entity.dependent(Registries.ITEM,
                "_spawn_egg");
    }

    public ColorCollection<ResourceKey<Item>> createSimpleColored(String baseName) {
        return ColorCollection.prefixWithColor(ColorCollection.create(baseName)).map(name -> create(name));
    }
}
