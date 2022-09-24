package fr.cleyhanrb.topluck;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.cleyhanrb.topluck.commands.TopLuckCommand;

@SuppressWarnings("deprecation")
public class Listeners implements Listener {

	private Main main;
	private Inventory TopLuckInv;
	
	public Listeners(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		
		List<String> players = main.getConfig().getStringList("freezed-players");
		
		if (players.contains(e.getPlayer().getDisplayName())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onMenuClick(InventoryClickEvent e) {
		
		Inventory inv = e.getClickedInventory();
		Player player = (Player)e.getWhoClicked();
		ItemStack it = e.getCurrentItem();	
		
		if (it != null && inv != null && e.getView().getTitle().contains("§c")) {
			e.setCancelled(true);
			if (it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getType() == Material.PLAYER_HEAD) {
				TopLuckInv = inv; 
				TopLuckCommand.openTopLuckPlayerMenu(main, player, it.getItemMeta().getDisplayName());
			}else if (it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getType() == Material.ENDER_PEARL) {
				String pseudo = ChatColor.stripColor(it.getItemMeta().getDisplayName().split(" ")[2].toString());
				player.teleport(Bukkit.getPlayer(pseudo));
			}else if (it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getType() == Material.PAPER) {
				String pseudo = ChatColor.stripColor(it.getItemMeta().getDisplayName().split(" ")[1].toString());
				
				Player p = Bukkit.getPlayer(pseudo);
				p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 255), true);
				p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 255), true);
				p.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, p.getLocation(), 1000);
				p.sendTitle(main.getConfig().getString("topluck.spotted.title").replace("&", "§"),
						main.getConfig().getString("topluck.spotted.subtitle").replace("&", "§"), 1, 20, 1);
			}else if (it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getType() == Material.STICK) {
				String pseudo = ChatColor.stripColor(it.getItemMeta().getDisplayName().split(" ")[1].toString());
				
				Player p = Bukkit.getPlayer(pseudo);
				p.setHealth(0.0D);
			}else if (it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getType() == Material.ENDER_EYE) {
				String pseudo = ChatColor.stripColor(it.getItemMeta().getDisplayName().split(" ")[1].toString());
				Player p = Bukkit.getPlayer(pseudo);
				
				p.kickPlayer("§cYou have been kicked by " + player.getDisplayName());
			}else if (it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getType() == Material.ARROW) {
				int page = Integer.parseInt(it.getItemMeta().getDisplayName().split(" ")[1]);
				
				main.register.loadPage(page - 1, inv);
				main.register.loadButtons(inv);
			}else if(it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getType() == Material.BARRIER) {
				main.register.loadPage(0, TopLuckInv);
				main.register.loadButtons(TopLuckInv);
				player.openInventory(TopLuckInv);
			}else if(it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getType() == Material.CHEST) {
				String pseudo = ChatColor.stripColor(it.getItemMeta().getDisplayName().split(" ")[1].toString());
				Player p = Bukkit.getPlayer(pseudo);
				player.openInventory(p.getInventory());
			}else if(it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getType() == Material.ENDER_CHEST) {
				String pseudo = ChatColor.stripColor(it.getItemMeta().getDisplayName().split(" ")[1].toString());
				Player p = Bukkit.getPlayer(pseudo);
				player.openInventory(p.getEnderChest());
			}
			else if(it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getType() == Material.PACKED_ICE) {
				String pseudo = ChatColor.stripColor(it.getItemMeta().getDisplayName().split(" ")[1].toString());
//				Player p = Bukkit.getPlayer(pseudo);
				Bukkit.dispatchCommand(player, "freeze " + pseudo);
				TopLuckCommand.openTopLuckPlayerMenu(main, player,pseudo);
			}else if(it.hasItemMeta() && it.getItemMeta().hasDisplayName() && it.getType() == Material.GLOWSTONE) {
				String pseudo = ChatColor.stripColor(it.getItemMeta().getDisplayName().split(" ")[1].toString());
//				Player p = Bukkit.getPlayer(pseudo);
				Bukkit.dispatchCommand(player, "unfreeze " + pseudo);
				TopLuckCommand.openTopLuckPlayerMenu(main, player,pseudo);
			}
		}
		
	}
	
	@EventHandler
	public void onMessage(PlayerChatEvent e) {
		List<String> players = main.getConfig().getStringList("freezed-players");
		
		if (players.contains(e.getPlayer().getDisplayName())) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(main.getConfig().getString("utils.freeze.cant-chat-message").replace("&", "§"));
		}else {
			if (main.getConfig().getBoolean("utils.chat.default") == false) {
				e.setCancelled(true);
				Bukkit.broadcastMessage(main.getConfig().getString("utils.chat.custom").replace("%player%", e.getPlayer().getDisplayName()).replace("%message%", e.getMessage()).replace("&", "§"));							
			}
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		if (main.getCustomConfig().getConfigurationSection("players." + e.getPlayer().getDisplayName() + ".Blocks") == null) {
			main.getCustomConfig().set("players." + e.getPlayer().getDisplayName() + ".Blocks.blocks", 0);
			main.getCustomConfig().set("players." + e.getPlayer().getDisplayName() + ".Blocks.stone", 0);
			main.getCustomConfig().set("players." + e.getPlayer().getDisplayName() + ".Blocks.diamond", 0);
			main.getCustomConfig().set("players." + e.getPlayer().getDisplayName() + ".Blocks.gold", 0);
			main.getCustomConfig().set("players." + e.getPlayer().getDisplayName() + ".Blocks.emerald", 0);
			main.getCustomConfig().set("players." + e.getPlayer().getDisplayName() + ".Blocks.iron", 0);
			main.getCustomConfig().set("players." + e.getPlayer().getDisplayName() + ".Blocks.lapis", 0);
			main.getCustomConfig().set("players." + e.getPlayer().getDisplayName() + ".Blocks.coal", 0);
			main.getCustomConfig().set("players." + e.getPlayer().getDisplayName() + ".Blocks.nether", 0);
			main.getCustomConfig().set("players." + e.getPlayer().getDisplayName() + ".Blocks.redstone", 0);
			main.saveCustomConfig();
		}
	}
	
	@EventHandler
	public void onBlockBreaked(BlockBreakEvent e) {
		List<String> players = main.getConfig().getStringList("freezed-players");
		
		if (players.contains(e.getPlayer().getDisplayName())) {
			e.setCancelled(true);
		}
		
		if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
			main.getCustomConfig().set("players." + e.getPlayer().getDisplayName() + ".Blocks.blocks", 
					main.getCustomConfig().getInt("players." + e.getPlayer().getDisplayName() + ".Blocks.blocks") + 1);
			
			
			
			
			
			switch (e.getBlock().getType()) {
			case DIAMOND_ORE:
				main.getCustomConfig().set("players." + e.getPlayer().getDisplayName() + ".Blocks.diamond", 
						main.getCustomConfig().getInt("players." + e.getPlayer().getDisplayName() + ".Blocks.diamond") + 1);
				break;
			case GOLD_ORE:
				main.getCustomConfig().set("players." + e.getPlayer().getDisplayName() + ".Blocks.gold", 
						main.getCustomConfig().getInt("players." + e.getPlayer().getDisplayName() + ".Blocks.gold") + 1);
				break;
			case REDSTONE_ORE:
			case LEGACY_GLOWING_REDSTONE_ORE:
				main.getCustomConfig().set("players." + e.getPlayer().getDisplayName() + ".Blocks.redstone", 
						main.getCustomConfig().getInt("players." + e.getPlayer().getDisplayName() + ".Blocks.redstone") + 1);
				break;
			case LAPIS_ORE:
				main.getCustomConfig().set("players." + e.getPlayer().getDisplayName() + ".Blocks.lapis", 
						main.getCustomConfig().getInt("players." + e.getPlayer().getDisplayName() + ".Blocks.lapis") + 1);
				break;
			case EMERALD_ORE:
				main.getCustomConfig().set("players." + e.getPlayer().getDisplayName() + ".Blocks.emerald", 
						main.getCustomConfig().getInt("players." + e.getPlayer().getDisplayName() + ".Blocks.emerald") + 1);
				break;
			case IRON_ORE:
				main.getCustomConfig().set("players." + e.getPlayer().getDisplayName() + ".Blocks.iron", 
						main.getCustomConfig().getInt("players." + e.getPlayer().getDisplayName() + ".Blocks.iron") + 1);
				break;
			case COAL_ORE:
				main.getCustomConfig().set("players." + e.getPlayer().getDisplayName() + ".Blocks.coal", 
						main.getCustomConfig().getInt("players." + e.getPlayer().getDisplayName() + ".Blocks.coal") + 1);
				break;
			case NETHER_QUARTZ_ORE:
				main.getCustomConfig().set("players." + e.getPlayer().getDisplayName() + ".Blocks.nether", 
						main.getCustomConfig().getInt("players." + e.getPlayer().getDisplayName() + ".Blocks.nether") + 1);
				break;
			case STONE:
				main.getCustomConfig().set("players." + e.getPlayer().getDisplayName() + ".Blocks.stone", 
						main.getCustomConfig().getInt("players." + e.getPlayer().getDisplayName() + ".Blocks.stone") + 1);
				break;
			default:
				break;
			}
			main.saveCustomConfig();
		}
	}
	
	
	
}
