package com.anecfox.mixin;

import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.camel.Camel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.anecfox.GenderUtilities.TAG_FEMALE;
import static com.anecfox.GenderUtilities.TAG_MALE;

@Mixin(Camel.class)
public class CamelBreedMixin {

    @Inject(method = "canMate", at = @At("HEAD"), cancellable = true)
    private void checkAnimalTagCompatibility(Animal partner, CallbackInfoReturnable<Boolean> cir) {
        var thisCamel = (Camel) (Object) this;

        boolean isThisCamelMale = thisCamel.entityTags().contains(TAG_MALE);
        boolean isThisCamelFemale = thisCamel.entityTags().contains(TAG_FEMALE);

        boolean isPartnerMale = partner.entityTags().contains(TAG_MALE);
        boolean isPartnerFemale = partner.entityTags().contains(TAG_FEMALE);

        if ((isThisCamelMale && isPartnerMale) || (isThisCamelFemale && isPartnerFemale)) {
            cir.setReturnValue(false);
        }
    }
}
