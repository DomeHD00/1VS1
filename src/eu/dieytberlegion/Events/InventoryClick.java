package eu.dieytberlegion.Events;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import eu.dieytberlegion.MYSQL.mysql;
import eu.dieytberlegion.Objecks.Games;
import eu.dieytberlegion.OneVsOne.OneVsOne;
import eu.dieytberlegion.status.gameStart;
import eu.dieytberlegion.utils.LobbyMangement;
import eu.dieytberlegion.utils.interactFunctions;

public class InventoryClick implements Listener {

	public static Plugin plugin;

	public InventoryClick(OneVsOne plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void PlayerDropItem(PlayerDropItemEvent e) {

	}

	@EventHandler
	public void onklick(InventoryClickEvent e) {

		Player p = (Player) e.getWhoClicked();

		if (e.getInventory().getName().equalsIgnoreCase("§bTools")) {

			e.setCancelled(true);
			String Name = e.getCurrentItem().getItemMeta().getDisplayName();
			Material m = e.getCurrentItem().getType();

			if (Name != "§c") {
				Boolean clear = false;

				switch (Name) {
				case "§bRunden":
					p.closeInventory();
					interactFunctions.interactArrow(p);
					break;
				case "§eWarteschlange betreten":
					interactFunctions.interactCompass(p);
					p.closeInventory();
					break;
				case "§bMap wählen":
					p.closeInventory();
					interactFunctions.interactMap(p);
					break;
				case "§bKiteditor":
					interactFunctions.interactDiamond(p);
					p.closeInventory();
					clear = true;
					break;
				case "§bKit wählen":
					p.closeInventory();
					interactFunctions.interactEmerald(p);
					break;
				case "§bSpiele anschauen":
					interactFunctions.interactNetherStar(p);
					break;
				case "§bAchievements §c(coming soon)":
					p.closeInventory();
					break;
				case "§bLobbys":
					interactFunctions.interactTNT(p);
					break;
				case "§bTuniere §c(coming soon)":
					p.closeInventory();
					break;
				default:
					p.closeInventory();
					break;
				}

			}
		} else if (e.getInventory().getName().equalsIgnoreCase("§bRunden")) {

			e.setCancelled(true);
			if (e.getCurrentItem().hasItemMeta()) {

				String Name = e.getCurrentItem().getItemMeta().getDisplayName();
				if (Name != "§c") {
					Name = Name.replace("§e", "");
					Name = Name.replaceAll(" Runde", "");
					if (OneVsOne.round.containsKey(p.getUniqueId())) {
						OneVsOne.round.remove(p.getUniqueId());
					}
					OneVsOne.round.put(p.getUniqueId(), Integer.parseInt(Name));
					p.sendMessage(OneVsOne.prefix + "§aDu spielst jetzt §e" + Name + " §aRunden.");

				}
			}
			p.closeInventory();
		} else if (e.getInventory().getName().equalsIgnoreCase("§bkit Wählen")) {
			e.setCancelled(true);
			String Name = e.getCurrentItem().getItemMeta().getDisplayName();
			if (Name != "§c") {
				Name = Name.replace("§e", "");
				Name = Name.replace(" Runde", "");
				if (OneVsOne.kit.containsKey(p)) {
					OneVsOne.kit.remove(p);
				}
				OneVsOne.kit.put(p.getUniqueId(), Name);
				p.sendMessage(OneVsOne.prefix + "§aDu hast das Kit §e" + Name + " §aausgerüstete");
			}
			p.closeInventory();
		} else if (e.getInventory().getName().equalsIgnoreCase("§bkit Löschen")) {
			e.setCancelled(true);
			String Namee = e.getCurrentItem().getItemMeta().getDisplayName();

			if (Namee != "§c") {

				Bukkit.getScheduler().runTaskAsynchronously(OneVsOne.getInstance(), () -> {
					String Name = e.getCurrentItem().getItemMeta().getDisplayName();
					Name = Name.replace("§e", "");
					mysql.update(
							"DELETE FROM kits WHERE kitName = '" + Name + "' AND UUID = '" + p.getUniqueId() + "'");
				});

				p.sendMessage(OneVsOne.prefix + "§aDas Kit wurde Gelöscht !");
			}
			p.closeInventory();
		} else if (e.getInventory().getName().equalsIgnoreCase("§bMap Wählen")) {
			// Click abfragen !

			String map = e.getCurrentItem().getItemMeta().getDisplayName();
			if (!(map == "§cKeine Map")) {
				OneVsOne.map.put(p.getUniqueId(), map);
				p.sendMessage(OneVsOne.prefix + "§aDu Hast die Map §e" + map + " §agewählt");
			}
			e.setCancelled(true);
			p.closeInventory();

		} else if (e.getInventory().getName().equalsIgnoreCase("§bSpiele")) {
			e.setCancelled(true);
			String Name = e.getCurrentItem().getItemMeta().getDisplayName();
			Games Game = null;

			if (Name != "§cKein Spiel") {

				for (Games game : OneVsOne.Games) {
					if (game.getName().equals(Name)) {
						Game = game;
						break;
					}
				}

				OneVsOne.spectator.add(p.getUniqueId());
				p.getInventory().clear();
				Game.getSpectatPlayer().add(p);
				p.setAllowFlight(true);
				for (Player all : Bukkit.getOnlinePlayers()) {
					all.hidePlayer(p);
				}
				p.teleport(Game.getArena().getSpctator());
				p.sendMessage(OneVsOne.prefix + "§aDu Beobachtes jetzt das Spiel: §e" + Game.getName());

			}
			p.closeInventory();
		} else if (e.getInventory().getName().equalsIgnoreCase("§bLobbywählen")) {
			e.setCancelled(true);

			if (e.getCurrentItem() != null) {
				if (e.getCurrentItem().hasItemMeta()) {
					String name = e.getCurrentItem().getItemMeta().getDisplayName();

					if (name.equals("§7Normale Lobby")) {
						LobbyMangement.setNormalLobby(p);
					} else if (name.equals("§6VIP Lobby")) {
						if (p.hasPermission("1vs1.lobby.vip")) {
							LobbyMangement.setVipLobby(p);
						}else {
							p.sendMessage(OneVsOne.nopem);
						}
					} else if (name.equals("§5Youtuber Lobby")) {
						if (p.hasPermission("1vs1.lobby.youtube")) {
							LobbyMangement.setYoutberLobby(p);
						}else {
							p.sendMessage(OneVsOne.nopem);
						}
					} else if (name.equals("§9Team Lobby")) {
						if (p.hasPermission("1vs1.lobby.team")) {
							LobbyMangement.setTeamLobby(p);
						}else {
							p.sendMessage(OneVsOne.nopem);
						}
					} else if (name.equals("§8AFK Lobby")) {
						if (p.hasPermission("1vs1.lobby.afk")) {
							LobbyMangement.setAfkLobby(p);
						}else {
							p.sendMessage(OneVsOne.nopem);
						}
					}

				}
			}
			p.closeInventory();
		} else {
			if (OneVsOne.Builder.contains(p) || OneVsOne.kitmenu.contains(p.getUniqueId())
					|| OneVsOne.onGame.containsKey(p.getUniqueId())) {
				e.setCancelled(false);
			} else {
				e.setCancelled(true);
			}

		}

	}

	private static ItemStack CreateIteam(Material m, String Name) {

		ItemStack item = new ItemStack(m);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(Name);
		item.setItemMeta(meta);

		return item;
	}

}
