package eu.dieytberlegion.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.plugin.Plugin;

import eu.dieytberlegion.OneVsOne.OneVsOne;

public class WeatherChange implements Listener {

	public static Plugin plugin;

	public WeatherChange(OneVsOne plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	
	@EventHandler
	public void onQuit(WeatherChangeEvent e) {
		e.setCancelled(true);
	}

}
