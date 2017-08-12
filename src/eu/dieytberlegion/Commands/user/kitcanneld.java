package eu.dieytberlegion.Commands.user;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.dieytberlegion.Events.onJoin;
import eu.dieytberlegion.OneVsOne.OneVsOne;

public class kitcanneld implements CommandExecutor {

	private OneVsOne plugin;

	public kitcanneld(OneVsOne Instance) {
		this.plugin = OneVsOne.getInstance();
	}

	@SuppressWarnings({ "unused", "deprecation" })
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (command.getName().equalsIgnoreCase("kitstop")) {

			if (!(sender instanceof Player)) {
				sender.sendMessage(OneVsOne.prefix + "§cNur Spieler dürfen /kitstop nutzen!");
				return true;
			}
			Player p = (Player) sender;

			if (!OneVsOne.kitmenu.contains(p.getUniqueId())) {
				p.sendMessage(OneVsOne.prefix + "§cDu bist nicht im Kiteditor!");
				return true;
			}
			onJoin.Joininv(p); 
			p.setGameMode(GameMode.SURVIVAL);
			OneVsOne.kitmenu.remove(p.getUniqueId());
			OneVsOne.fresplayer.remove(p.getUniqueId());
			p.sendMessage(OneVsOne.prefix + "§aDu hast den Kiteditor verlassen!");
		}
		return true;
	}

}
