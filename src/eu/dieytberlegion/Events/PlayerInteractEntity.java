package eu.dieytberlegion.Events;

import javax.swing.plaf.BorderUIResource.MatteBorderUIResource;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import eu.dieytberlegion.OneVsOne.OneVsOne;
import eu.dieytberlegion.status.gameStart;

public class PlayerInteractEntity implements Listener{

	public PlayerInteractEntity(OneVsOne plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public static void PlayerInteract(PlayerInteractEntityEvent e){
		Player p = e.getPlayer();
		Player taget = (Player) e.getRightClicked();
		
		//---Player Erausfordung annehmen !


		
			
		
	

	}
}
