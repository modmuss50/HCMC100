package com.hcmc100.mod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.advancement.criterion.CriterionProgress;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stat;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.world.GameRules;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import java.util.zip.GZIPOutputStream;

public class HCMC implements ClientModInitializer {

	static Properties properties;

	@Override
	public void onInitializeClient() {
		properties = new Properties(new File(MinecraftClient.getInstance().runDirectory, "hcmc.nbt"));
		new Commands(properties);

		ClientTickCallback.EVENT.register(client -> {
			if (client.world != null) {
				if (client.world.getTime() % (20 * 30) == 0) {
					if (getPlayer() != null) {
						//Save the data every 30 seconds
						savePlayer();
					}
				}
			}
		});
	}

	public static boolean isValidAdvancement(Advancement advancement) {
		return getPlayer() != null && advancement.getDisplay() != null && !advancement.getId().getPath().startsWith("recipes/");
	}

	//Called via mixin - TODO use fabric api once updated
	public static void playerLoggedIn() {
		//Forced to run on the main thread as login is called on the netty thread :D
		MinecraftClient.getInstance().execute(() -> {
			if (properties.get().getString("id").isEmpty()) {
				TranslatableText component = new TranslatableText("hcmc.login");
				component.getStyle().setColor(Formatting.LIGHT_PURPLE);
				component.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://api.hcmc100.com/twitch/login"));
				getPlayer().sendMessage(component);
			} else {
				String username = getPlayer().getGameProfile().getName();
				String uuid = getPlayer().getGameProfile().getId().toString();
				syncAdvancementData((id) -> HttpExecutor.post("update/" + id + "/login/" + username + "/" + uuid));
			}
		});
	}

	//Called via mixin - TODO use fabric api once updated
	public static void playerLoggedOut() {
		syncAdvancementData((id) -> HttpExecutor.post("update/" + id + "/logout"));
	}

	//Called via mixin
	public static void advancement(Advancement advancement) {
		if (isValidAdvancement(advancement)) {
			syncAdvancementData(null);
		}
	}

	//Called via mixin
	public static void playerDeath() {
		byte[] deathMessage = getPlayer().getDamageTracker().getDeathMessage().getString().getBytes(StandardCharsets.UTF_8);
		syncAdvancementData((id) -> HttpExecutor.post("update/" + id + "/death", deathMessage));
	}

	private static void savePlayer() {
		String id = properties.getID();

		if (!id.isEmpty()) {
			try {
				ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
				DataOutputStream out = new DataOutputStream(new GZIPOutputStream(byteOut));
				out.writeByte(1);
				out.writeShort(StatHandler.usefulStats.size());

				for (Stat<Identifier> stat : StatHandler.usefulStats) {
					out.writeUTF(stat.getValue().getPath());
					out.writeInt(getPlayer().getStatHandler().getStat(stat));
				}

				out.close();
				HttpExecutor.post("update/" + id + "/stats", byteOut.toByteArray());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void syncAdvancementData(Consumer<String> postAction) {
		ServerPlayerEntity player = getPlayer();
		String id = properties.getID();

		if (!id.isEmpty()) {
			try {
				ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
				try(DataOutputStream out = new DataOutputStream(new GZIPOutputStream(byteOut))){
					out.writeByte(1);
					out.writeBoolean(!player.server.getGameRules().get(GameRules.NATURAL_REGENERATION).get());

					long currentDate = new Date().getTime();

					List<Pair<Advancement, Date>> completed = player.server.getAdvancementManager().getAdvancements().stream()
						.filter(HCMC::isValidAdvancement)
						.map(advancement -> new Pair<>(advancement, getCompletionDate(player.getAdvancementManager().getProgress(advancement))))
						.filter(pair -> pair.getRight() != null)
						.collect(Collectors.toList());

					out.writeShort(completed.size());

					for (Pair<Advancement, Date> pair : completed) {
						Date date = pair.getRight();
						Advancement advancement = pair.getLeft();

						if (advancement.getId().getNamespace().equals("minecraft")) {
							out.writeUTF(advancement.getId().getPath());
						} else {
							out.writeUTF(advancement.getId().getNamespace() + "/" + advancement.getId().getPath());
						}
						out.writeLong(currentDate - date.getTime());
					}
				}

				HttpExecutor.post("update/" + id + "/advancements", byteOut.toByteArray(), postAction == null ? null : () -> postAction.accept(id));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

	}

	private static Date getCompletionDate(AdvancementProgress progress) {
		if (!progress.isDone()) {
			return null;
		}

		return StreamSupport.stream(progress.getObtainedCriteria().spliterator(), false)
			.map(progress::getCriterionProgress)
			.filter(Objects::nonNull)
			.filter(CriterionProgress::isObtained)
			.map(CriterionProgress::getObtainedDate)
			.sorted(Date::compareTo)
			.findAny()
			.orElse(null);
	}

	private static ServerPlayerEntity getPlayer() {
		if (MinecraftClient.getInstance().getServer() == null || MinecraftClient.getInstance().player == null) {
			return null;
		}
		return MinecraftClient.getInstance().getServer().getPlayerManager().getPlayer(MinecraftClient.getInstance().player.getUuid());
	}

}