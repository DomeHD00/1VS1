package eu.dieytberlegion.Events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;

import eu.dieytberlegion.OneVsOne.OneVsOne;

public class onBlock implements Listener{

	public onBlock(OneVsOne plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public static void BlockBreak(BlockBreakEvent e){
		if(OneVsOne.Builder.contains(e.getPlayer())){
			e.setCancelled(false);
		}else{
			e.setCancelled(true);
		}
		
		
	}

	@EventHandler
	public static void BlockPlace(BlockPlaceEvent e){
		if(OneVsOne.Builder.contains(e.getPlayer())){
			e.setCancelled(false);
		}else{
			e.setCancelled(true);
		}
		
		
	}
	
	@EventHandler
	public void on(BlockDamageEvent e) {
        if(e.getBlock().getType().equals(Material.WHEAT)){
            e.setCancelled(true);
        }
    }
	
	
}
