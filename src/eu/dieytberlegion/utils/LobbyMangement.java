package eu.dieytberlegion.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import eu.dieytberlegion.OneVsOne.OneVsOne;

public class LobbyMangement {
	
	public static void setNormalLobby(Player p){
		removeAll(p);
		for(Player all : Bukkit.getOnlinePlayers()){
			all.showPlayer(p);
			p.showPlayer(all);
		}
		p.sendMessage(OneVsOne.prefix + "§aDu bist nun in der §7Normale§a Lobby");
	}

	public static void setVipLobby(Player p){
		
		if(!OneVsOne.vipLobby.contains(p.getUniqueId())){
			removeAll(p);
			for(Player all : Bukkit.getOnlinePlayers()){
				p.hidePlayer(all);
				all.hidePlayer(p);
			}
			for(Player all : OneVsOne.vipLobby){
				p.showPlayer(all);
				all.showPlayer(p);
			}
			OneVsOne.vipLobby.add(p);
			p.sendMessage(OneVsOne.prefix + "§aDu bist nun in der §6VIP§a Lobby");
		}else {
			p.sendMessage(OneVsOne.prefix + "§cDu bist schon in Der §6VIP§c Lobby");
		}
		
	}
	
	public static void setYoutberLobby(Player p){
		
		if(!OneVsOne.youtubeLobby.contains(p.getUniqueId())){
			removeAll(p);
			for(Player all : Bukkit.getOnlinePlayers()){
				p.hidePlayer(all);
				all.hidePlayer(p);
			}
			for(Player all : OneVsOne.youtubeLobby){
				p.showPlayer(all);
				all.showPlayer(p);
			}
			OneVsOne.youtubeLobby.add(p);
			p.sendMessage(OneVsOne.prefix + "§aDu bist nun in der §5YouTuber§a Lobby");
		}else {
			p.sendMessage(OneVsOne.prefix + "§cDu bist schon in Der §5YouTuber§c Lobby");
		}
		
	}
	
	public static void setTeamLobby(Player p){
		
		if(!OneVsOne.teamLobby.contains(p.getUniqueId())){
			removeAll(p);
			for(Player all : Bukkit.getOnlinePlayers()){
				p.hidePlayer(all);
				all.hidePlayer(p);
			}
			for(Player all : OneVsOne.teamLobby){
				p.showPlayer(all);
				all.showPlayer(p);
			}
			OneVsOne.teamLobby.add(p);
			p.sendMessage(OneVsOne.prefix + "§aDu bist nun in der §9Team§a Lobby");
		}else {
			p.sendMessage(OneVsOne.prefix + "§cDu bist schon in Der §9Team§c Lobby");
		}
		
	}
	
	public static void setAfkLobby(Player p){
		
		if(!OneVsOne.afkLobby.contains(p.getUniqueId())){
			removeAll(p);
			for(Player all : Bukkit.getOnlinePlayers()){
				p.hidePlayer(all);
				all.hidePlayer(p);
			}
			for(Player all : OneVsOne.afkLobby){
				p.showPlayer(all);
				all.showPlayer(p);
			}
			OneVsOne.afkLobby.add(p);
			p.sendMessage(OneVsOne.prefix + "§aDu bist nun in der §8Afk§a Lobby");
		}else {
			p.sendMessage(OneVsOne.prefix + "§cDu bist schon in Der §8Afk§c Lobby");
		}
		
	}
	
	
	private static void removeAll(Player p){
		
		if(OneVsOne.vipLobby.contains(p)){
			OneVsOne.vipLobby.remove(p);
		}
		if(OneVsOne.youtubeLobby.contains(p)){
			OneVsOne.youtubeLobby.remove(p);
		}
		if(OneVsOne.teamLobby.contains(p)){
			OneVsOne.teamLobby.remove(p);
		}
		if(OneVsOne.afkLobby.contains(p)){
			OneVsOne.afkLobby.remove(p);
		}
		
	}
	
}
