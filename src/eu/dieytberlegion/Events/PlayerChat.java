package eu.dieytberlegion.Events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import eu.dieytberlegion.OneVsOne.OneVsOne;
import eu.dieytberlegion.nick.Nick;

public class PlayerChat implements Listener{

	public PlayerChat(OneVsOne plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
    public void onChat(AsyncPlayerChatEvent e) {

		Player p = e.getPlayer();
		String chat = null;
		OneVsOne main = OneVsOne.getInstance();
		
		if(p.hasPermission("WarsUP.chat.black")){
			e.setMessage(e.getMessage().replaceAll("&0", "§0"));
		}
		if(p.hasPermission("WarsUP.chat.dblue")){
			e.setMessage(e.getMessage().replaceAll("&1", "§1"));
		}
		if(p.hasPermission("WarsUP.chat.dgrun")){
			e.setMessage(e.getMessage().replaceAll("&2", "§2"));
		}
		if(p.hasPermission("WarsUP.chat.daqua")){
			e.setMessage(e.getMessage().replaceAll("&3", "§3"));
		}
		if(p.hasPermission("WarsUP.chat.dred")){
			e.setMessage(e.getMessage().replaceAll("&4", "§4"));
		}
		if(p.hasPermission("WarsUP.chat.dlila")){
			e.setMessage(e.getMessage().replaceAll("&5", "§5"));
		}
		if(p.hasPermission("WarsUP.chat.gold")){
			e.setMessage(e.getMessage().replaceAll("&6", "§6"));
		}
		if(p.hasPermission("WarsUP.chat.grau")){
			e.setMessage(e.getMessage().replaceAll("&7", "§7"));
		}
		if(p.hasPermission("WarsUP.chat.dgrau")){
			e.setMessage(e.getMessage().replaceAll("&8", "§8"));
		}
		if(p.hasPermission("WarsUP.chat.blue")){
			e.setMessage(e.getMessage().replaceAll("&9", "§9"));
		}
		if(p.hasPermission("WarsUP.chat.grun")){
			e.setMessage(e.getMessage().replaceAll("&a", "§a"));
		}
		if(p.hasPermission("WarsUP.chat.aqua")){
			e.setMessage(e.getMessage().replaceAll("&b", "§b"));
		}
		if(p.hasPermission("WarsUP.chat.red")){
			e.setMessage(e.getMessage().replaceAll("&c", "§c"));
		}
		if(p.hasPermission("WarsUP.chat.lila")){
			e.setMessage(e.getMessage().replaceAll("&d", "§d"));
		}
		if(p.hasPermission("WarsUP.chat.gelb")){
			e.setMessage(e.getMessage().replaceAll("&e", "§e"));
		}
		if(p.hasPermission("WarsUP.chat.weiß")){
			e.setMessage(e.getMessage().replaceAll("&f", "§f"));
		}
		
		
		
		if(p.hasPermission("WarsUP.rang.Owner")){
			chat = main.getInstance().getConfig().getString("Server.loc.Owner.chat");
		}else if(p.hasPermission("WarsUP.rang.Admin")){
			chat = main.getInstance().getConfig().getString("Server.loc.Admin.chat");
		}else if(p.hasPermission("WarsUP.rang.dev")){
			chat = main.getInstance().getConfig().getString("Server.loc.Developer.chat");
		}else if(p.hasPermission("WarsUP.rang.mod")){
			chat = main.getInstance().getConfig().getString("Server.loc.Mod.chat");
		}else if(p.hasPermission("WarsUP.rang.sup")){
			chat = main.getInstance().getConfig().getString("Server.loc.Supporter.chat");
		}else if(p.hasPermission("WarsUP.rang.Partner")){
			chat = main.getInstance().getConfig().getString("Server.loc.Partner.chat");
		}else if(p.hasPermission("WarsUP.rang.Youtuber")){
			chat = main.getInstance().getConfig().getString("Server.loc.Youtuber.chat");
		}else if(p.hasPermission("WarsUP.rang.Freund")){
			chat = main.getInstance().getConfig().getString("Server.loc.Freund.chat");
		}else if(p.hasPermission("WarsUP.rang.Vip")){
			chat = main.getInstance().getConfig().getString("Server.loc.Vip.chat");
		}else {
			chat = main.getInstance().getConfig().getString("Server.loc.User.chat");
		}
		
		if(p.hasPermission("WarsUP.rang.Owner") || p.hasPermission("WarsUP.rang.Admin")){
			e.setFormat(chat + " " + p.getDisplayName() + " §7>> §c" + e.getMessage());
		}else {
			e.setFormat(chat + " " + p.getDisplayName() + " §7>> §7" + e.getMessage());
		}
	}

}
