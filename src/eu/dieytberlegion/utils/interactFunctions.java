package eu.dieytberlegion.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import eu.dieytberlegion.Events.onJoin;
import eu.dieytberlegion.MYSQL.mysql;
import eu.dieytberlegion.OneVsOne.OneVsOne;
import eu.dieytberlegion.nick.Nick;
import eu.dieytberlegion.status.gameStart;

public class interactFunctions {

	public static int in;
	
	public static void interactCommandBlock (Player p) {
		if (!OneVsOne.onGame.containsKey(p)) {
			Inventory inv = p.getServer().createInventory(null, 9 * 3, "§bTools");
			for (int i = 0; i < 9 * 3; i++) {
				inv.setItem(i, CreateGlasskit());
			}
			// glas
			inv.setItem(0, CreateIteam(Material.ARROW, "§bRunden"));
			inv.setItem(2, CreateIteam(Material.COMPASS, "§eWarteschlange betreten"));
			inv.setItem(4, CreateIteam(Material.MAP, "§bMap wählen"));
			inv.setItem(6, CreateIteam(Material.DIAMOND, "§bKiteditor"));
			inv.setItem(8, CreateIteam(Material.EMERALD, "§bKit wählen"));
			inv.setItem(10, CreateIteam(Material.NETHER_STAR, "§bSpiele anschauen"));
			inv.setItem(12, CreateIteam(Material.ENDER_PEARL, "§bAchievements §c(coming soon)"));
			inv.setItem(14, CreateIteam(Material.TNT, "§bLobbys")); 
			//inv.setItem(14, CreateIteam(Material.DIAMOND_SWORD, "§bTeam Kämpfe §c(coming soon)"));
			//inv.setItem(16, CreateIteam(Material.WORKBENCH, "§bTuniere §c(coming soon)"));
			p.openInventory(inv);
		}
	}

	public static void interactArrow (Player p) {
		if (!OneVsOne.onGame.containsKey(p.getUniqueId())) {
			Inventory inv = p.getServer().createInventory(null, 9 * 1, "§bRunden");
			inv.setItem(0, CreateIteam(Material.ARROW, "§e1 Runde"));
			inv.setItem(3, CreateIteam(Material.ARROW, "§e3 Runde"));
			inv.setItem(5, CreateIteam(Material.ARROW, "§e5 Runde"));
			inv.setItem(8, CreateIteam(Material.ARROW, "§e10 Runde"));
			// glas
			inv.setItem(1, CreateGlass());
			inv.setItem(2, CreateGlass());
			inv.setItem(4, CreateGlass());
			inv.setItem(6, CreateGlass());
			inv.setItem(7, CreateGlass());
			p.openInventory(inv);
		}
	}

	public static void interactNetherStar (Player p) {
		if (!OneVsOne.onGame.containsKey(p.getUniqueId())) {
			Bukkit.dispatchCommand(p, "spectate");
		}
	}

