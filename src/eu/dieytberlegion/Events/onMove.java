package eu.dieytberlegion.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.Vector;

import eu.dieytberlegion.OneVsOne.OneVsOne;

public class onMove implements Listener{

	public onMove(OneVsOne plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	

	@EventHandler
	public static void Playersneak(PlayerToggleSneakEvent e){
		/*ÜPlayer p = e.getPlayer();
		if(OneVsOne.onGame.containsKey(p.getUniqueId())){
		Vector v = p.getLocation().getDirection().multiply(1D).setY(0.3D);
		p.setVelocity(v);
	 }*/
	}
	
	@EventHandler
	public static void cEvent(PlayerMoveEvent e){
		Player p = e.getPlayer();
		if(OneVsOne.fresplayer.contains(p.getUniqueId())){
			if(e.getFrom().getX() != e.getTo().getX() || e.getFrom().getZ() != e.getTo().getZ()){
				e.setTo(e.getFrom());
			}
			
		}
		if(!OneVsOne.onGame.containsKey(p.getUniqueId())){
			p.setHealth(20);
		}
		p.setFoodLevel(20);
		
	}

	
}
