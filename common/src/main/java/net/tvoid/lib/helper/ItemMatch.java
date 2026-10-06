package net.tvoid.lib.helper;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;
import java.util.function.Supplier;

public final class ItemMatch {
    private ItemMatch() {}

    public static Predicate<ItemStack> not(Object o) {
        return of(o).negate();
    }

    public static Predicate<ItemStack> except(Object include, Object exclude) {
        return of(include).and(of(exclude).negate());
    }

    public static Predicate<ItemStack> any() { return s -> true; }
    public static Predicate<ItemStack> emptyHand() { return ItemStack::isEmpty; }

    @SuppressWarnings("unchecked")
    public static Predicate<ItemStack> of(Object o) {
        return switch (o) {
            case Item i -> s -> s.is(i);
            case TagKey<?> t when t.isFor(Registries.ITEM) -> {
                TagKey<Item> tag = (TagKey<Item>) t;
                yield s -> s.is(tag);
            }
            case Predicate<?> p -> (Predicate<ItemStack>) p;
            case Supplier<?> sup -> s -> s.is((Item) sup.get());
            default -> throw new IllegalArgumentException("Not a valid item match: " + o);
        };
    }
}