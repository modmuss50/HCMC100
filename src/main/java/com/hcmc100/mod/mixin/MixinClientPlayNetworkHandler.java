package com.hcmc100.mod.mixin;

import com.hcmc100.mod.HCMC;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.packet.GameJoinS2CPacket;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.listener.ClientPlayPacketListener;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Deprecated //TODO replace with the fabric api once merged! (very soon!)
@Mixin(ClientPlayNetworkHandler.class)
public abstract class MixinClientPlayNetworkHandler implements ClientPlayPacketListener {

	@Shadow
	@Final
	private ClientConnection connection;

	@Inject(method = "onGameJoin", at = @At("RETURN"))
	private void onJoin(GameJoinS2CPacket gameJoinS2CPacket_1, CallbackInfo ci) {
		HCMC.playerLoggedIn();
	}
}