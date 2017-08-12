package eu.dieytberlegion.Events;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import eu.dieytberlegion.Objecks.Games;
import eu.dieytberlegion.OneVsOne.OneVsOne;
import eu.dieytberlegion.status.gameEnd;

public class onQuit implements Listener{

	public onQuit(OneVsOne plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	@EventHandler
	public static void Quit(PlayerQuitEvent e){
		Player p = e.getPlayer();
		
		OneVsOne.kit.remove(p);
		p.setGameMode(GameMode.SURVIVAL);
		
		if(p.hasMetadata("schematicLoad")){
			schematicRemove(p);
		}
		if(OneVsOne.spectator.contains(p.getUniqueId())){
			OneVsOne.spectator.remove(p.getUniqueId());
		}
		if(OneVsOne.fresplayer.contains(p.getUniqueId())){
			OneVsOne.fresplayer.remove(p.getUniqueId());
		}
		if(OneVsOne.kitmenu.contains(p.getUniqueId())){
			OneVsOne.kitmenu.remove(p.getUniqueId());
		}
		if(OneVsOne.onGame.containsKey(p.getUniqueId())){
			//Player taget = OneVsOne.challenge.get(p);
			Games game = OneVsOne.onGame.get(p.getUniqueId());
			Player taget;
			if(game.getPlayer1() == p.getUniqueId()){
				taget = Bukkit.getPlayer(game.getPlayer2());
			}else {
				taget = Bukkit.getPlayer(game.getPlayer1());
			}
			gameEnd.end(taget, false);
			OneVsOne.challenge.remove(p.getUniqueId());
			OneVsOne.onGame.remove(p.getUniqueId());
		}
		
		
		e.setQuitMessage("");
	}
	
	private static void  schematicRemove(Player p){
		
		ArrayList<String> list = (ArrayList<String>) p.getMetadata ("schematicLoad").get(0).value();
		
		World w = Bukkit.getWorld(OneVsOne.getInstance().getConfig().getString("OneVsOne.world.name1"));
		Location l = new Location(w , 0, 0, 0);
		l.setX(0);
		l.setY(200);
		l.setZ(0);
		
		for(String str : list){
			String[] array = str.split(";");
			Material material = Material.AIR;
			byte data = Byte.valueOf(array[1]);
			int posX = Integer.parseInt(array[2]);
			int posY = Integer.parseInt(array[3]);
			int posZ = Integer.parseInt(array[4]);
			//Bukkit.broadcastMessage("Block ID" + l.getBlock().getType().getId());
			l.clone().add(posX, posY, posZ).getBlock().setType(material);
			l.clone().add(posX, posY, posZ).getBlock().setData(data);
			
			p.removeMetadata("schematicLoad", OneVsOne.getInstance());
		}
		
	}

}
