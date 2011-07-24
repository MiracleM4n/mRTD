package net.D3GN.MiracleM4n.mRTD;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;

public class RTDListener {
	
	mRTD plugin;
	
	public RTDListener(mRTD plugin) {
		this.plugin = plugin;
	}
	
	public Integer getTimeAmount() {
		Random random = new Random();
		Integer minValue = plugin.minValue;
		Integer maxValue = plugin.maxValue;
		Integer rand = random.nextInt(maxValue - minValue + 1) + minValue;
		return rand;
	}
	
	public Integer getHealthAmount() {
		Random random = new Random();
		Integer minValue = 50;
		Integer maxValue = 200;
		Integer rand = random.nextInt(maxValue - minValue + 1) + minValue;
		return rand;
	}
	
	public Integer getRoll() {
		Random random = new Random();
		Integer minValue = 2;
		Integer maxValue = 12;
		Integer rand = random.nextInt(maxValue - minValue + 1) + minValue;
		return rand;
	}
	
	public Integer getRTD(Player player) {
		Random random = new Random();
		Integer minValue = 1;
		Integer maxValue = 13;
		Integer rand = random.nextInt(maxValue - minValue + 1) + minValue;
		setRTDType(rand, player);
		getTimeAmount();
		return 0;
	}
	
	private String setRTDType(Integer rand, Player player) {
		PluginDescriptionFile pdfFile = plugin.getDescription();
		String plugInfo = "[" + (pdfFile.getName()) + "] ";
		World world = player.getWorld();
		if (rand == 1) {
			plugin.timedRTD.put(player, true);
			plugin.timeRTD.put(player, getTimeAmount());
			player.sendMessage(plugInfo + "You Rolled a " + getRoll() + " and got Double-Drop for " + plugin.timeRTD.get(player) + " seconds.");
			plugin.RTD.put(player, rand);
		} else if (rand == 2) {
			plugin.timedRTD.put(player, true);
			plugin.timeRTD.put(player, getTimeAmount());
			player.sendMessage(plugInfo + "You Rolled a " + getRoll() + " and got No Damage for " + plugin.timeRTD.get(player) + " seconds.");
			plugin.RTD.put(player, rand);
		} else if (rand == 3) {
			plugin.timedRTD.put(player, true);
			plugin.timeRTD.put(player, getTimeAmount());
			player.sendMessage(plugInfo + "You Rolled a " + getRoll() + " and got No Fall Damage for " + plugin.timeRTD.get(player) + " seconds.");
			plugin.RTD.put(player, rand);
		} else if (rand == 4) {
			plugin.timedRTD.put(player, true);
			plugin.timeRTD.put(player, getTimeAmount());
			player.sendMessage(plugInfo + "You Rolled a " + getRoll() + " and got InstaBreak for " + plugin.timeRTD.get(player) + " seconds.");
			plugin.RTD.put(player, rand);
		} else if (rand == 5) {
			plugin.timeRTD.put(player, getTimeAmount());
			plugin.timedRTD.put(player, true);
			player.sendMessage(plugInfo + "You Rolled a " + getRoll() + " and got set on fire for " + plugin.timeRTD.get(player) + " seconds.");
			player.setFireTicks(20*(plugin.timeRTD.get(player)));
		} else if (rand == 6) {
			plugin.timedRTD.put(player, false);
			player.sendMessage(plugInfo + "You Rolled a " + getRoll() + " and got Slayed.");
			player.setHealth(0);
		} else if (rand == 7) {
			world.dropItemNaturally(player.getLocation(), new ItemStack(Material.DIAMOND, 1));
			player.sendMessage(plugInfo + "You Rolled a " + getRoll() + " and got a Diamond.");
		} else if (rand == 8) {
			plugin.timedRTD.put(player, false);
			player.sendMessage(plugInfo + "You Rolled a " + getRoll() + " and exploded.");
			world.createExplosion(player.getLocation(), 2F, false);	
		} else if (rand == 9) {
			plugin.timedRTD.put(player, false);
			player.sendMessage(plugInfo + "You Rolled a " + getRoll() + " and got struck by lightning.");
			world.strikeLightning(player.getLocation());
		} else if (rand == 10) {
			plugin.timedRTD.put(player, false);
			player.sendMessage(plugInfo + "You Rolled a " + getRoll() + " and have been....Dropped.");
			player.teleport(player.getLocation().add(0, 50, 0));
		} else if (rand == 11) {
			plugin.timedRTD.put(player, false);
			player.setHealth(player.getHealth() + 5);
			player.sendMessage(plugInfo + "You Rolled a " + getRoll() + " and have Regained 5 health.");
		} else if (rand == 12) {
			plugin.timedRTD.put(player, false);
			player.setHealth(200);
			player.sendMessage(plugInfo + "You Rolled a " + getRoll() + " and have been super healed.");
			player.sendMessage(plugInfo + "You're health is now " + player.getHealth() + ".");
		} else if (rand == 13) {
			plugin.timedRTD.put(player, false);
			world.spawnCreature(player.getLocation(), CreatureType.CREEPER);
			for (Entity entities : player.getNearbyEntities(2, 2, 2)) {
				if (!(entities instanceof Creeper)) continue;
				entities.setPassenger(player);
			}
			player.sendMessage(plugInfo + "You Rolled a " + getRoll() + " and Ssssss...");
		}
		return "";
	}

}
