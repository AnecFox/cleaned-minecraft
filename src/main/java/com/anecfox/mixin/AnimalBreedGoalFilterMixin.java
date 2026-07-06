package com.anecfox.mixin;

import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.animal.Animal;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.anecfox.GenderUtilities.TAG_MALE;

@Mixin(BreedGoal.class)
public class AnimalBreedGoalFilterMixin {

    @Shadow
    @Final
    protected Animal animal;

    @Inject(method = "canUse", at = @At("HEAD"), cancellable = true)
    private void blockMaleTurtleInitiative(CallbackInfoReturnable<Boolean> cir) {
        if (this.animal instanceof AgeableMob mob) {
            if (mob.entityTags().contains(TAG_MALE)) {
                cir.setReturnValue(false);
            }
        }
    }
}
