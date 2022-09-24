package fr.cleyhanrb.topluck.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.cleyhanrb.topluck.Main;

public class GodCommand implements CommandExecutor{

	private Main main;
	
	public GodCommand(Main main) {
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player)sender;
			
			if (args.length == 0) {
				player.setInvulnerable(!player.isInvulnerable());
				if (player.isInvulnerable()) {
					player.sendMessage(main.getConfig().getString("utils.god.enabled").replace("%player%", player.getDisplayName()).replace("&", "§"));
				}else{
					player.sendMessage(main.getConfig().getString("utils.god.disabled").replace("%player%", player.getDisplayName()).replace("&", "§"));
				}
			}else if(args.length == 1) {
				if (args[0].equalsIgnoreCase("on")) {
					player.setInvulnerable(true);
					player.sendMessage(main.getConfig().getString("utils.god.enabled").replace("%player%", player.getDisplayName()).replace("&", "§"));
				}else if(args[0].equalsIgnoreCase("off")) {
					player.setInvulnerable(false);
					player.sendMessage(main.getConfig().getString("utils.god.disabled").replace("%player%", player.getDisplayName()).replace("&", "§"));
				}else {
					player.sendMessage("§cError args: §6/god [on/off]");					
				}
			}else if(args.length == 2) {
				if (Bukkit.getPlayer(args[1]) != null) {
					Player targetPlayer = Bukkit.getPlayer(args[1]);
					if (args[0].equalsIgnoreCase("on")) {
						targetPlayer.setInvulnerable(true);
						player.sendMessage(main.getConfig().getString("utils.god.enabled").replace("%player%", targetPlayer.getDisplayName()).replace("&", "§"));
					}else if(args[0].equalsIgnoreCase("off")) {
						targetPlayer.setInvulnerable(false);
						player.sendMessage(main.getConfig().getString("utils.god.disabled").replace("%player%", targetPlayer.getDisplayName()).replace("&", "§"));
					}else {
						player.sendMessage("§cError args: §6/god [on/off]");					
					}
				}else {
					player.sendMessage("§c" + args[1] + " is not connected");					
				}
			}else {
				player.sendMessage("§cError args: §6/god [on/off]");
			}
		}		
		return false;
	}

}
