package net.tvoid.testmod.world.item;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.tvoid.lib.register.helpers.ToolSet;
import net.tvoid.lib.register.ItemRegistrar;
import net.tvoid.lib.register.helpers.SpearStats;
import net.tvoid.testmod.TestMod;
import net.tvoid.testmod.TestTabs;
import net.tvoid.testmod.TestTags;

public class TestItems {
    public static final ItemRegistrar ITEM = new ItemRegistrar(TestMod.modId);

    static { ITEM.tabs(TestTabs.TABS, TestTabs.MAIN, TestTabs.EQUIPMENT); }

    public static final RegistrySupplier<Item> TEST_ITEM = ITEM.reg("test_item_0");
    public static final RegistrySupplier<Item> TEST_ITEM_SHORT = ITEM.reg("test_item_1");
    public static final RegistrySupplier<Item> TEST_ITEM_NEW = ITEM.reg("test_item_2", Item::new);
    public static final RegistrySupplier<Item> TEST_ITEM_LONG = ITEM.reg("test_item_3", p -> p.stacksTo(16), Item::new);

    public static final RegistrySupplier<Item> TEST_SWORD = ITEM.sword("test", ToolMaterial.COPPER, 3.0F, -2.4F);
    public static final RegistrySupplier<Item> TEST_PICKAXE = ITEM.pickaxe("test", ToolMaterial.COPPER, 1.0F, -2.8F);
    public static final RegistrySupplier<Item> TEST_AXE = ITEM.axe("test", ToolMaterial.COPPER, 7.0F, -3.2F);
    public static final RegistrySupplier<Item> TEST_SHOVEL = ITEM.shovel("test", ToolMaterial.COPPER, 1.5F, -3.0F);
    public static final RegistrySupplier<Item> TEST_HOE = ITEM.hoe("test", ToolMaterial.COPPER, -1.0F, -2.0F);
    public static final RegistrySupplier<Item> TEST_SPEAR = ITEM.spear("test", ToolMaterial.COPPER,
            new SpearStats(0.85F, 0.82F, 0.65F, 4.0F, 12.0F, 8.25F, 5.1F, 12.5F, 4.6F));

    /*
    private static final ToolSet TEST_SET = new ToolSet("test", ToolMaterial.IRON);
    public static final RegistrySupplier<Item> TEST_SET_SWORD = TEST_SET.sword(3.0F, -2.4F);
    public static final RegistrySupplier<Item> TEST_SET_PICKAXE = TEST_SET.pickaxe(1.0F, -2.8F);
    public static final RegistrySupplier<Item> TEST_SET_AXE = TEST_SET.axe(6.0F, -3.1F);
    public static final RegistrySupplier<Item> TEST_SET_SHOVEL = TEST_SET.shovel(1.5F, -3.0F);
    public static final RegistrySupplier<Item> TEST_SET_HOE = TEST_SET.hoe(-2.0F, -1.0F);
    public static final RegistrySupplier<Item> TEST_SET_SPEAR = TEST_SET.spear(
            new SpearStats(0.95F, 0.95F, 0.6F, 2.5F, 11.0F, 6.75F, 5.1F, 11.25F, 4.6F));
    */

    public static final ToolMaterial TEST_MATERIAL = new ToolMaterial(
            TestTags.INCORRECT_FOR_TEST_TOOL, 500, 6.0f, 2.0f, 14, ItemTags.COPPER_TOOL_MATERIALS);
    public static final ToolSet TEST_TOOL_SET = new ToolSet(
            TEST_SWORD, TEST_PICKAXE, TEST_AXE, TEST_SHOVEL, TEST_HOE, TEST_SPEAR
    );


    // public static final ColorCollection<RegistrySupplier<Item>> DYEABLE_TEST_ITEM = ITEM.ColorCollection.items(TestItemIds.DYEABLE_TEST_ITEM, (name, color) -> ITEM.item(name));

    public static void reg() {
        ITEM.reg();
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