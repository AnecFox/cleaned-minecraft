package com.anecfox.mixin;

import net.minecraft.world.entity.monster.Silverfish;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Silverfish.class)
public class SilverfishPeacefulMixin {

    @Inject(method = "registerGoals", at = @At("RETURN"))
    private void onRegisterGoals(CallbackInfo ci) {
        var silverfishMob = (Silverfish) (Object) this;
        ((MobAccessor) silverfishMob).getTargetSelector().removeAllGoals(_ -> true);
    }
}
