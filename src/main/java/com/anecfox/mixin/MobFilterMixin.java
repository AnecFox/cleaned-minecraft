package com.anecfox.mixin;

import net.minecraft.world.entity.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.anecfox.BannedContent.BANNED_MOBS;

@Mixin(Mob.class)
public class MobFilterMixin {

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void purgeOnFirstTick(CallbackInfo ci) {
        var mob = (Mob) (Object) this;
        EntityType<?> mobType = mob.getType();

        if (!mob.level().isClientSide()) {
            if (BANNED_MOBS.contains(mobType)) {
                mob.discard();
                ci.cancel();
            }
        }
    }
}
