package com.anecfox.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.camel.Camel;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

import static com.anecfox.GenderUtilities.TAG_FEMALE;

@Mixin(ServerLevel.class)
public class GlobalBabyVillagerOrCamelSpawnRelocatorMixin {

    @Inject(method = "addEntity", at = @At("HEAD"))
    private void relocateBabiesToMotherOnSpawn(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        ServerLevel level = (ServerLevel) (Object) this;

        if (entity instanceof AgeableMob baby && baby.isBaby()) {
            if (baby instanceof Villager || baby instanceof Camel) {
                AABB searchArea = baby.getBoundingBox().inflate(4.0d);

                if (baby instanceof Villager) {
                    List<Villager> parents = level.getEntitiesOfClass(Villager.class, searchArea);
                    for (Villager parent : parents) {
                        if (!parent.isBaby() && parent.entityTags().contains(TAG_FEMALE)) {
                            baby.setPos(parent.getX(), parent.getY(), parent.getZ());
                            baby.setYRot(parent.getYRot());
                            baby.setXRot(parent.getXRot());
                            break;
                        }
                    }
                } else if (baby instanceof Camel) {
                    List<Camel> parents = level.getEntitiesOfClass(Camel.class, searchArea);
                    for (Camel parent : parents) {
                        if (!parent.isBaby() && parent.entityTags().contains(TAG_FEMALE)) {
                            baby.setPos(parent.getX(), parent.getY(), parent.getZ());
                            baby.setYRot(parent.getYRot());
                            baby.setXRot(parent.getXRot());
                            break;
                        }
                    }
                }
            }
        }
    }
}
