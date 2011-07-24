package net.D3GN.MiracleM4n.mRTD;


import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.inventory.ItemStack;

public class MBlockListener extends BlockListener {
	mRTD plugin;
	
	public MBlockListener(mRTD plugin) {
		this.plugin = plugin;
	}
	
	public void onBlockDamage(BlockDamageEvent event) {
		Player player = event.getPlayer();
		if (plugin.hasRTD.get(player) == null) return;
		if (plugin.hasRTD.get(player) == false) return;
		if (plugin.RTD.get(player) == null) return;
		if (plugin.RTD.get(player) == 4) {
			event.setInstaBreak(true);
		}
	}
	
	public void onBlockBreak(BlockBreakEvent event) {
		Block block = event.getBlock();
		Material mat = block.getType();
		Player player = event.getPlayer();
		if (plugin.hasRTD.get(player) == null) return;
		if (plugin.hasRTD.get(player) == false) return;
		if (plugin.RTD.get(player) == null) return;
		if (plugin.RTD.get(player) == 1) {
			event.setCancelled(true);
			block.setType(Material.AIR);
			block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(mat, 2));
		}
	}
}
