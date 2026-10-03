package net.tvoid.testmod.world.item;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.tvoid.lib.register.ArmorRegistrar;
import net.tvoid.lib.register.ItemRegistrar;
import net.tvoid.lib.register.ToolRegistrar;
import net.tvoid.lib.register.ToolSet;
import net.tvoid.testmod.TestMod;

public class TestItems {
    private static final ItemRegistrar ITEM = new ItemRegistrar(TestMod.modId);
    private static final ToolRegistrar TOOL = new ToolRegistrar(TestMod.modId);
    private static final ArmorRegistrar ARMOR = new ArmorRegistrar(TestMod.modId);

    public static final RegistrySupplier<Item> TEST_ITEM = ITEM.reg("test_item_0");
    public static final RegistrySupplier<Item> TEST_ITEM_SHORT = ITEM.reg("test_item_1");
    public static final RegistrySupplier<Item> TEST_ITEM_NEW = ITEM.reg("test_item_2", Item::new);
    public static final RegistrySupplier<Item> TEST_ITEM_LONG = ITEM.reg("test_item_3", p -> p.stacksTo(16), Item::new);

    public static final RegistrySupplier<Item> TEST_SWORD = TOOL.sword("test_sword", ToolMaterial.COPPER, 3.0F, 2);
    public static final RegistrySupplier<Item> TEST_PICKAXE = TOOL.pickaxe("test_sword", ToolMaterial.COPPER, 3.0F, 2);
    public static final RegistrySupplier<Item> TEST_AXE = TOOL.axe("test_sword", ToolMaterial.COPPER, 3.0F, 2);
    public static final RegistrySupplier<Item> TEST_SHOVEL = TOOL.shovel("test_sword", ToolMaterial.COPPER, 3.0F, 2);
    public static final RegistrySupplier<Item> TEST_HOE = TOOL.hoe("test_sword", ToolMaterial.COPPER, 3.0F, 2);
    public static final RegistrySupplier<Item> TEST_SPEAR = TOOL.spear("test_spear", ToolMaterial.COPPER, 0.95F, 0.95F, 0.6F, 2.5F, 11.0F, 6.75F, 5.1F, 11.25F, 4.6F);

    public static final ToolMaterial TEST_MATERIAL = new ToolMaterial(
            BlockTags.INCORRECT_FOR_TEST_TOOL, 500, 6.0f, 2.0f, 14, ItemTags.COPPER_TOOL_MATERIALS);
    public static final ToolSet TEST_TOOL_SET = new ToolSet(
            TEST_SWORD, TEST_PICKAXE, TEST_AXE, TEST_SHOVEL, TEST_HOE, TEST_SPEAR
    );


    // public static final ColorCollection<RegistrySupplier<Item>> DYEABLE_TEST_ITEM = ITEM.ColorCollection.items(TestItemIds.DYEABLE_TEST_ITEM, (name, color) -> ITEM.item(name));

    public static void init() {
        ITEM.reg();
        TOOL.reg();
        ARMOR.reg();
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