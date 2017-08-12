package eu.dieytberlegion.Events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import eu.dieytberlegion.OneVsOne.OneVsOne;

public class ProjectilHit implements Listener{

	public ProjectilHit(OneVsOne plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public static void ProjectilHitEvent(ProjectileHitEvent e){
		
		if(e.getEntityType().equals(EntityType.ARROW)){
			e.getEntity().remove();
		}
		
		
	}

}
