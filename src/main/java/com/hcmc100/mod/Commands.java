package com.hcmc100.mod;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.GameRules;

public class Commands {

	Properties properties;

	public Commands(Properties properties) {
		this.properties = properties;
		initialize();
	}

	public void initialize() {
		CommandRegistry.INSTANCE.register(false, dispatcher -> {
			dispatcher.register(CommandManager.literal("hcmc_login").then(CommandManager.argument("id", StringArgumentType.word()).executes(ctx -> loginCommand(ctx.getSource().getPlayer(), StringArgumentType.getString(ctx, "id")))));
			dispatcher.register(CommandManager.literal("hcmc_logout").executes(ctx -> logoutCommand()));
			dispatcher.register(CommandManager.literal("hcmc_uhc").executes(ctx -> uhcCommand(ctx.getSource().getPlayer())));
		});
	}

	private int logoutCommand() {
		String id = properties.getID();

		if (!id.isEmpty()) {
			properties.get().remove("id");
			properties.save();
			HttpExecutor.post("twitch/logout/" + id);
		}

		return 0;
	}

	private int loginCommand(ServerPlayerEntity player, String id) {
		TranslatableText component = new TranslatableText("hcmc.logged_in");
		component.getStyle().setColor(Formatting.DARK_GREEN);
		player.sendMessage(component);
		properties.get().putString("id", id);
		properties.save();
		HCMC.syncAdvancementData((id1) -> HttpExecutor.post("update/" + id1 + "/login"));
		return 0;
	}

	private int uhcCommand(ServerPlayerEntity player) {
		player.server.getGameRules().get(GameRules.NATURAL_REGENERATION).set(!player.server.getGameRules().get(GameRules.NATURAL_REGENERATION).get(), player.server);

		if (player.server.getGameRules().get(GameRules.NATURAL_REGENERATION).get()) {
			player.sendMessage(new TranslatableText("hcmc.uch_disabled"));
		} else {
			player.sendMessage(new TranslatableText("hcmc.uch_enabled"));
		}

		HCMC.syncAdvancementData(null);
		return 0;
	}
}
