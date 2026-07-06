package com.anecfox.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.npc.villager.Villager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashSet;

import static com.anecfox.GenderUtilities.TAG_FEMALE;

@Mixin(Villager.class)
public class VillagerSpawnBabyToMatherMixin {

    @Inject(method = "getBreedOffspring(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/AgeableMob;)Lnet/minecraft/world/entity/AgeableMob;", at = @At("RETURN"))
    private void relocateBabyToMather(ServerLevel firstPartner, AgeableMob secondPartner, CallbackInfoReturnable<AgeableMob> cir) {
        var villager = (Villager) (Object) this;

        if (secondPartner instanceof Villager partnerVillager) {
            AgeableMob baby = cir.getReturnValue();
            if (baby != null) {
                Villager mother = villager.entityTags().contains(TAG_FEMALE) ?
                        villager : partnerVillager;

                baby.teleportTo(
                        (ServerLevel) baby.level(),
                        mother.getX(),
                        mother.getY(),
                        mother.getZ(),
                        new HashSet<>(),
                        mother.getYRot(),
                        mother.getXRot(),
                        false
                );
            }
        }
    }
}
