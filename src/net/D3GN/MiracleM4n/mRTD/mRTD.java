package net.D3GN.MiracleM4n.mRTD;

import java.io.File;
import java.util.HashMap;


import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.command.ColouredConsoleSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

public class mRTD extends JavaPlugin {

	public final RTDListener rListener = new RTDListener(this);
	private MPlayerListener pListener = new MPlayerListener(this);
	private MEntityListener eListener = new MEntityListener(this);
	private MBlockListener bListener = new MBlockListener(this);
	private MCommandSender cSender = new MCommandSender(this);
	
	private PluginManager pm;
	ColouredConsoleSender console = null;
  	//Boolean contrib = false;
  	Boolean hasChanged = false;
	Configuration config;
	
	Integer minValue = 20;
	Integer maxValue = 60;
	
	Integer rtdTimeout = 120;

	HashMap<Player, Boolean> isRTD = new HashMap<Player, Boolean>();
	HashMap<Player, Boolean> hasRTD = new HashMap<Player, Boolean>();
	HashMap<Player, Integer> RTD = new HashMap<Player, Integer>();
	//HashMap<Player, String> RTDReason = new HashMap<Player, String>();
	HashMap<Player, Integer> timeRTD = new HashMap<Player, Integer>();
	HashMap<Player, Boolean> timedRTD = new HashMap<Player, Boolean>();
	
	public void onEnable() {
		pm = getServer().getPluginManager();
		config = getConfiguration();
		console = new ColouredConsoleSender((CraftServer)getServer());
		PluginDescriptionFile pdfFile = getDescription();
		
		//getContrib();
		
		if (!(new File(getDataFolder(), "config.yml")).exists()) {
			defaultConfig();
			checkConfig();
			loadConfig();
		} else {
			checkConfig();
			loadConfig();
		}
		
		pm.registerEvent(Event.Type.PLAYER_MOVE, pListener, Priority.High, this);
		pm.registerEvent(Event.Type.PLAYER_JOIN, pListener, Priority.High, this);
		pm.registerEvent(Event.Type.ENTITY_DAMAGE, eListener, Priority.High, this);
		pm.registerEvent(Event.Type.BLOCK_BREAK, bListener, Priority.High, this);
		pm.registerEvent(Event.Type.BLOCK_DAMAGE, bListener, Priority.High, this);
		getCommand("mrtd").setExecutor(cSender);
		
		/*
		if (contrib) {
			MCustomListener cListener = new MCustomListener(this);
			pm.registerEvent(Event.Type.CUSTOM_EVENT, cListener, Event.Priority.High, this);
		}
		*/
		
		setupPermissions();
		
		for(Player players : getServer().getOnlinePlayers()) {
			hasRTD.put(players, false);
			isRTD.put(players, false);
		}
		
		console.sendMessage("[" + (pdfFile.getName()) + "]" + " version " + 
				pdfFile.getVersion() + " is enabled!");
	}
	
	public void onDisable() {
		PluginDescriptionFile pdfFile = getDescription();
		
		console.sendMessage("[" + (pdfFile.getName()) + "]" + " version " + 
				pdfFile.getVersion() + " is disabled!");
	}
	
	public void loadConfig() {
		config.load();
		rtdTimeout = config.getInt("RTD-Timeout", rtdTimeout);
		minValue = config.getInt("RTD-MinValue", minValue);
		maxValue = config.getInt("RTD-MaxValue", maxValue);
	}

	public void defaultConfig() {
		config.setHeader("# RollTheDice configuration file");
		config.setProperty("RTD-Timeout", rtdTimeout);
		config.setProperty("RTD-MinValue", minValue);
		config.setProperty("RTD-MaxValue", maxValue);
		config.setProperty("auto-Changed", "1");
		config.save();
	}
	
	public void checkConfig() {
		PluginDescriptionFile pdfFile = getDescription();
		Configuration config = new Configuration(new File(getDataFolder(), "config.yml"));
		config.load();
		if (config.getProperty("auto-Changed") == "1") {
			if (config.getProperty("RTD-Timeout") == null) {
				config.setProperty("RTD-Timeout", rtdTimeout);
				hasChanged = true;
			}
			if (config.getProperty("RTD-MinValue") == null) {
				config.setProperty("RTD-MinValue", minValue);
				hasChanged = true;
			}
			if (config.getProperty("RTD-MaxValue") == null) {
				config.setProperty("RTD-MaxValue", maxValue);
				hasChanged = true;
			}
		}
		if (config.getProperty("auto-Changed") == "2") {
			hasChanged = true;
		}
		if (hasChanged) {
			defaultConfig();
			System.out.println("[" + pdfFile.getName() + "]" + " config.yml has been updated.");
		}
	}

	private void setupPermissions() {
		PluginDescriptionFile pdfFile = getDescription();
		Plugin bukkitPermTest = this.getServer().getPluginManager().getPlugin("PermissionsBukkit");
		if (bukkitPermTest != null) {
			console.sendMessage("[" + (pdfFile.getName()) + "]" + " PermissionsBukkit " + (bukkitPermTest.getDescription().getVersion()) + " found hooking in.");
		} else {
			console.sendMessage("[" + (pdfFile.getName()) + "]" + " Permissions plugin not found, Defaulting to Bukkit Methods.");
		}
	}
	
	/*
	public void getContrib(){
		PluginDescriptionFile pdfFile = getDescription();
		Plugin contibTest = this.getServer().getPluginManager().getPlugin("BukkitContrib");
		if(contibTest != null){
			this.contrib = true;
			console.sendMessage("[" + (pdfFile.getName()) + "]" + " BukkitContrib found now using.");
		}
		else {
			this.contrib = false;
			console.sendMessage("[" + (pdfFile.getName()) + "]" + " BukkitContrib not found not using.");
		}
	}
	*/
}
