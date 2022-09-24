package fr.cleyhanrb.topluck.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.cleyhanrb.topluck.Main;

public class TopLuckCommand implements CommandExecutor {

	private Main main;
	
	public TopLuckCommand(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		Player tmpPlayer = (Player)sender;
		if (tmpPlayer instanceof Player) {
			if (args.length == 0) {
				main.register.Update();
				Player player = (Player)sender;
				Inventory inv = Bukkit.createInventory(null, 54, main.getConfig().getString("topluck.name").replace("&", "§"));
				
//				Collection<? extends Player> players = Bukkit.getOnlinePlayers();
//				
//				for (Player player2 : players) {
//					inv.addItem(getPlayerItem(player2.getDisplayName()));
//					if (player2.getGameMode() == GameMode.SURVIVAL || player2.getGameMode() == GameMode.ADVENTURE) {
//					}
//				}
				
				main.register.initMenu();
				main.register.loadPage(0, inv);
				main.register.loadButtons(inv);
				
				player.openInventory(inv);
			}else {
				
				if (args.length == 1) {
					if (args[0].equalsIgnoreCase("reset")) {
						main.getCustomConfig().set("players", "");
						for (Player player : Bukkit.getOnlinePlayers()) {
							
							main.getCustomConfig().set("players." + player.getDisplayName() + ".Blocks.blocks", 0);
							main.getCustomConfig().set("players." + player.getDisplayName() + ".Blocks.cobble", 0);
							main.getCustomConfig().set("players." + player.getDisplayName() + ".Blocks.diamond", 0);
							main.getCustomConfig().set("players." + player.getDisplayName() + ".Blocks.gold", 0);
							main.getCustomConfig().set("players." + player.getDisplayName() + ".Blocks.emerald", 0);
							main.getCustomConfig().set("players." + player.getDisplayName() + ".Blocks.iron", 0);
							main.getCustomConfig().set("players." + player.getDisplayName() + ".Blocks.lapis", 0);
							main.getCustomConfig().set("players." + player.getDisplayName() + ".Blocks.coal", 0);
							main.getCustomConfig().set("players." + player.getDisplayName() + ".Blocks.nether", 0);
							main.getCustomConfig().set("players." + player.getDisplayName() + ".Blocks.redstone", 0);
							main.saveCustomConfig();
						}
						((Player)sender).sendMessage(main.getConfig().getString("topluck.reset").replace("&", "§"));
					}
				}
				
			}
		}
		return false;
	}
	
	
	
