package com.anecfox.mixin;

import net.minecraft.world.entity.AgeableMob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.anecfox.GenderUtilities.getRandomGenderForMob;
import static com.anecfox.GenderUtilities.containsGenderTag;

@Mixin(AgeableMob.class)
public class AgeableMobTickMixin {

    @Unique
    private boolean hasCheckedTags = false;

    @Inject(method = "aiStep", at = @At("HEAD"))
    private void checkAndAssignTagsOnFirstTick(CallbackInfo ci) {
        if (this.hasCheckedTags) {
            return;
        }

        var ageableMob = (AgeableMob) (Object) this;

        if (!ageableMob.level().isClientSide() && !containsGenderTag(ageableMob.entityTags().toString())) {
            ageableMob.addTag(getRandomGenderForMob());
            hasCheckedTags = true;
        }
    }
}
