package net.D3GN.MiracleM4n.mRTD;


import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityListener;

public class MEntityListener extends EntityListener {
	mRTD plugin;
	
	public MEntityListener(mRTD plugin) {
		this.plugin = plugin;
	}
	
	public void onEntityDamage(EntityDamageEvent event) {
		DamageCause type = event.getCause();
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if (plugin.hasRTD.get(player) == null) return;
			if (plugin.hasRTD.get(player) == false) return;
			if (plugin.RTD.get(player) == null) return;
			if (plugin.RTD.get(player) == 2) {
				event.setCancelled(true);
			} else if(type == DamageCause.FALL) {
				if (plugin.RTD.get(player) == 3) {
					event.setCancelled(true);
				}
			}
		}
		return;
	}
}
