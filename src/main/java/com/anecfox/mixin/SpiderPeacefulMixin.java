package com.anecfox.mixin;

import net.minecraft.world.entity.monster.spider.Spider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Spider.class)
public class SpiderPeacefulMixin {

    @Inject(method = "registerGoals", at = @At("RETURN"))
    private void onRegisterGoals(CallbackInfo ci) {
        var spiderMob = (Spider) (Object) this;
        ((MobAccessor) spiderMob).getTargetSelector().removeAllGoals(_ -> true);
    }
}
