package com.anecfox.mixin;

import net.minecraft.world.entity.animal.polarbear.PolarBear;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PolarBear.class)
public class PolarBearPeacefulMixin {

    @Inject(method = "registerGoals", at = @At("RETURN"))
    private void onRegisterGoals(CallbackInfo ci) {
        var bear = (PolarBear) (Object) this;
        ((MobAccessor) bear).getTargetSelector().removeAllGoals(_ -> true);
    }
}
