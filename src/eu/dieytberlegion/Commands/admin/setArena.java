package eu.dieytberlegion.Commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.dieytberlegion.OneVsOne.OneVsOne;

public class setArena implements CommandExecutor {

	private OneVsOne plugin;

	public setArena(OneVsOne Instance) {
		this.plugin = OneVsOne.getInstance();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (command.getName().equalsIgnoreCase("schematic")) {
			if(!(sender instanceof Player)){
				sender.sendMessage("§cDu musst ein Player sein !");
				return true;
			}
			Player p = (Player) sender;
			
			if(!p.hasPermission("1vs1.command.setArena")){
				p.sendMessage(OneVsOne.prefix + "§cKeine Rechte !");
				return true;
			}
			
			
			switch(args.length) {
			case 1:
				if(args[0].equalsIgnoreCase("past")){
					if(OneVsOne.getSchematicsMenager().pasteSchematic(p)){
						p.sendMessage(OneVsOne.prefix + "§aDie Schematic wurde Gesetzt");
					}
				}
			    break;
			case 2:
			     if(args[0].equalsIgnoreCase("save")){
					if(OneVsOne.getSchematicsMenager().saveSchematic(p, args[1])){
						p.sendMessage(OneVsOne.prefix + "§aDie Schematic wurde gespeiert");
					}
				}
				else if(args[0].equalsIgnoreCase("load")){
					if(OneVsOne.getSchematicsMenager().loadSchematic(p, args[1])){
						p.sendMessage(OneVsOne.prefix + "§aDie Schematic wurde Geladen");
					}
				}
				else if(args[0].equalsIgnoreCase("delete")){
					if(OneVsOne.getSchematicsMenager().deleteSchematic(args[1])){
						p.sendMessage(OneVsOne.prefix + "§aDie Schematic wurde Gelöscht");
					}
				}
				break;
			default:
				p.sendMessage(OneVsOne.prefix + "§cFalscher Agummente !");
				break;
			}
			
			
		}
		return true;
	}

}
