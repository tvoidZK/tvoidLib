package net.tvoid.lib.mixin;

import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractFurnaceBlockEntity.class)
public interface FurnaceAccessor {
    @Accessor("litTimeRemaining") int tvoid$getLitTimeRemaining();
    @Accessor("litTimeRemaining") void tvoid$setLitTimeRemaining(int value);

    @Accessor("litTotalTime") int tvoid$getLitTotalTime();
    @Accessor("litTotalTime") void tvoid$setLitTotalTime(int value);
}