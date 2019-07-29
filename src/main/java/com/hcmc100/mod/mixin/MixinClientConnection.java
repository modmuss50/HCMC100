package com.hcmc100.mod.mixin;

import com.hcmc100.mod.HCMC;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.listener.ServerPlayPacketListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Deprecated //TODO replace with the fabric api once merged! (very soon!)
@Mixin(ClientConnection.class)
public abstract class MixinClientConnection {

	@Shadow
	private PacketListener packetListener;

	@Inject(method = "channelInactive", remap = false, at = @At("RETURN"))
	private void onChannelInactive(ChannelHandlerContext context, CallbackInfo ci) {
		if (packetListener instanceof ServerPlayPacketListener) {
			HCMC.playerLoggedOut();
		}
	}
}