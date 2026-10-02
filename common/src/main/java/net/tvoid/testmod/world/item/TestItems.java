package net.tvoid.testmod.world.item;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.Item;
import net.tvoid.lib.world.item.ItemRegistrar;
import net.tvoid.testmod.TestMod;

public class TestItems {
    private static final ItemRegistrar ITEM = new ItemRegistrar(TestMod.modId);

    public static final RegistrySupplier<Item> TEST_ITEM = ITEM.reg("test_item_0");
    public static final RegistrySupplier<Item> TEST_ITEM_SHORT = ITEM.reg("test_item_1");
    public static final RegistrySupplier<Item> TEST_ITEM_NEW = ITEM.reg("test_item_2", Item::new);
    public static final RegistrySupplier<Item> TEST_ITEM_LONG = ITEM.reg("test_item_3", p -> p.stacksTo(16), Item::new);

    // public static final ColorCollection<RegistrySupplier<Item>> DYEABLE_TEST_ITEM = ITEM.ColorCollection.items(TestItemIds.DYEABLE_TEST_ITEM, (name, color) -> ITEM.item(name));

    public static void init() {
        ITEM.register();
    }
}


//package net.tvoid.testmod.references;
//
//import net.minecraft.world.entity.EntityTypeIds;
//import net.minecraft.world.item.JukeboxSongs;
//import net.minecraft.world.item.equipment.trim.TrimPatterns;
//import net.minecraft.world.level.block.ColorCollection;
//import net.minecraft.resources.ResourceKey;
//import net.minecraft.world.item.Item;
//import net.tvoid.lib.references.ModItemIds;
//import net.tvoid.testmod.TestMod;
//
// doesn't exist as a block
//public class TestItemIds {
//    private static final ModItemIds KEY = new ModItemIds(TestMod.modId);
//
//    // vehicles
//    // armor
//    // animal equippables
//    // tools
//    // weapons
//    // unplaceable food
//    // ingots/minerals
//    // mob drops and other ingredients
//    // maps
//    // other items
//    public static final ResourceKey<Item> TEST_ITEM = KEY.create("test_item_0");
//    public static final ResourceKey<Item> TEST_ITEM_SHORT = KEY.create("test_item_1");
//    public static final ResourceKey<Item> TEST_ITEM_NEW = KEY.create("test_item_2");
//    public static final ResourceKey<Item> TEST_ITEM_LONG = KEY.create("test_item_3");
//    // spawn eggs
//    public static final ResourceKey<Item> TEST_ENTITY_SPAWN_EGG;
//    // music discs, banner patterns
//    public static final ResourceKey<Item> MUSIC_DISC_TEST;
//    // smithing templates, pottery sherds
//    public static final ResourceKey<Item> TEST_ARMOR_TRIM_SMITHING_TEMPLATE;
//    // multicolors (wool etc)
//    public static final ColorCollection<ResourceKey<Item>> DYEABLE_TEST_ITEM;
//
//    static {
//        TEST_ENTITY_SPAWN_EGG = KEY.createSpawnEgg(EntityTypeIds.TEST_ENTITY);
//        MUSIC_DISC_TEST = KEY.createMusicDisc(JukeboxSongs.TEST_SONG);
//        TEST_ARMOR_TRIM_SMITHING_TEMPLATE = KEY.createArmorTrimSmithingTemplate(TrimPatterns.TEST_PATTERN);
//        DYEABLE_TEST_ITEM = KEY.createSimpleColored("dyeable_test_item");
//    }
//}