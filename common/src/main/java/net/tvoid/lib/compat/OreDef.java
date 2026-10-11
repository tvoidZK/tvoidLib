package net.tvoid.lib.compat;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.tvoid.lib.data.gen.LangGen;
import org.jetbrains.annotations.Nullable;

public record OreDef(String id, Identifier baseItem, int color, @Nullable String nameKey, int growthOdds, float stemOdds) {

    public static OreDef of(String id, String namespace, String path, int color) {
        return new OreDef(id, Identifier.fromNamespaceAndPath(namespace, path),
                color, null, 5, 0.5f);
    }

    public OreDef withGrowthOdds(int n) {
        return new OreDef(id, baseItem, color, nameKey, n, stemOdds);
    }

    public OreDef withStemOdds(float f) {
        return new OreDef(id, baseItem, color, nameKey, growthOdds, f);
    }

    public String modId() {
        return baseItem.getNamespace();
    }

    public Component displayName() {
        if (nameKey != null) return Component.translatable(nameKey);
        if (BuiltInRegistries.ITEM.containsKey(baseItem)) {
            return BuiltInRegistries.ITEM.getValue(baseItem).getDefaultInstance().getHoverName();
        }
        return Component.literal(LangGen.prettify(id));
    }
}