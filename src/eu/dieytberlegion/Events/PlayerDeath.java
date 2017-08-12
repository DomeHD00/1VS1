package eu.dieytberlegion.Events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import eu.dieytberlegion.Objecks.Games;
import eu.dieytberlegion.OneVsOne.OneVsOne;
import eu.dieytberlegion.status.gameEnd;
import eu.dieytberlegion.status.gameStart;

public class PlayerDeath implements Listener {

	static int schedule;

	public PlayerDeath(OneVsOne plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public static void PlayerDeathEvent(PlayerDeathEvent e) {
		Player p = e.getEntity().getKiller();
		Player taget = e.getEntity().getPlayer();

		if (OneVsOne.onGame.containsKey(taget.getUniqueId())) {
			Games Game = OneVsOne.onGame.get(taget.getUniqueId());

			Bukkit.getPlayer(Game.getPlayer1()).sendMessage(OneVsOne.prefix + "Der Spieler§e " + taget.getDisplayName() + " §7ist gestorben!");
			Bukkit.getPlayer(Game.getPlayer1()).sendMessage(OneVsOne.prefix + "Der Spieler§e " + p.getDisplayName() + " §7hätte noch "
					+ (int) (p.getHealth() / 2) + " Herzen!");
			Bukkit.getPlayer(Game.getPlayer2()).sendMessage(OneVsOne.prefix + "Der Spieler§e " + taget.getDisplayName() + " §7ist gestorben!");
			Bukkit.getPlayer(Game.getPlayer2()).sendMessage(OneVsOne.prefix + "Der Spieler§e " + p.getDisplayName() + " §7hätte noch "
					+ (int) (p.getHealth() / 2) + " Herzen!");

			if (Game.getPlayer1() == p.getUniqueId()) {
				int i = Game.getponisPlayer1();
				i++;

				int o = Game.getdponisPlayer2();
				o++;

				Game.setponisPlayer1(i);
				Game.setdponisPlayer2(o);
			} else {
				int i = Game.getponisPlayer2();
				i++;

				int o = Game.getdponisPlayer1();
				o++;

				Game.setponisPlayer2(i);
				Game.setdponisPlayer1(o);
			}

			if (Game.getponisPlayer1() == Game.getRound()) {
				Bukkit.getPlayer(Game.getPlayer1()).setHealth(20);
				Bukkit.getPlayer(Game.getPlayer2()).setHealth(20);
				Bukkit.getScheduler().scheduleSyncDelayedTask(OneVsOne.getInstance(), new Runnable() {
					@Override
					public void run() {
						gameEnd.end(Bukkit.getPlayer(Game.getPlayer1()), true);
					}
				}, 20 * 1);
			} else if (Game.getponisPlayer2() == Game.getRound()) {
				Bukkit.getPlayer(Game.getPlayer1()).setHealth(20);
				Bukkit.getPlayer(Game.getPlayer2()).setHealth(20);
				Bukkit.getScheduler().scheduleSyncDelayedTask(OneVsOne.getInstance(), new Runnable() {
					@Override
					public void run() {
						gameEnd.end(Bukkit.getPlayer(Game.getPlayer2()), true);
					}
				}, 20 * 1);
			} else {
				Game.setCountdown(3);
				Game.getGameInstanc().Setinv(p, Game.getGameInstanc());
				Game.getGameInstanc().Setinv(taget, Game.getGameInstanc());
				if (Game.getPlayer1() == p.getUniqueId()) {
					OneVsOne.fresplayer.add(p.getUniqueId());
					OneVsOne.fresplayer.add(taget.getUniqueId());
					Bukkit.getPlayer(Game.getPlayer2()).setHealth(20);
					Location lp1 = Game.getGameInstanc().start1;
					Bukkit.getPlayer(Game.getPlayer2()).teleport(lp1);
					Bukkit.getScheduler().scheduleSyncDelayedTask(OneVsOne.getInstance(), new Runnable() {
						@Override
						public void run() {
							p.teleport(Game.getGameInstanc().start2);
							Game.setCountdown(3);
							Game.getGameInstanc().Setinv(Bukkit.getPlayer(Game.getPlayer2()), Game.getGameInstanc());
						}
					}, 20 * 1);
				} else {
					OneVsOne.fresplayer.add(Bukkit.getPlayer(Game.getPlayer1()).getUniqueId());
					OneVsOne.fresplayer.add(Bukkit.getPlayer(Game.getPlayer2()).getUniqueId());
					Bukkit.getPlayer(Game.getPlayer1()).setHealth(20);
					Location lp1 = Game.getGameInstanc().start1;
					Bukkit.getPlayer(Game.getPlayer1()).teleport(lp1);
					Bukkit.getScheduler().scheduleSyncDelayedTask(OneVsOne.getInstance(), new Runnable() {
						@Override
						public void run() {
							p.teleport(Game.getGameInstanc().start2);
							Game.setCountdown(3);
							Game.getGameInstanc().Setinv(Bukkit.getPlayer(Game.getPlayer1()), Game.getGameInstanc());
						}
					}, 20 * 1);
				}
				schedule = Bukkit.getScheduler().scheduleSyncRepeatingTask(OneVsOne.getInstance(), new Runnable() {
					@Override
					public void run() {
						if (Game.getCountdown() > 0) {
							// Wenn Der contdown läuft | wird
							// das jede Sekunde
							// aufgrufen !
							int i = Game.getCountdown();
							i--;
							Game.setCountdown(i);
							p.setLevel(Game.getCountdown());
							taget.setLevel(Game.getCountdown());
							if (Game.getCountdown() == 0) {
								p.sendMessage(OneVsOne.prefix + "§aDie Runde beginnt jetzt!");
								taget.sendMessage(OneVsOne.prefix + "§aDie Runde beginnt jetzt!");
								p.setLevel(0);
								taget.setLevel(0);
							}
						} else {
							Bukkit.getScheduler().cancelTask(schedule);
							OneVsOne.fresplayer.remove(Bukkit.getPlayer(Game.getPlayer1()).getUniqueId());
							OneVsOne.fresplayer.remove(Bukkit.getPlayer(Game.getPlayer2()).getUniqueId());
						}
					}
				}, 0, 20);

			}

			e.setDeathMessage("");
			e.getDrops().removeAll(e.getDrops());
			Bukkit.getPlayer(Game.getPlayer1()).setHealth(20);
			Bukkit.getPlayer(Game.getPlayer2()).setHealth(20);
		}
	}

}
