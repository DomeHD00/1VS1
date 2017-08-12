package eu.dieytberlegion.Commands.admin;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.dieytberlegion.MYSQL.mysql;
import eu.dieytberlegion.OneVsOne.OneVsOne;
import eu.dieytberlegion.nick.Nick;

public class setLocation implements CommandExecutor {

	private OneVsOne plugin;

	public setLocation(OneVsOne Instance) {
		this.plugin = OneVsOne.getInstance();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (command.getName().equalsIgnoreCase("setLoc")) {

			if (!(sender instanceof Player)) {
				sender.sendMessage(OneVsOne.prefix + "§cNur Spieler Dürfen /setLocation nutzen");
				return true;
			}
			Player p = (Player) sender;

			if (p.hasPermission("1vs1.system.setLoc")) {
				if (args.length == 2) {
					// setloc [name] pos1
					// setloc [name] pos2
					// setloc [name] spectator

					if (args[1].equalsIgnoreCase("pos1")) {
						String name = args[0];
                        Location loc = p.getLocation();
						
						Bukkit.getScheduler().runTaskAsynchronously(OneVsOne.getInstance(), () -> {
							try {
								ResultSet pos = mysql.getResult("SELECT * FROM setup WHERE Name = '" + name + "'");
								if (pos.next()) {
                                    mysql.update("update setup set pos1X = '"+ loc.getBlockX() +"' where Name = '"+ name + "'");
                                    mysql.update("update setup set pos1Y = '"+ loc.getBlockY() +"' where Name = '"+ name + "'");
                                    mysql.update("update setup set pos1Z = '"+ loc.getBlockZ() +"' where Name = '"+ name + "'");
                                    mysql.update("update setup set pos1Yaw = '"+ loc.getYaw() +"' where Name = '"+ name + "'");
                                    mysql.update("update setup set pos1Pitch = '"+ loc.getPitch() +"' where Name = '"+ name + "'");
								}
								p.sendMessage(OneVsOne.prefix + "§aDu hast Die Position erfolgrich gesetzt!");
							} catch (SQLException e1) {
								e1.printStackTrace();
							}

						});
					}
					else if (args[1].equalsIgnoreCase("pos2")) {
						String name = args[0];
                        Location loc = p.getLocation();
						
						Bukkit.getScheduler().runTaskAsynchronously(OneVsOne.getInstance(), () -> {
							try {
								ResultSet pos = mysql.getResult("SELECT * FROM setup WHERE Name = '" + name + "'");
								if (pos.next()) {
                                    mysql.update("update setup set pos2X = '"+ loc.getBlockX() +"' where Name = '"+ name + "'");
                                    mysql.update("update setup set pos2Y = '"+ loc.getBlockY() +"' where Name = '"+ name + "'");
                                    mysql.update("update setup set pos2Z = '"+ loc.getBlockZ() +"' where Name = '"+ name + "'");
                                    mysql.update("update setup set pos2Yaw = '"+ loc.getYaw() +"' where Name = '"+ name + "'");
                                    mysql.update("update setup set pos2Pitch = '"+ loc.getPitch() +"' where Name = '"+ name + "'");
								}
								p.sendMessage(OneVsOne.prefix + "§aDu hast Die Position erfolgrich gesetzt!");
							} catch (SQLException e1) {
								e1.printStackTrace();
							}

						});
					}
					else if (args[1].equalsIgnoreCase("spectator")) {
						String name = args[0];
                        Location loc = p.getLocation();
						
						Bukkit.getScheduler().runTaskAsynchronously(OneVsOne.getInstance(), () -> {
							try {
								ResultSet pos = mysql.getResult("SELECT * FROM setup WHERE Name = '" + name + "'");
								if (pos.next()) {
                                    mysql.update("update setup set SposX = '"+ loc.getBlockX() +"' where Name = '"+ name + "'");
                                    mysql.update("update setup set SposY = '"+ loc.getBlockY() +"' where Name = '"+ name + "'");
                                    mysql.update("update setup set SposZ = '"+ loc.getBlockZ() +"' where Name = '"+ name + "'");
								}
								p.sendMessage(OneVsOne.prefix + "§aDu hast Die Position erfolgrich gesetzt!");
							} catch (SQLException e1) {
								e1.printStackTrace();
							}

						});
					}

				}else {
					p.sendMessage(OneVsOne.prefix + "§c/setLoc [name] [pos1/pos2/spectator]");
				}
			} else {
				p.sendMessage("OneVsOne.nopem");
			}
		}

		return true;
	}
}
