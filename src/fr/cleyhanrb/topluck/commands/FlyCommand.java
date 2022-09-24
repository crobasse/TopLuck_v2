package fr.cleyhanrb.topluck.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.cleyhanrb.topluck.Main;

public class FlyCommand implements CommandExecutor{

	private Main main;
	
	public FlyCommand(Main main) {
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player)sender;
			
			if (args.length == 0) {
				player.setAllowFlight(!player.getAllowFlight());
				if (player.getAllowFlight()) {
					player.sendMessage(main.getConfig().getString("utils.fly.enabled").replace("%player%", player.getDisplayName()).replace("&", "§"));
				}else{
					player.sendMessage(main.getConfig().getString("utils.fly.disabled").replace("%player%", player.getDisplayName()).replace("&", "§"));
				}
			}else if(args.length == 1) {
				if (args[0].equalsIgnoreCase("on")) {
					player.setAllowFlight(true);
					player.sendMessage(main.getConfig().getString("utils.fly.enabled").replace("%player%", player.getDisplayName()).replace("&", "§"));
				}else if(args[0].equalsIgnoreCase("off")) {
					player.setAllowFlight(false);
					player.sendMessage(main.getConfig().getString("utils.fly.disabled").replace("%player%", player.getDisplayName()).replace("&", "§"));
				}else {
					player.sendMessage("§cError args: §6/fly [on/off]");					
				}
			}else if(args.length == 2) {
				if (Bukkit.getPlayer(args[1]) != null) {
					Player targetPlayer = Bukkit.getPlayer(args[1]);
					if (args[0].equalsIgnoreCase("on")) {
						targetPlayer.setAllowFlight(true);
						player.sendMessage(main.getConfig().getString("utils.fly.enabled").replace("%player%", targetPlayer.getDisplayName()).replace("&", "§"));
					}else if(args[0].equalsIgnoreCase("off")) {
						targetPlayer.setAllowFlight(false);
						player.sendMessage(main.getConfig().getString("utils.fly.disabled").replace("%player%", targetPlayer.getDisplayName()).replace("&", "§"));
					}else {
						player.sendMessage("§cError args: §6/fly [on/off]");					
					}
				}else {
					player.sendMessage("§c" + args[1] + " is not connected");					
				}
			}else {
				player.sendMessage("§cError args: §6/fly [on/off]");
			}
		}		
		return false;
	}

}
