package com.anecfox.mixin;

import net.minecraft.world.entity.animal.chicken.Chicken;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.anecfox.GenderUtilities.TAG_MALE;

@Mixin(Chicken.class)
public class ChickenEggBlockingMixin {

    @Shadow
    public int eggTime;

    @Inject(method = "aiStep", at = @At("HEAD"))
    private void blockEggLayingForRoosters(CallbackInfo ci) {
        Chicken chicken = (Chicken) (Object) this;

        if (!chicken.level().isClientSide()) {
            if (chicken.entityTags().contains(TAG_MALE)) {
                if (this.eggTime <= 100) {
                    this.eggTime = chicken.getRandom().nextInt(6000) + 6000;
                }
            }
        }
    }
}
