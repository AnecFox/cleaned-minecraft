package com.anecfox.mixin;

import net.minecraft.world.entity.animal.Animal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.anecfox.GenderUtilities.TAG_FEMALE;
import static com.anecfox.GenderUtilities.TAG_MALE;

@Mixin(Animal.class)
public class AnimalBreedMixin {

    @Inject(method = "canMate", at = @At("HEAD"), cancellable = true)
    private void checkAnimalTagCompatibility(Animal partner, CallbackInfoReturnable<Boolean> cir) {
        Animal thisAnimal = (Animal) (Object) this;

        boolean isThisAnimalMale = thisAnimal.entityTags().contains(TAG_MALE);
        boolean isThisAnimalFemale = thisAnimal.entityTags().contains(TAG_FEMALE);

        boolean isPartnerMale = partner.entityTags().contains(TAG_MALE);
        boolean isPartnerFemale = partner.entityTags().contains(TAG_FEMALE);

        if ((isThisAnimalMale && isPartnerMale) || (isThisAnimalFemale && isPartnerFemale)) {
            cir.setReturnValue(false);
        }
    }
}
