package com.anecfox.mixin;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;

import static com.anecfox.BannedContent.BANNED_ITEMS;

@Mixin(CreativeModeTab.class)
public class CreativeMenuTransformerMixin {

    @Inject(method = "getDisplayItems", at = @At("RETURN"), cancellable = true)
    private void onGetDisplayItems(CallbackInfoReturnable<Collection<ItemStack>> cir) {
        Collection<ItemStack> originalItems = cir.getReturnValue();
        List<ItemStack> modifiedItems = new ArrayList<>(originalItems);

        modifiedItems.removeIf(stack -> BANNED_ITEMS.contains(stack.getItem()) || stack.is(Items.POTION));
        cir.setReturnValue(modifiedItems);
    }
}
