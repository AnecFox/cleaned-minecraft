package com.anecfox.mixin;

import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Piglin.class)
public class PiglinEquipmentMixin {

    @Inject(method = "populateDefaultEquipmentSlots", at = @At("TAIL"))
    private void removeWeaponOnSpawn(RandomSource random, DifficultyInstance difficulty, CallbackInfo ci) {
        var piglin = (Piglin) (Object) this;
        piglin.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
    }
}
