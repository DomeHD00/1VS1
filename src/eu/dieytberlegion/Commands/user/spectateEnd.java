package eu.dieytberlegion.Commands.user;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.dieytberlegion.Events.onJoin;
import eu.dieytberlegion.Objecks.Games;
import eu.dieytberlegion.OneVsOne.OneVsOne;

public class spectateEnd implements CommandExecutor {

	private OneVsOne plugin;

	public spectateEnd(OneVsOne Instance) {
		this.plugin = OneVsOne.getInstance();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (command.getName().equalsIgnoreCase("spectateend")) {

			if (!(sender instanceof Player)) {
				sender.sendMessage(OneVsOne.prefix + "§cNur Spieler dürfen /spectator nutzen!");
				return true;
			}
			Player p = (Player) sender;
			if(!OneVsOne.spectator.contains(p.getUniqueId())){
				p.sendMessage(OneVsOne.prefix + "§cDu bist kein Spectator!");
				return true;
			}
			if(args.length != 0){
				p.sendMessage(OneVsOne.prefix + "§c/spectateend");
				return true;
			}
			
			p.setAllowFlight(false);
			OneVsOne.spectator.remove(p.getUniqueId());	
			for(Player all : Bukkit.getOnlinePlayers()){
			  all.showPlayer(p);	
			}
			games : for(Games game : OneVsOne.Games){
				for(Player sp : game.getSpectatPlayer()){
					if(game.getSpectatPlayer().contains(p)){
						game.getSpectatPlayer().remove(sp);
						break games;
					}
				}
			}
			p.setGameMode(GameMode.SURVIVAL);
			onJoin.Joininv(p);
			p.teleport((Location) OneVsOne.getInstance().getConfig().get("OneVsOne.world.lobby"));	
			
			p.sendMessage(OneVsOne.prefix + "§aDu bist jetzt kein Spectator mehr!");
			
		}
		return true;
	}
}
