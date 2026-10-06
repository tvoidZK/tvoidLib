package net.tvoid.lib.helper;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Predicate;
import java.util.function.Supplier;

public final class BlockMatch {
    private BlockMatch() {}

    public static Predicate<BlockState> not(Object o) {
        return of(o).negate();
    }

    public static Predicate<BlockState> except(Object include, Object exclude) {
        return of(include).and(of(exclude).negate());
    }

    public static Predicate<BlockState> any() { return s -> true; }



    @SuppressWarnings("unchecked")
    public static Predicate<BlockState> of(Object o) {
        return switch (o) {
            case Block b -> s -> s.is(b);
            case TagKey<?> t when t.isFor(Registries.BLOCK) -> {
                TagKey<Block> tag = (TagKey<Block>) t;
                yield s -> s.is(tag);
            }
            case Predicate<?> p -> (Predicate<BlockState>) p;
            case Supplier<?> sup -> s -> s.is((Block) sup.get());
            default -> throw new IllegalArgumentException("Not a valid block match: " + o);
        };
    }
}