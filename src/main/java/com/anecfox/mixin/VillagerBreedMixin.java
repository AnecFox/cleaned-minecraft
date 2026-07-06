package com.anecfox.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.VillagerMakeLove;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.npc.villager.Villager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

import static com.anecfox.GenderUtilities.TAG_FEMALE;
import static com.anecfox.GenderUtilities.TAG_MALE;

@Mixin(VillagerMakeLove.class)
public class VillagerBreedMixin {

    @Inject(method = "checkExtraStartConditions*", at = @At("HEAD"), cancellable = true)
    private void preventHeartsForSameTags(ServerLevel level, Villager body, CallbackInfoReturnable<Boolean> cir) {
        Optional<Villager> partnerOptional = body.getBrain().getMemory(MemoryModuleType.BREED_TARGET)
                .flatMap(livingEntity -> livingEntity instanceof Villager ?
                        Optional.of((Villager) livingEntity) : Optional.empty());

        if (partnerOptional.isPresent()) {
            Villager partner = partnerOptional.get();

            boolean isOwnerMale = body.entityTags().contains(TAG_MALE);
            boolean isOwnerFemale = body.entityTags().contains(TAG_FEMALE);

            boolean isPartnerMale = partner.entityTags().contains(TAG_MALE);
            boolean isPartnerFemale = partner.entityTags().contains(TAG_FEMALE);

            if ((isOwnerMale && isPartnerMale) || (isOwnerFemale && isPartnerFemale)) {
                cir.setReturnValue(false);
            }
        }
    }
}
