package eu.dieytberlegion.Events;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

import eu.dieytberlegion.OneVsOne.OneVsOne;

public class PlayerGameModeChange implements Listener{

		
		public PlayerGameModeChange(OneVsOne plugin) {
			plugin.getServer().getPluginManager().registerEvents(this, plugin);
		}

		@EventHandler
	public static void PlayerDeathEvnt(PlayerGameModeChangeEvent e){
		   Player p = e.getPlayer();
		  
		   if(OneVsOne.onGame.containsKey(p.getUniqueId())){
			   e.setCancelled(true);
			   e.getNewGameMode().compareTo(GameMode.SURVIVAL);
			   p.sendMessage(OneVsOne.prefix + "§cDu drafst dem Gamemode im Spiel nicht ändern");
		   }
	}
}
