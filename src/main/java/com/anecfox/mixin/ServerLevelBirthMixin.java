package com.anecfox.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Animal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.anecfox.GenderUtilities.containsGenderTag;
import static com.anecfox.GenderUtilities.getRandomGenderForMob;

@Mixin(ServerLevel.class)
public class ServerLevelBirthMixin {

    @Inject(method = "tryAddFreshEntityWithPassengers", at = @At("HEAD"))
    private void assignTagToNewbornAnimal(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof Animal animal) {
            if (animal.isBaby() && !containsGenderTag(animal.entityTags().toString())) {
                animal.addTag(getRandomGenderForMob());
            }
        }
    }
}
