package eu.dieytberlegion.Events;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.plaf.BorderUIResource.MatteBorderUIResource;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import eu.dieytberlegion.MYSQL.mysql;
import eu.dieytberlegion.OneVsOne.OneVsOne;
import eu.dieytberlegion.status.gameStart;
import eu.dieytberlegion.utils.interactFunctions;


public class PlayerInteract implements Listener {
    
	public PlayerInteract(OneVsOne plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public static void PlayerInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		

		if(e.getAction().equals(Action.PHYSICAL)){
			e.setCancelled(true);
			return;
		}
		
		if (p.getItemInHand().getType() == Material.COMMAND) {
			e.setCancelled(true);
			if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				interactFunctions.interactCommandBlock(p);
			}
		}
		
		if (p.getItemInHand().getType() == Material.ARROW) {
			e.setCancelled(true);
			if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				interactFunctions.interactArrow(p);
			}
		}
		
		if (p.getItemInHand().getType() == Material.ENDER_PEARL) {
			e.setCancelled(true);
		}
		
		if (p.getItemInHand().getType() == Material.NETHER_STAR) {
			e.setCancelled(true);
			if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				interactFunctions.interactNetherStar(p);
			}
		}
		if (p.getItemInHand().getType() == Material.EMERALD) {
			e.setCancelled(true);
			if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				interactFunctions.interactEmerald(p);
			}
		}

		if (p.getItemInHand().getType() == Material.DIAMOND) {
			e.setCancelled(true);
			if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				interactFunctions.interactDiamond(p);
			}
		}
		

		// warteschlagene
		if (p.getItemInHand().getType() == Material.COMPASS) {
			e.setCancelled(true);
			if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				interactFunctions.interactCompass(p);
			}
		}
		// map Wählen
		if (p.getItemInHand().getType() == Material.MAP) {
			e.setCancelled(true);
			if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				interactFunctions.interactMap(p);
			}
		}


		// Draf herrausgeforderen
		if (p.getItemInHand().getType() == Material.STICK) {
			if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				if(!OneVsOne.onGame.containsKey(p)){
				interactFunctions.interactColor(p);
				}
			}
			e.setCancelled(true);
		}

		
		//nicht erlaubte items
		if (p.getItemInHand().getType() == Material.LAVA_BUCKET  || p.getItemInHand().getType() == Material.WATER_BUCKET || p.getItemInHand().getType() == Material.FLINT_AND_STEEL) {
			if(!OneVsOne.Builder.contains(p)){
				e.setCancelled(true);
			}
		}
		
		
		if (p.getItemInHand().getType() == Material.IRON_SPADE) {
			if (!p.hasPermission("1vs1.command.setArena")) {
				return;
			}

			if (p.getGameMode() != GameMode.CREATIVE) {
				return;
			}

			if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
				OneVsOne.selectPosOne(p, e.getClickedBlock().getLocation());
			} else if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				OneVsOne.selectPosTwo(p, e.getClickedBlock().getLocation());
			}
			e.setCancelled(true);
		}
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
