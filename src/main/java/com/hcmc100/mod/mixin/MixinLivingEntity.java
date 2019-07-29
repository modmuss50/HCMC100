package com.hcmc100.mod.mixin;

import com.hcmc100.mod.HCMC;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class MixinLivingEntity {

	@Inject(method = "onDeath", at = @At("HEAD"))
	private void onDeath(DamageSource damageSource, CallbackInfo info) {
		if ((Object) this instanceof ServerPlayerEntity) {
			HCMC.playerDeath();
		}

	}
}
