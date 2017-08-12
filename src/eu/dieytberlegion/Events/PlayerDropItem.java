package eu.dieytberlegion.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.plugin.Plugin;

import eu.dieytberlegion.OneVsOne.OneVsOne;

public class PlayerDropItem implements Listener{
	
	public static Plugin plugin;
	

	public PlayerDropItem(OneVsOne plugin){
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	
	@EventHandler
	public void PlayerDrop(PlayerDropItemEvent e){
		Player p = e.getPlayer();
		
		if(OneVsOne.Builder.contains(p)){
			e.setCancelled(false);
		}else{
			e.setCancelled(true);
		}
	}

}
