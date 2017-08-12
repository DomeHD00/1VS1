package eu.dieytberlegion.Commands.admin;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.dieytberlegion.Events.onJoin;
import eu.dieytberlegion.OneVsOne.OneVsOne;
import eu.dieytberlegion.nick.Nick;

public class Builder implements CommandExecutor {

	private OneVsOne plugin;

	public Builder(OneVsOne Instance) {
		this.plugin = OneVsOne.getInstance();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (command.getName().equalsIgnoreCase("builder")) {

			if (!(sender instanceof Player)) {
				sender.sendMessage("§cNur Spieler dürfen /Builder nutzen");
				return true;
			}

			Player p = (Player) sender;

			if (p.hasPermission("1vs1.system.builder")) {

				if(OneVsOne.Builder.contains(p)){
				OneVsOne.Builder.remove(p);
				onJoin.Joininv(p);
				p.setGameMode(GameMode.SURVIVAL);
				p.sendMessage(OneVsOne.prefix + "§aDu hast den Buildermodus verlassen !");
				}else {
					OneVsOne.Builder.add(p);
					p.setGameMode(GameMode.CREATIVE);
					p.getInventory().clear();
					p.sendMessage(OneVsOne.prefix + "§aDu hast den Buildermodus eingeschaltet !");
				}
				
			}

		}
		return true;

	}

}
