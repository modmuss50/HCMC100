package com.hcmc100.mod;

import net.minecraft.stat.Stat;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;

import java.util.HashSet;

public class StatHandler {

	public static final HashSet<Stat<Identifier>> usefulStats = new HashSet<>();

	static {
		usefulStats.add(Stats.CUSTOM.getOrCreateStat(Stats.PLAY_ONE_MINUTE));
		usefulStats.add(Stats.CUSTOM.getOrCreateStat(Stats.TIME_SINCE_DEATH));
		usefulStats.add(Stats.CUSTOM.getOrCreateStat(Stats.TIME_SINCE_REST));
		usefulStats.add(Stats.CUSTOM.getOrCreateStat(Stats.WALK_ONE_CM));
		usefulStats.add(Stats.CUSTOM.getOrCreateStat(Stats.CROUCH_ONE_CM));
		usefulStats.add(Stats.CUSTOM.getOrCreateStat(Stats.SPRINT_ONE_CM));
		usefulStats.add(Stats.CUSTOM.getOrCreateStat(Stats.WALK_ON_WATER_ONE_CM));
		usefulStats.add(Stats.CUSTOM.getOrCreateStat(Stats.FALL_ONE_CM));
		usefulStats.add(Stats.CUSTOM.getOrCreateStat(Stats.CLIMB_ONE_CM));
		usefulStats.add(Stats.CUSTOM.getOrCreateStat(Stats.FLY_ONE_CM));
		usefulStats.add(Stats.CUSTOM.getOrCreateStat(Stats.WALK_UNDER_WATER_ONE_CM));
		usefulStats.add(Stats.CUSTOM.getOrCreateStat(Stats.MINECART_ONE_CM));
		usefulStats.add(Stats.CUSTOM.getOrCreateStat(Stats.BOAT_ONE_CM));
		usefulStats.add(Stats.CUSTOM.getOrCreateStat(Stats.PIG_ONE_CM));
		usefulStats.add(Stats.CUSTOM.getOrCreateStat(Stats.HORSE_ONE_CM));
		usefulStats.add(Stats.CUSTOM.getOrCreateStat(Stats.AVIATE_ONE_CM));
		usefulStats.add(Stats.CUSTOM.getOrCreateStat(Stats.SWIM_ONE_CM));
		usefulStats.add(Stats.CUSTOM.getOrCreateStat(Stats.JUMP));
		usefulStats.add(Stats.CUSTOM.getOrCreateStat(Stats.DAMAGE_DEALT));
		usefulStats.add(Stats.CUSTOM.getOrCreateStat(Stats.DAMAGE_DEALT_ABSORBED));
		usefulStats.add(Stats.CUSTOM.getOrCreateStat(Stats.DAMAGE_DEALT_RESISTED));
		usefulStats.add(Stats.CUSTOM.getOrCreateStat(Stats.DAMAGE_TAKEN));
		usefulStats.add(Stats.CUSTOM.getOrCreateStat(Stats.DAMAGE_BLOCKED_BY_SHIELD));
		usefulStats.add(Stats.CUSTOM.getOrCreateStat(Stats.DAMAGE_ABSORBED));
		usefulStats.add(Stats.CUSTOM.getOrCreateStat(Stats.DAMAGE_RESISTED));
		usefulStats.add(Stats.CUSTOM.getOrCreateStat(Stats.DEATHS));
		usefulStats.add(Stats.CUSTOM.getOrCreateStat(Stats.MOB_KILLS));
		usefulStats.add(Stats.CUSTOM.getOrCreateStat(Stats.ANIMALS_BRED));
		usefulStats.add(Stats.CUSTOM.getOrCreateStat(Stats.PLAYER_KILLS));
		usefulStats.add(Stats.CUSTOM.getOrCreateStat(Stats.FISH_CAUGHT));
		usefulStats.add(Stats.CUSTOM.getOrCreateStat(Stats.TALKED_TO_VILLAGER));
		usefulStats.add(Stats.CUSTOM.getOrCreateStat(Stats.TRADED_WITH_VILLAGER));
		usefulStats.add(Stats.CUSTOM.getOrCreateStat(Stats.ENCHANT_ITEM));
		usefulStats.add(Stats.CUSTOM.getOrCreateStat(Stats.PLAY_RECORD));
		usefulStats.add(Stats.CUSTOM.getOrCreateStat(Stats.SLEEP_IN_BED));
		usefulStats.add(Stats.CUSTOM.getOrCreateStat(Stats.RAID_TRIGGER));
		usefulStats.add(Stats.CUSTOM.getOrCreateStat(Stats.RAID_WIN));
	}



}
