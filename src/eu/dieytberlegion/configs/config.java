package eu.dieytberlegion.configs;


import java.io.File;
import java.security.PublicKey;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import eu.dieytberlegion.OneVsOne.OneVsOne;

public class config {

	public static Location l;
	
	@SuppressWarnings("static-access")
	public static void setConfig() {
		OneVsOne OneVsOne = eu.dieytberlegion.OneVsOne.OneVsOne.getInstance();

		OneVsOne main = OneVsOne;
		World w = Bukkit.getWorld("world");
	     l = new Location(w, 1.0, 64.0, 1.0);
		//Words
		OneVsOne.getConfig().addDefault("OneVsOne.world.name1", "map");
		OneVsOne.getConfig().addDefault("OneVsOne.world.lobby", l);

		
		main.getInstance().getConfig().addDefault("Server.rang.Owner.tab", "§4Onwer |");
		main.getInstance().getConfig().addDefault("Server.loc.Owner.chat", "§4[Onwer]:");
		//Admin
		main.getInstance().getConfig().addDefault("Server.rang.Admin.tab", "§cAdmin |");
		main.getInstance().getConfig().addDefault("Server.loc.Admin.chat", "§c[Admin]:");
		//dev
		main.getInstance().getConfig().addDefault("Server.rang.Developer.tab", "§4dev |");
		main.getInstance().getConfig().addDefault("Server.loc.Developer.chat", "§b[Developer]:");
		//mod
		main.getInstance().getConfig().addDefault("Server.rang.Mod.tab", "§2Mod |");
		main.getInstance().getConfig().addDefault("Server.loc.Mod.chat", "§2[Mod]:");
		//supporter
		main.getInstance().getConfig().addDefault("Server.rang.Supporter.tab", "§eSup |");
		main.getInstance().getConfig().addDefault("Server.loc.Supporter.chat", "§e[Supporter]:");
		//Partner
		main.getInstance().getConfig().addDefault("Server.rang.Partner.tab", "§d");
		main.getInstance().getConfig().addDefault("Server.loc.Partner.chat", "§d[Partner]:");
		//Youtuber
		main.getInstance().getConfig().addDefault("Server.rang.Youtuber.tab", "§5");
		main.getInstance().getConfig().addDefault("Server.loc.Youtuber.chat", "§5[Youtuber]:");
		//Freund
		main.getInstance().getConfig().addDefault("Server.rang.Freund.tab", "§a");
		main.getInstance().getConfig().addDefault("Server.loc.Freund.chat", "§a[Freund]:");
		//Vip
		main.getInstance().getConfig().addDefault("Server.rang.Vip.tab", "§6");
		main.getInstance().getConfig().addDefault("Server.loc.Vip.chat", "§e[VIP]:");
		//user
		main.getInstance().getConfig().addDefault("Server.rang.User.tab", "§7");
		main.getInstance().getConfig().addDefault("Server.loc.User.chat", "§7");
		

		OneVsOne.getConfig().options().copyDefaults(true);
		OneVsOne.saveConfig();
		OneVsOne.reloadConfig();
	}
	
	
	
	
}
