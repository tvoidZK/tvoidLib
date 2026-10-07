package net.tvoid.lib.item.interaction;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.InteractionEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
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
    private final List<InteractionRecipe> interactionRecipes = new ArrayList<>();
    private final List<HandRecipe> handRecipes = new ArrayList<>();

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

    public HandRecipe convertHeld(Object main, Object off, Object result) {
        return convertHeld(main, off, result, 1);
    }

    public HandRecipe convertHeld(Object main, Object off, Object result, int count) {
        HandRecipe r = new HandRecipe(ItemMatch.of(main), ItemMatch.of(off), itemSupplier(result), count);
        handRecipes.add(r);
        return r;
    }

    private InteractionRecipe add(Predicate<BlockState> from,
                                  Predicate<ItemStack> with,
                                  InteractionRecipe.Result result) {
        InteractionRecipe r = new InteractionRecipe(from, with, result);
        interactionRecipes.add(r);
        return r;
    }

    private boolean checkHand(Player player, InteractionHand hand) {
        if (hand != InteractionHand.MAIN_HAND) return false;
        ItemStack main = player.getItemInHand(InteractionHand.MAIN_HAND);
        ItemStack off = player.getItemInHand(InteractionHand.OFF_HAND);

        for (HandRecipe r : handRecipes) {
            if (!r.main().test(main) || !r.off().test(off)) continue;
            if (!player.level().isClientSide()) {
                //main.shrink(1);
                ItemStack out = new ItemStack(r.result().get(), r.count());
                if (!player.getInventory().add(out)) {
                    Containers.dropItemStack(player.level(), player.getX(), player.getY(), player.getZ(), out);
                }
            }
            return true;
        }
        return false;
    }

    @FunctionalInterface
    public interface AfterAction {
        void run(Level level, BlockPos pos, BlockState oldState, ItemStack held);
    }

    public void reg() {
        InteractionEvent.RIGHT_CLICK_ITEM.register((player, hand) ->
                checkHand(player, hand)
                        ? EventResult.interruptTrue()
                        : EventResult.pass());

        InteractionEvent.RIGHT_CLICK_BLOCK.register((player, hand, pos, face) -> {
            if (checkHand(player, hand)) return EventResult.interruptTrue();
            Level level = player.level();
            ItemStack stack = player.getItemInHand(hand);
            BlockState state = level.getBlockState(pos);

            for (InteractionRecipe r : interactionRecipes) {
                if (!r.from().test(state) || !r.with().test(stack)) continue;
                if (!level.isClientSide()) {
                    switch (r.result()) {
                        case InteractionRecipe.BlockResult b -> {
                            level.setBlockAndUpdate(pos, b.block().get().defaultBlockState());
                            b.after().run(level, pos, state, stack);
                        }
                        case InteractionRecipe.ItemResult i -> {
                            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                            Containers.dropItemStack(
                                    level, pos.getX(), pos.getY(), pos.getZ(),
                                    new ItemStack(i.item().get()));
                        }
                    }
                }
                return EventResult.interruptTrue();
            }
            return EventResult.pass();
        });
    }
}