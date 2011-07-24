package net.D3GN.MiracleM4n.mRTD;


import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MPlayerListener extends PlayerListener {
	mRTD plugin;
	
	public MPlayerListener(mRTD plugin) {
		this.plugin = plugin;
	}
	
	public void onPlayerMove(PlayerMoveEvent event) {		
	}
	
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		plugin.hasRTD.put(player, false);
		plugin.isRTD.put(player, false);
	}
}
