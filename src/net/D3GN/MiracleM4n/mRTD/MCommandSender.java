package net.D3GN.MiracleM4n.mRTD;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

public class MCommandSender implements CommandExecutor, Runnable  {
	mRTD plugin;
	
	public MCommandSender(mRTD plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand (CommandSender sender, Command command, String label, String[] args) {
		PluginDescriptionFile pdfFile = plugin.getDescription();
		final String plugInfo = "[" + (pdfFile.getName()) + "] ";
		String commandName = command.getName();
		if (commandName.equalsIgnoreCase("mrtd")) {
			if (sender instanceof Player) {
				final Player player = (Player) sender;
				Runnable isRTDRunnable = new Runnable() { 
					public void run() {
						plugin.isRTD.put(player, false);
						player.sendMessage(plugInfo + "You may now RTD again.");
					}
				};
				Runnable hasRTDRunnable = new Runnable() { 
					public void run() {
						plugin.hasRTD.put(player, false);
						player.sendMessage(plugInfo + "Your RTD has warn off.");
					}
				};
				if (args.length == 0) {
					if (plugin.isRTD.get(player) == false) {
						plugin.rListener.getRTD(player);
						if (player != null) {
							plugin.isRTD.put(player, true);
							plugin.hasRTD.put(player, true);
							plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, isRTDRunnable, (plugin.rtdTimeout)*20);
							if(plugin.timedRTD.get(player)) {
								plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, hasRTDRunnable, (plugin.timeRTD.get(player))*20);
							}
						}
						return true;
					} else if (plugin.isRTD.get(player).equals(true)) {
						player.sendMessage(plugInfo + "Your cannot RTD again yet.");
						return true;
					} else {
						return false;
					}
				} else if (args.length == 1) {
					if(args[0].equalsIgnoreCase("help")) {
						return false;
					} else if (args[0].equalsIgnoreCase("reload")) {
						if (player.hasPermission("mrtd.reload")) {
							plugin.checkConfig();
							plugin.loadConfig();
							player.sendMessage(plugInfo + "Config reloaded.");
						} else {
							player.sendMessage(plugInfo + "You don't have permission to reload.");
						}
					}
				}
			} else if (args[0].equalsIgnoreCase("reload")) {
				plugin.checkConfig();
				plugin.loadConfig();
				plugin.console.sendMessage(plugInfo + "Config reloaded.");
			} else {
				plugin.console.sendMessage(plugInfo + "You can't RTD from console.");
				return true;
			} 
		}
		return false;
	}

	public void run() {
	}
}
