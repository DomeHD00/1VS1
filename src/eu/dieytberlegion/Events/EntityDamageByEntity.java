package eu.dieytberlegion.Events;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import eu.dieytberlegion.OneVsOne.OneVsOne;
import eu.dieytberlegion.status.gameStart;

public class EntityDamageByEntity implements Listener{

	public EntityDamageByEntity(OneVsOne plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	

	@EventHandler
	public static void EntityDamage(EntityDamageByEntityEvent e){		
		if(!(e.getEntity() instanceof Player)){
			return;
		}
	
		
		Player p = (Player) e.getDamager();
		Player taget = (Player) e.getEntity();
		
        if(OneVsOne.spectator.contains(p.getUniqueId()) || OneVsOne.spectator.contains(taget.getUniqueId()) ){
        	e.setCancelled(true);
        	return;
        }
        if(!OneVsOne.onGame.containsKey(p.getUniqueId()) && !OneVsOne.onGame.containsKey(taget.getUniqueId())){
        	e.setCancelled(true);
        }

        if(OneVsOne.onGame.containsKey(p.getUniqueId()) && OneVsOne.onGame.containsKey(taget.getUniqueId())){
        	if(OneVsOne.domeMode.contains(taget.getUniqueId())){
        		e.setCancelled(true);
        	}
        
        	if(p.getInventory().getItemInHand().getItemMeta().getDisplayName() == "§aGottes Stab"){
        		taget.setHealth(0.5);
        	}
        }

		
     if(!OneVsOne.onGame.containsKey(p.getUniqueId()) && !OneVsOne.onGame.containsKey(taget.getUniqueId()) && !OneVsOne.kitmenu.contains(taget.getUniqueId())){
    	 e.setCancelled(true);
		if(p.getItemInHand().getType() == Material.IRON_SWORD){
			if(!OneVsOne.noChallenge.containsKey(taget.getUniqueId())){
				
				if(OneVsOne.challenge.containsKey(p.getUniqueId())){
					if(OneVsOne.challenge.get(p.getUniqueId()) == taget.getUniqueId()){

						OneVsOne.challenge.remove(p.getUniqueId());

						
						if(OneVsOne.map.containsKey(taget.getUniqueId())){
						gameStart gi = new gameStart();
						if(OneVsOne.kit.containsKey(taget.getUniqueId())){
						gameStart.Start(p, taget, OneVsOne.map.get(taget.getUniqueId()), OneVsOne.kit.get(taget.getUniqueId()),OneVsOne.round.get(taget.getUniqueId()), gi);
						}else{
							gameStart.Start(p, taget, OneVsOne.map.get(taget.getUniqueId()), "-" ,OneVsOne.round.get(taget.getUniqueId()), gi);
							p.sendMessage(OneVsOne.prefix + "§atest Zum Start");
						}
						}
						p.sendMessage(OneVsOne.prefix + "§aDas Spiel beginnt!");
						taget.sendMessage(OneVsOne.prefix + "§aDas Spiel beginnt!");
					return;
				}else if(OneVsOne.challenge.get(taget.getUniqueId()) == p.getUniqueId()){
						e.setCancelled(true);
						p.sendMessage(OneVsOne.prefix + "§cDu kannst jeden Spieler nur einmal herausfordern!");
					}else{
						if(OneVsOne.challenge.get(p.getUniqueId()) == taget.getUniqueId()){
							p.sendMessage(OneVsOne.prefix + "yes 2");
							return;
						}
						p.sendMessage(OneVsOne.prefix + "Deine Anfrage wurde an " + taget.getDisplayName() + " gesendet!");
						taget.sendMessage(OneVsOne.prefix + "Du wurdest von " + p.getDisplayName() + " herausfordern!");
						
						if(OneVsOne.challenge.containsKey(taget.getUniqueId())){
							OneVsOne.challenge.remove(taget.getUniqueId());
							OneVsOne.challenge.put(taget.getUniqueId(), p.getUniqueId());
						}else {
						    OneVsOne.challenge.put(taget.getUniqueId(), p.getUniqueId());
						}
					}
					e.setCancelled(true);
				}else{
					p.sendMessage(OneVsOne.prefix + "Deine Anfrage wurde an " + taget.getDisplayName() + " gesendet!");
					taget.sendMessage(OneVsOne.prefix + "Du wurdest von " + p.getDisplayName() + " herausfordern!");
					
					if(OneVsOne.challenge.containsKey(taget.getUniqueId())){
						OneVsOne.challenge.remove(taget.getUniqueId());
						OneVsOne.challenge.put(taget.getUniqueId(), p.getUniqueId());
					}else {
					    OneVsOne.challenge.put(taget.getUniqueId(), p.getUniqueId());
					}
				}
			
			

		}
		}
		
		
		
		
		}
		
	
	}
	

	
	@EventHandler
	 public void onEntityDamageEvent(EntityDamageEvent e) {
	        if (e.getEntity() instanceof Player) {
		        Player p = (Player) e.getEntity();
		        if (e.getCause() == DamageCause.FALL) {
		        	e.setCancelled(true);
		        } 
	        }

	 }
	
}
