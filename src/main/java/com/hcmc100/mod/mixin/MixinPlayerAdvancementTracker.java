package com.hcmc100.mod.mixin;

import com.hcmc100.mod.HCMC;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.PlayerAdvancementTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerAdvancementTracker.class)
public class MixinPlayerAdvancementTracker {

	@Inject(method = "grantCriterion", at = @At(value = "INVOKE", target = "net/minecraft/server/PlayerManager.sendToAll(Lnet/minecraft/text/Text;)V"))
	public void grantCriterion(Advancement advancement, String criterion, CallbackInfoReturnable<Boolean> infoReturnable) {
		HCMC.advancement(advancement);
	}

}
