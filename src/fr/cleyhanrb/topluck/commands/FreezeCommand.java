package fr.cleyhanrb.topluck.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.cleyhanrb.topluck.Main;

public class FreezeCommand implements CommandExecutor {

	private Main main;
	
	public FreezeCommand(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {		
		if (args.length == 1) {
			if (msg.toString().equalsIgnoreCase("freeze")) {
				
				List<String> freezedPlayers = main.getConfig().getStringList("freezed-players");

				Player player = Bukkit.getPlayer(args[0]);				
				if (player != null) {
					
					if (!freezedPlayers.contains(args[0])) {
						freezedPlayers.add(args[0]);
						main.getConfig().set("freezed-players", freezedPlayers);
						main.saveConfig();
						sender.sendMessage("§c" + args[0] + " is now freezed");
					}else {
						sender.sendMessage("§c" + args[0] + " is already freezed");
					}
					
				}else {
					((Player)sender).sendMessage("§c" + "The player is not connected");
				}
				
			}else if(msg.toString().equalsIgnoreCase("unfreeze")) {
				
				List<String> freezedPlayers = main.getConfig().getStringList("freezed-players");

				Player player = Bukkit.getPlayer(args[0]);				
				if (player != null) {
					
					if (freezedPlayers.contains(args[0])) {
						freezedPlayers.add(args[0]);
						main.getConfig().set("freezed-players", main.getConfig().getStringList("freezed-players").remove(args[0]));
						main.saveConfig();
						sender.sendMessage("§c" + args[0] + " is now unfreezed");
					}else {
						sender.sendMessage("§c" + args[0] + " is not freezed");
					}
					
				}else {
					((Player)sender).sendMessage("§c" + "The player is not connected");
				}
				
			}
		}
		
		return false;
	}
	
//	public void FreezePlayer(Player player) {
//		List<String> players = main.getConfig().getStringList("freezed-players");
//		
//		for (String playerName : players) {
//			Player GettedPlayer = Bukkit.getPlayer(playerName);
//			
//			GettedPlayer.setWalkSpeed(0);
//			GettedPlayer.setFlySpeed(0);
//		}
//	}

}
