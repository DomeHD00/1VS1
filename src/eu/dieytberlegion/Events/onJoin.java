package eu.dieytberlegion.Events;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import eu.dieytberlegion.MYSQL.mysql;
import eu.dieytberlegion.OneVsOne.OneVsOne;
import eu.dieytberlegion.nick.Nick;


public class onJoin implements Listener{

	public onJoin(OneVsOne plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public static void join(PlayerJoinEvent e){
		Player p = e.getPlayer();
		Joininv(p);
        e.setJoinMessage("");
        p.setGameMode(GameMode.SURVIVAL);
        
        
        if( OneVsOne.round.containsKey(p)){
        	 OneVsOne.round.remove(p);
        }
        OneVsOne.round.put(p.getUniqueId(), 3);
        
        /*InfinityScoreboard sb = new InfinityScoreboard(p, ipp);
        sb.update(p, ipp);
        OneVsOne.sb.add(sb);*/
        
		
        for(Player all : Bukkit.getOnlinePlayers()){
        	if(OneVsOne.spectator.contains(all)){
        		p.hidePlayer(all);
        	}
        }
        
        if(checkJoin(p)){
        p.teleport((Location) OneVsOne.getInstance().getConfig().get("OneVsOne.world.lobby"));
        	
        Bukkit.getScheduler().runTaskAsynchronously(OneVsOne.getInstance(), () -> {
        	try {
    			ResultSet pos =  mysql.getResult("SELECT * FROM stats WHERE UUID = '"+ p.getUniqueId() +"'");
    			if(pos.next()){
    				double kd =  (double) pos.getInt("kills") / (double) pos.getInt("Deaths");
    				kd = kd * 100;
    				kd = Math.round(kd);
    				kd = kd / 100;
    			    p.sendMessage(OneVsOne.prefix + "Deine KD ist: §e" + kd);
    			}else{
    				mysql.update("INSERT INTO stats VALUES ('"+ p.getUniqueId() +"','"+ p.getName() +"','0','0','0','0','0','0')");
    				p.sendMessage(OneVsOne.prefix + "Willkommen auf dem 1VS1 Server");
    			}
    		} catch (SQLException e1) {
    			e1.printStackTrace();
    		}
        	
           	try {
           		ResultSet pos =  mysql.getResult("SELECT * FROM setup");
				if(pos.next()){
                   OneVsOne.map.put(p.getUniqueId(), pos.getString("Name"));
				}else {
					if(p.hasPermission("1vs1.system.worlds")){
						p.sendMessage("§cDu muss noch eine welt einstellen ");
					}
				}
        		} catch (SQLException e1) {
        			e1.printStackTrace();
        		}
        	
           	
           	try {
           		ResultSet pos =  mysql.getResult("SELECT * FROM kits");
				if(pos.next()){
                   OneVsOne.kit.put(p.getUniqueId(), "Deault-kit");
				}else {
					p.sendMessage("§cDas standert kit muss festgeleget werden: §e/deaultkitSave");
				}
       		} catch (SQLException e1) {
    			e1.printStackTrace();
    		}
        } );
        
        for(Player all : Bukkit.getOnlinePlayers()){
        	if(OneVsOne.afkLobby.contains(all)){
        		all.hidePlayer(p);
        		p.hidePlayer(all);
        	}
        	if(OneVsOne.teamLobby.contains(all)){
        		all.hidePlayer(p);
        		p.hidePlayer(all);
        	}
        	if(OneVsOne.youtubeLobby.contains(all)){
        		all.hidePlayer(p);
        		p.hidePlayer(all);
        	}
        	if(OneVsOne.youtubeLobby.contains(all)){
        		all.hidePlayer(p);
        		p.hidePlayer(all);
        	}
        	
        	
        }
        
        }
	}
	public static void Joininv(Player p){
		//ivnetar clearen
		p.getInventory().clear();
		p.getInventory().setHelmet(null);
		p.getInventory().setChestplate(null);
		p.getInventory().setLeggings(null);
		p.getInventory().setBoots(null);
		//Schwert zum spielen Setzen
		p.getInventory().setItem(0,CreateIteam(Material.IRON_SWORD, "§5Spieler herausfordern")); 
		//nick und Lobbyseletor
		
		if(p.hasPermission("WarsUP.system.nick")){
		p.getInventory().setItem(2,CreateIteam(Material.NAME_TAG, "§5Nick")); 
		}
		
		//Wer draf herausvordern
		p.getInventory().setItem(1,CreateIteam(Material.STICK, "§aJeder darf mich herausfordern")); 
		p.getInventory().setItem(8,CreateIteam(Material.COMMAND, "§bTools")); 
	}
	
	private static Boolean checkJoin(Player p){
		
		//	Location l = new Location(Bukkit.getWorld("world"), 1.0, 1.0, 1.0);
			if((Location) OneVsOne.getInstance().getConfig().get("OneVsOne.world.lobby") == eu.dieytberlegion.configs.config.l){
				p.sendMessage(OneVsOne.prefix + "§cDu Die Spawn Position Festlegen !");
				return false;
			}
		return true;
	}
	
	
	private static ItemStack CreateIteam(Material m, String Name) {

		ItemStack item = new ItemStack(m);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(Name);
		if(m == Material.IRON_SWORD){
		   meta.spigot().setUnbreakable(true);	
		}
		item.setItemMeta(meta);

		return item;
	}
}
