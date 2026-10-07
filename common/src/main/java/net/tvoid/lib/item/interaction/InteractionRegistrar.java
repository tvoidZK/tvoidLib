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
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.tvoid.lib.helper.BlockMatch;
import net.tvoid.lib.helper.ItemMatch;
import net.tvoid.lib.helper.SmeltingTicks;
import net.tvoid.lib.mixin.FurnaceAccessor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class InteractionRegistrar {
    private final List<FromBlock> BlockTransformations = new ArrayList<>();
    private final List<FromItem> ItemTransformations = new ArrayList<>();

    private static Supplier<Block> blockSupplier(Object o) {
        return switch (o) {
            case Block b -> () -> b;
            case Supplier<?> s -> () -> (Block) s.get();
            default -> throw new IllegalArgumentException("Not a valid block output: " + o);
        };
    }

    private static Supplier<Item> itemSupplier(Object o) {
        return switch (o) {
            case Item i -> () -> i;
            case Supplier<?> s -> () -> (Item) s.get();
            default -> throw new IllegalArgumentException("Not a valid item output: " + o);
        };
    }

    public static AfterAction smeltItem() {
        return smeltItem(1);
    }

    public static AfterAction smeltItem(double items) {
        int t = SmeltingTicks.furnaceItems(items);
        return (level, pos, old, held) -> {
            if (level.getBlockEntity(pos) instanceof AbstractFurnaceBlockEntity f
                    && !f.getItem(0).isEmpty()) {
                FurnaceAccessor a = (FurnaceAccessor) f;
                a.tvoid$setLitTimeRemaining(t);
                a.tvoid$setLitTotalTime(t);
                f.setChanged();
            }
        };
    }

    public FromBlock convertBlock(Object from, Object with, Object to) {
        return blockTransformation(BlockMatch.of(from), ItemMatch.of(with),
                new FromBlock.ToBlock(blockSupplier(to)));
    }

    public FromBlock convertBlock(Object from, Object with, Object to, AfterAction after) {
        return blockTransformation(BlockMatch.of(from), ItemMatch.of(with),
                new FromBlock.ToBlock(blockSupplier(to), after));
    }

    public FromBlock convertBlock(Object from, Object with, Object to, BiConsumer<Level, BlockPos> after) {
        return blockTransformation(BlockMatch.of(from), ItemMatch.of(with),
                new FromBlock.ToBlock(blockSupplier(to), after));
    }

    public FromBlock dropItem(Object from, Object with, Object drops) {
        return blockTransformation(BlockMatch.of(from), ItemMatch.of(with),
                new FromBlock.ToItem(itemSupplier(drops)));
    }

    public FromItem convertHeld(Object main, Object off, Object result) {
        return convertHeld(main, off, result, 1);
    }

    public FromItem convertHeld(Object main,
                                  Object off,
                                  Object result,
                                  int count) {
        FromItem r = new FromItem(ItemMatch.of(main), ItemMatch.of(off), itemSupplier(result), count);
        ItemTransformations.add(r);
        return r;
    }

    public FromBlock blockInteraction(Object from,
                                      Object with,
                                      AfterAction action) {
        return blockTransformation(BlockMatch.of(from), ItemMatch.of(with), new FromBlock.BlockInteraction(action));
    }

    private FromBlock blockTransformation(Predicate<BlockState> from,
                                          Predicate<ItemStack> with,
                                          FromBlock.Result result) {
        FromBlock r = new FromBlock(from, with, result);
        BlockTransformations.add(r);
        return r;
    }

    private boolean checkHand(Player player, InteractionHand hand) {
        if (hand != InteractionHand.MAIN_HAND) return false;
        ItemStack main = player.getItemInHand(InteractionHand.MAIN_HAND);
        ItemStack off = player.getItemInHand(InteractionHand.OFF_HAND);

        for (FromItem r : ItemTransformations) {
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

            for (FromBlock r : BlockTransformations) {
                if (!r.from().test(state) || !r.with().test(stack)) continue;
                if (!level.isClientSide()) {
                    switch (r.result()) {
                        case FromBlock.ToBlock b -> {
                            level.setBlockAndUpdate(pos, b.block().get().defaultBlockState());
                            b.after().run(level, pos, state, stack);
                        }
                        case FromBlock.ToItem i -> {
                            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                            Containers.dropItemStack(
                                    level, pos.getX(), pos.getY(), pos.getZ(),
                                    new ItemStack(i.item().get()));
                        }
                        case FromBlock.BlockInteraction a -> {
                                a.action().run(level, pos, state, stack);
                        }
                    }
                }
                return EventResult.interruptTrue();
            }
            return EventResult.pass();
        });
    }
}