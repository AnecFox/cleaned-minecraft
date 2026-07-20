package com.anecfox.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

@Mixin(Mob.class)
public class MobAttackMixin {

    @Unique
    private static final Set<EntityType<?>> PEACEFUL_MOBS = Set.of(
            EntityTypes.SPIDER,
            EntityTypes.CAVE_SPIDER,
            EntityTypes.SILVERFISH,
            EntityTypes.POLAR_BEAR,
            EntityTypes.PIGLIN,
            EntityTypes.PIGLIN_BRUTE,
            EntityTypes.HOGLIN,
            EntityTypes.IRON_GOLEM,
            EntityTypes.SNOW_GOLEM
    );

    @Inject(method = "canAttack", at = @At("HEAD"), cancellable = true)
    private void onCanAttack(LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
        var mob = (Mob) (Object) this;

        if (PEACEFUL_MOBS.contains(mob.getType()) &&
                (PEACEFUL_MOBS.contains(target.getType()) ||
                        target instanceof Player)) {
            cir.setReturnValue(false);
        }
    }
}
