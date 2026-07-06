package com.anecfox.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.golem.IronGolem;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.monster.spider.CaveSpider;
import net.minecraft.world.entity.monster.spider.Spider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(IronGolem.class)
public class IronGolemSpydersAndSilverfishIgnoreMixin {

    @Inject(method = "canAttack", at = @At("HEAD"), cancellable = true)
    private void onCanAttack(LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
        if (target instanceof Spider || target instanceof CaveSpider || target instanceof Silverfish) {
            cir.setReturnValue(false);
        }
    }
}
