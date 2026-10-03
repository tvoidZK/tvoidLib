package net.tvoid.testmod;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.tvoid.lib.tags.TagsHelper;

public class TestTags {
    private static final TagsHelper TAG = new TagsHelper(TestMod.modId);
    private static final TagsHelper C = new TagsHelper("c");
    private static final TagsHelper TB = new TagsHelper("terrablender");

    public static final TagKey<Item> TEST_ITEM_TAG = TAG.item("test_item_tag");
    public static final TagKey<Item> REPAIRS_TEST_ARMOR = TAG.item("repairs_test_armor");

    public static final TagKey<Block> TEST_BLOCK_TAG = TAG.block("test_block_tag");
    public static final TagKey<Block> INCORRECT_FOR_TEST_TOOL = TAG.block("incorrect_for_test_tool");
    public static final TagKey<Block> TEST_C_BLOCK_TAG = C.block("test_c_block_tag");

    public static final TagKey<Biome>  TEST_BIOME_TAG = TB.biome("test_biome_tag");
}