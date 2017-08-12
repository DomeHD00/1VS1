package eu.dieytberlegion.Events;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import eu.dieytberlegion.MYSQL.mysql;
import eu.dieytberlegion.Objecks.Games;
import eu.dieytberlegion.OneVsOne.OneVsOne;
import eu.dieytberlegion.status.gameStart;

public class PlayerRespawn implements Listener{
	
		private static Games game;

		public PlayerRespawn(OneVsOne plugin) {
			plugin.getServer().getPluginManager().registerEvents(this, plugin);
		}
		
		@EventHandler
		public static void Player(PlayerRespawnEvent e){
			Player p = e.getPlayer();
			
			if (OneVsOne.onGame.containsKey(p.getUniqueId())) {
			game = OneVsOne.onGame.get(p);
	
				if(game.getStatus() == "onGame"){
				Location lp1 = game.getGameInstanc().start1;
				//p.teleport(lp1);
				e.setRespawnLocation(lp1);
				game.getGameInstanc().Setinv(p, game.getGameInstanc());
				}else {
					e.setRespawnLocation((Location) OneVsOne.getInstance().getConfig().get("OneVsOne.world.lobby"));
					onJoin.Joininv(p);
					
					Player p1 = Bukkit.getPlayer(game.getPlayer1());
					Player p2 = Bukkit.getPlayer(game.getPlayer2());
					
					OneVsOne.onGame.remove(p1);
					OneVsOne.onGame.remove(p2);
				}
			}
			
		}

}
