package net.tvoid.lib.asset;

import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.tvoid.lib.register.ItemRegistrar;

public class ItemModels {
    public static void items(ItemModelGenerators gen, ItemRegistrar items) {
        items.flatItems().forEach(i -> gen.generateFlatItem(i.get(), ModelTemplates.FLAT_ITEM));
        items.handheldItems().forEach(i -> gen.generateFlatItem(i.get(), ModelTemplates.FLAT_HANDHELD_ITEM));
    }
}