	public static void openTopLuckPlayerMenu(Main main, Player player, String playerClicked) {
		
		Inventory inv = Bukkit.createInventory(null, 27, "§c" + playerClicked);
		inv.setItem(0, generateItem("§cTeleport To " + playerClicked, Material.ENDER_PEARL, true));
		inv.setItem(1, generateItem("§cAlert " + playerClicked, Material.PAPER, true));
		inv.setItem(2, generateItem("§cKill " + playerClicked, Material.STICK, true));
		inv.setItem(3, generateItem("§cKick " + playerClicked, Material.ENDER_EYE, true));
		inv.setItem(4, generateItem("§cOpen " + playerClicked + " Inventory", Material.CHEST, false));
		inv.setItem(5, generateItem("§cOpen " + playerClicked + " Ender Chest", Material.ENDER_CHEST, false));
		
		inv.setItem(8, generateItem("§cReturn To TopLuck List", Material.BARRIER, true));
		
		
		List<String> freezedPlayers = main.getConfig().getStringList("freezed-players");

		Player playerP = Bukkit.getPlayer(player.getName());				
		String playerName = player.getName();
		
		if (playerP != null) {
			if (freezedPlayers.contains(playerName)) {
				inv.setItem(6, generateItem("§cUnFreeze " + playerClicked, Material.GLOWSTONE, true));
			}else {
				inv.setItem(6, generateItem("§cFreeze " + playerClicked, Material.PACKED_ICE, true));
			}
		}
		
		
		int blocks = main.getCustomConfig().getInt("players." + player.getDisplayName() + ".Blocks.blocks");
        int cobble = main.getCustomConfig().getInt("players." + player.getDisplayName() + ".Blocks.stone");
        int diamond = main.getCustomConfig().getInt("players." + player.getDisplayName() + ".Blocks.diamond");
        int emerald = main.getCustomConfig().getInt("players." + player.getDisplayName() + ".Blocks.emerald");
        int gold = main.getCustomConfig().getInt("players." + player.getDisplayName() + ".Blocks.gold");
        int iron = main.getCustomConfig().getInt("players." + player.getDisplayName() + ".Blocks.iron");
        int coal = main.getCustomConfig().getInt("players." + player.getDisplayName() + ".Blocks.coal");
        int lapis = main.getCustomConfig().getInt("players." + player.getDisplayName() + ".Blocks.lapis");
        int redstone = main.getCustomConfig().getInt("players." + player.getDisplayName() + ".Blocks.redstone");
        int nether = main.getCustomConfig().getInt("players." + player.getDisplayName() + ".Blocks.nether");
		
        float percentCobble = 0;
        float percentDiamond = 0;
        float percentEmerald = 0;
        float percentGold = 0;
        float percentIron = 0;
        float percentCoal = 0;
        float percentLapis = 0;
        float percentRedstone = 0;
        float percentNether = 0;

        if (cobble>0) {percentCobble = (float)100*(float)cobble/(float)blocks;}
        if (diamond>0) {percentDiamond = (float)100*(float)diamond/(float)blocks;}
        if (emerald>0) {percentEmerald = (float)100*(float)emerald/(float)blocks;}
        if (gold>0) {percentGold = (float)100*(float)gold/(float)blocks;}
        if (iron>0) {percentIron = (float)100*(float)iron/(float)blocks;}
        if (coal>0) {percentCoal = (float)100*(float)coal/(float)blocks;}
        if (lapis>0) {percentLapis = (float)100*(float)lapis/(float)blocks;}
        if (redstone>0) {percentRedstone = (float)100*(float)redstone/(float)blocks;}
        if (nether>0) {percentNether = (float)100*(float)nether/(float)blocks;}
		
        inv.setItem(18, setItemName(generateItem("", Material.DIAMOND_ORE, true), "§b" + diamond + " diamond mined (" + percentDiamond + "%)"));
        inv.setItem(19, setItemName(generateItem("", Material.EMERALD_ORE, true), "§a" + emerald + " emerald mined (" + percentEmerald + "%)"));
        inv.setItem(20, setItemName(generateItem("", Material.GOLD_ORE, true), "§e" + gold + " gold mined (" + percentGold + "%)"));
        inv.setItem(21, setItemName(generateItem("", Material.IRON_ORE, true), "§7" + iron + " iron mined (" + percentIron + "%)"));
        inv.setItem(22, setItemName(generateItem("", Material.COAL_ORE, true), "§8" + coal + " coal mined (" + percentCoal + "%)"));
        inv.setItem(23, setItemName(generateItem("", Material.LAPIS_ORE, true), "§9" + lapis + " lapis mined (" + percentLapis + "%)"));
        inv.setItem(24, setItemName(generateItem("", Material.REDSTONE_ORE, true), "§4" + redstone + " redstone mined (" + percentRedstone + "%)"));
        inv.setItem(25, setItemName(generateItem("", Material.NETHER_QUARTZ_ORE, true), "§f" + nether + " nether quartz mined (" + percentNether + "%)"));
        inv.setItem(26, setItemName(generateItem("", Material.STONE, true), "§8" + cobble + " stone mined (" + percentCobble + "%)"));
		player.openInventory(inv);
		
	}
	
	private static ItemStack setItemName(ItemStack item, String text) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(text);
		item.setItemMeta(meta);
		return item;
	}
	
	private static ItemStack generateItem(String name, Material material, boolean enchanted) {
		ItemStack it = new ItemStack(material);
		ItemMeta meta = it.getItemMeta();
		meta.setDisplayName(name);
		if (enchanted) {
			meta.addEnchant(EnchantmentWrapper.DIG_SPEED, 0, true);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		it.setItemMeta(meta);
		return it;
	}
	
	

}
