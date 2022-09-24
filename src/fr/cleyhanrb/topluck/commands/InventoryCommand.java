package fr.cleyhanrb.topluck.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InventoryCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if (sender instanceof Player) {			
			if (msg.toLowerCase().contains("ec")) {
				if (args.length == 0) {
					Player player = (Player)sender;
					player.openInventory(player.getEnderChest());
					
				}else if(args.length == 1){
					Player player = Bukkit.getPlayer(args[0]);				
					if (player != null) {
						((Player)sender).openInventory(player.getEnderChest());
					}else {
						((Player)sender).sendMessage("The player is not connected");
					}
				}
			}else if (msg.toLowerCase().contains("inv")) {
				if (args.length == 0) {
					Player player = (Player)sender;
					player.openInventory(player.getInventory());
					
				}else if(args.length == 1){
					Player player = Bukkit.getPlayer(args[0]);				
					if (player != null) {
						((Player)sender).openInventory(player.getInventory());
					}else {
						((Player)sender).sendMessage("The player is not connected");
					}
				}
			}
			
		}
		
		return false;
	}

}