	public static void interactEmerald (Player p) {
		if (!OneVsOne.onGame.containsKey(p.getUniqueId())) {
			Inventory inv = p.getServer().createInventory(null, 9 * 1, "§bkit Wählen");

			// mysql abfragen
			Bukkit.getScheduler().runTaskAsynchronously(OneVsOne.getInstance(), () -> {
				int i = 0;

				try {
					ResultSet pos = mysql.getResult("SELECT * FROM kits WHERE UUID = '" + p.getUniqueId() + "'");
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
		}

	}

	public static void interactDiamond (Player p) {
		if (!OneVsOne.onGame.containsKey(p.getUniqueId())) {

			in = 0;
			try {
				ResultSet pos = mysql.getResult("SELECT * FROM kits WHERE playerName = '" + p.getName() + "'");
				while (pos.next()) {
					in++;
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			if (in >= 3) {
				p.sendMessage(OneVsOne.prefix + "§cDu hast schon 3 Kits, Benutze §e/kitDelete §c um ein kit zu löschen!");
				OneVsOne.kitmenu.remove(p);
				OneVsOne.fresplayer.remove(p);
				onJoin.Joininv(p);
				p.setGameMode(GameMode.SURVIVAL);
			}

			if (!(in >= 3)) {
				OneVsOne.kitmenu.add(p.getUniqueId());
				OneVsOne.fresplayer.add(p.getUniqueId());
				p.setGameMode(GameMode.CREATIVE);
				p.setAllowFlight(false);
				p.getInventory().clear();
				p.updateInventory();
				p.sendMessage(OneVsOne.prefix + "§aDu bist jetzt im Kiteditor:");
				p.sendMessage(OneVsOne.prefix + "§e/kitsave <Kit-name> §aUm das Kit zu speichern!");
				p.sendMessage(OneVsOne.prefix + "§e/kitstop §aUm den Kiteditor zu verlassen!");
				
	
				
				if (p.hasPermission("1vs1.kit.defaultkit")) {
					p.sendMessage(OneVsOne.prefix + "§e/deaultkitSave §aUm Das deaultkitSave zu ändern!");
				}
			}
		}
	}

	public static void interactCompass (Player p) {
		if (!OneVsOne.onGame.containsKey(p.getUniqueId()) && !OneVsOne.kitmenu.contains(p.getUniqueId())) {

			if(OneVsOne.queue.contains(p.getUniqueId())){
				OneVsOne.queue.remove(p.getUniqueId());
				p.sendMessage(OneVsOne.prefix + "§cDu hast die Warteschlange §cverlassen!");
			}else{
				OneVsOne.queue.add(p.getUniqueId());
				p.sendMessage(OneVsOne.prefix + "§aDu bist jetzt in der Warteschlange!");
				
				if (OneVsOne.queue.size() >= 2) {
					UUID p1 = OneVsOne.queue.get(0);
					UUID p2 = OneVsOne.queue.get(1);

					String Map = OneVsOne.map.get(p1);

					gameStart.Start(Bukkit.getPlayer(p1), Bukkit.getPlayer(p2), OneVsOne.map.get(p1), "-", 3,new gameStart());
					OneVsOne.queue.remove(p1);
					OneVsOne.queue.remove(p2);
				}
			}


		}
	}
	
	public static void interactMap (Player p) {
		if (!OneVsOne.onGame.containsKey(p.getUniqueId())) {
			Inventory inv = p.getServer().createInventory(null, 9 * 6, "§bMap Wählen");

			// mysql abfragen
			Bukkit.getScheduler().runTaskAsynchronously(OneVsOne.getInstance(), () -> {
				int i = 0;

				try {
					ResultSet pos = mysql.getResult("SELECT * FROM setup");
					while (pos.next()) {
						inv.setItem(i, CreateIteam(Material.WOOL, pos.getString("Name")));
						i++;
						if (i == 9 * 6) {
							break;
						}
					}
					while (!(i == 9 * 6)) {
						inv.setItem(i, CreateGlass());
						i++;
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

				p.openInventory(inv);
			});

		}
	}
	public static void interactTNT (Player p) {
		
		Inventory inv = p.getServer().createInventory(null, 9 * 1, "§bLobbywählen");
		
		inv.setItem(0, Createcolor(8,  "§7Normale Lobby"));
		inv.setItem(2, Createcolor(11, "§6VIP Lobby"));
		inv.setItem(4, Createcolor(5,  "§5Youtuber Lobby"));
		inv.setItem(6, Createcolor(12, "§9Team Lobby"));
		inv.setItem(8, Createcolor(1,  "§8AFK Lobby"));
		
		p.openInventory(inv);
		
	}
	public static void interactColor (Player p) {
		if (!OneVsOne.onGame.containsKey(p.getUniqueId())) {
		if (OneVsOne.noChallenge.containsKey(p.getUniqueId())) {
			OneVsOne.noChallenge.remove(p.getUniqueId());

			ItemStack i = p.getInventory().getItem(1);
			ItemMeta meta = i.getItemMeta();
			meta.setDisplayName("§cKeiner darf mich herausfordern");
			i.setItemMeta(meta);
			p.getInventory().setItem(1, i);

			p.sendMessage(OneVsOne.prefix + "§aDu kannst jetzt wieder herausfordert werden !");
		} else {
			OneVsOne.noChallenge.put(p.getUniqueId(), true);

			ItemStack i = p.getInventory().getItem(1);
			ItemMeta meta = i.getItemMeta();
			meta.setDisplayName("§aJeder darf mich herausfordern");
			i.setItemMeta(meta);
			p.getInventory().setItem(1, i);

			p.sendMessage(OneVsOne.prefix + "§cDu kannst nicht mehr herausfordert werden !");
		}
		}
		}
	
	
	//--- Itemcratoren !
	
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
	
	private static ItemStack Createcolor(int id,String Name) {

		ItemStack item = new ItemStack(351, 1, (byte) id);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(Name);
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
