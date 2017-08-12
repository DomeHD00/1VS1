package eu.dieytberlegion.Commands.user;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import eu.dieytberlegion.MYSQL.mysql;
import eu.dieytberlegion.OneVsOne.OneVsOne;
import eu.dieytberlegion.nick.Nick;

public class kitDelete  implements CommandExecutor {

	private OneVsOne plugin;

	public kitDelete(OneVsOne Instance) {
		this.plugin = OneVsOne.getInstance();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (command.getName().equalsIgnoreCase("kitdelete")) {

			if (!(sender instanceof Player)) {
				sender.sendMessage(OneVsOne.prefix + "§cNur Spieler dürfen /kitdelete nutzen!");
				return true;
			}
			Player p = (Player) sender;

			if (OneVsOne.kitmenu.contains(p)) {
				p.sendMessage(OneVsOne.prefix + "§cDu musst erst den Kiteditor verlassen!");
				return true;
			}
			
			if(args.length == 0){
			Inventory inv = p.getServer().createInventory(null, 9 * 1, "§bKit löschen");

			// mysql abfragen
			Bukkit.getScheduler().runTaskAsynchronously(OneVsOne.getInstance(), () -> {
				int i = 0;

				try {
					ResultSet pos = mysql.getResult("SELECT * FROM kits WHERE UUID = '"+ p.getUniqueId() +"'");
					while (pos.next()) {
						inv.setItem(i, CreateIteam(Material.DIAMOND_SWORD, "§e" + pos.getString("kitName")));
						i++;
						if (i == 9 * 1) {
							break;
						}
					}
					while (!(i == 9 * 1)) {
						inv.setItem(i, CreateGlasskit());
						i++;
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			
			p.openInventory(inv);
			});
			
			}else{
				p.sendMessage(OneVsOne.prefix + "§c/kitdelete");
			}
		}
		return true;
	}

		

		private static ItemStack CreateGlasskit() {

			ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§c");
			item.setItemMeta(meta);

			return item;
		}

		private static ItemStack CreateGlass() {

			ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§cKeine Map");
			item.setItemMeta(meta);

			return item;
		}

		private static ItemStack CreateIteam(Material m, String Name) {

			ItemStack item = new ItemStack(m);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(Name);
			item.setItemMeta(meta);

			return item;
		}
}
