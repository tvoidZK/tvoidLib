package net.tvoid.lib.item.interaction;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.InteractionEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.tvoid.lib.helper.BlockMatch;
import net.tvoid.lib.helper.ItemMatch;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class InteractionRegistrar {
    private final List<InteractionRecipe> recipes = new ArrayList<>();

    public InteractionRecipe convertBlock(Object from, Object with, Object to) {
        return add(BlockMatch.of(from), ItemMatch.of(with),
                new InteractionRecipe.BlockResult(blockSupplier(to)));
    }

    public InteractionRecipe convertBlock(Object from, Object with, Object to, AfterAction after) {
        return add(BlockMatch.of(from), ItemMatch.of(with),
                new InteractionRecipe.BlockResult(blockSupplier(to), after));
    }

    public InteractionRecipe convertBlock(Object from, Object with, Object to, BiConsumer<Level, BlockPos> after) {
        return add(BlockMatch.of(from), ItemMatch.of(with),
                new InteractionRecipe.BlockResult(blockSupplier(to), after));
    }

    private static Supplier<Block> blockSupplier(Object o) {
        return switch (o) {
            case Block b -> () -> b;
            case Supplier<?> s -> () -> (Block) s.get();
            default -> throw new IllegalArgumentException("Not a valid block output: " + o);
        };
    }

    public InteractionRecipe dropItem(Object from, Object with, Object drops) {
        return add(BlockMatch.of(from), ItemMatch.of(with),
                new InteractionRecipe.ItemResult(itemSupplier(drops)));
    }

    private static Supplier<Item> itemSupplier(Object o) {
        return switch (o) {
            case Item i -> () -> i;
            case Supplier<?> s -> () -> (Item) s.get();
            default -> throw new IllegalArgumentException("Not a valid item output: " + o);
        };
    }

    private InteractionRecipe add(Predicate<BlockState> from,
                                  Predicate<ItemStack> with,
                                  InteractionRecipe.Result result) {
        InteractionRecipe r = new InteractionRecipe(from, with, result);
        recipes.add(r);
        return r;
    }

    @FunctionalInterface
    public interface AfterAction {
        void run(Level level, BlockPos pos, BlockState oldState, ItemStack held);
    }

    public void reg() {
        InteractionEvent.RIGHT_CLICK_BLOCK.register((player, hand, pos, face) -> {
            Level level = player.level();
            ItemStack stack = player.getItemInHand(hand);
            BlockState state = level.getBlockState(pos);

            for (InteractionRecipe r : recipes) {
                if (r.from().test(state) && r.with().test(stack)) {
                    if (!level.isClientSide()) {
                        switch (r.result()) {
                            case InteractionRecipe.BlockResult b -> {
                                level.setBlockAndUpdate(pos, b.block().get().defaultBlockState());
                                b.after().run(level, pos, state, stack);
                            }
                            case InteractionRecipe.ItemResult i -> {
                                level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                                Containers.dropItemStack(level,
                                        pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                                        new ItemStack(i.item().get()));
                            }
                        }
                    }
                    return EventResult.interruptTrue();
                }
            }
            return EventResult.pass();
        });
    }
}