package eu.dieytberlegion.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import eu.dieytberlegion.OneVsOne.OneVsOne;

public class AsyncPlayerPreLogin implements Listener{

	public AsyncPlayerPreLogin(OneVsOne plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public static void PlayerPreLogin(AsyncPlayerPreLoginEvent e){

		
		 
	}

}
