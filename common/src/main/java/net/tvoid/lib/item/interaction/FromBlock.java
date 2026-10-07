package net.tvoid.lib.item.interaction;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public record FromBlock(Predicate<BlockState> from,
                        Predicate<ItemStack> with,
                        Result result) {

    public sealed interface Result permits ToBlock, ToItem, BlockInteraction {}

    public record ToBlock(Supplier<? extends Block> block, InteractionRegistrar.AfterAction after) implements Result {
        public ToBlock(Supplier<? extends Block> block) {
            this(block, (l, p, o, h) -> {});
        }
        public ToBlock(Supplier<? extends Block> block, BiConsumer<Level, BlockPos> after) {
            this(block, (l, p, o, h) -> after.accept(l, p));
        }
    }

    public record ToItem(Supplier<? extends Item> item) implements Result {}

    public record BlockInteraction(InteractionRegistrar.AfterAction action) implements Result {}
}