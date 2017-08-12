package eu.dieytberlegion.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import net.minecraft.server.v1_8_R3.Blocks;

public class topPlayerFile {

	 public static Location defaultLocation = new Location(Bukkit.getWorld("world"), 0, 0, 0);
	
	public void setStandert(){
		FileConfiguration cfg = getFileConfiguration();
		
		cfg.options().copyDefaults(true);
		
		cfg.addDefault("Top.Player1", defaultLocation);
		cfg.addDefault("Top.Player2", defaultLocation);
		cfg.addDefault("Top.Player3", defaultLocation);
		cfg.addDefault("Top.Player4", defaultLocation);
		cfg.addDefault("Top.Player5", defaultLocation);
		cfg.addDefault("Top.Player6", defaultLocation);
		cfg.addDefault("Top.Player7", defaultLocation);
		cfg.addDefault("Top.Player8", defaultLocation);
		cfg.addDefault("Top.Player9", defaultLocation);
		cfg.addDefault("Top.Player10", defaultLocation);
		
		try {
			cfg.save(getFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private File getFile() {
		return new File("plugins/1VS1", "topPlayer.yml");
	}
	
	private FileConfiguration getFileConfiguration(){
		return YamlConfiguration.loadConfiguration(getFile());
	}
	
	public void schreibData(String vaule,Location location){
		FileConfiguration cfg = getFileConfiguration();
		cfg.set(vaule, location);
		try {
			cfg.save(getFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void readData(){
		FileConfiguration cfg = getFileConfiguration();
		
		/*updateTopPlayer.Player1 = (Location) cfg.get("Player1");
		updateTopPlayer.Player2 = (Location) cfg.get("Player2");
		updateTopPlayer.Player3 = (Location) cfg.get("Player3");
		updateTopPlayer.Player4 = (Location) cfg.get("Player4");
		updateTopPlayer.Player5 = (Location) cfg.get("Player5");
		updateTopPlayer.Player6 = (Location) cfg.get("Player6");
		updateTopPlayer.Player7 = (Location) cfg.get("Player7");
		updateTopPlayer.Player8 = (Location) cfg.get("Player8");
		updateTopPlayer.Player9 = (Location) cfg.get("Player9");
		updateTopPlayer.Player10 = (Location) cfg.get("Player10");*/
	}
}
