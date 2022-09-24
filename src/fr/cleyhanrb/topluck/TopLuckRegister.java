package fr.cleyhanrb.topluck;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import fr.cleyhanrb.topluck.enums.TopLuckItem;

@SuppressWarnings("deprecation")
public class TopLuckRegister {

	private ArrayList<ArrayList<Player>> pages = new ArrayList<ArrayList<Player>>();
	private Map<String, TopLuckPlayer> players = new HashMap<String, TopLuckPlayer>();
	private Main main;
	private int selectedPage = 0;
	
	public TopLuckRegister(Main main) {
		this.main = main;
		Update();
	}
	
	public void Update() {
		players.clear();
		try {
			Set<String> cPlayers = main.getCustomConfig().getConfigurationSection("players").getKeys(false);
			
			for (String string : cPlayers) {
				TopLuckPlayer player = new TopLuckPlayer(string);
				
				player.setData(TopLuckItem.BLOCKS, getCount("players." + string + ".Blocks.blocks"));
				player.setData(TopLuckItem.COAL, getCount("players." + string + ".Blocks.coal"));
				player.setData(TopLuckItem.COBBLE, getCount("players." + string + ".Blocks.stone"));
				player.setData(TopLuckItem.DIAMOND, getCount("players." + string + ".Blocks.diamond"));
				player.setData(TopLuckItem.EMERALD, getCount("players." + string + ".Blocks.emerald"));
				player.setData(TopLuckItem.GOLD, getCount("players." + string + ".Blocks.gold"));
				player.setData(TopLuckItem.IRON, getCount("players." + string + ".Blocks.iron"));
				player.setData(TopLuckItem.LAPIS, getCount("players." + string + ".Blocks.lapis"));
				player.setData(TopLuckItem.NETHER, getCount("players." + string + ".Blocks.nether"));
				player.setData(TopLuckItem.REDSTONE, getCount("players." + string + ".Blocks.redstone"));
				
				players.put(string, player);
			}
			
		} catch (Exception e){}
	}
	
	public void loadPage(int page, Inventory inv) {
		
		inv.clear();
		
		selectedPage = page;
		for (int i = 0; i < pages.get(page).size(); i++) {
			Player player = pages.get(page).get(i);
			inv.setItem(i, getPlayerItem(player.getDisplayName()));
		}
		
	}
	
	public void loadButtons(Inventory inv) {
		try {
			if(selectedPage > 0) {
				if (pages.get(selectedPage -1) != null) {
					inv.setItem(45, generateItem("Page " + (selectedPage), Material.ARROW, false));
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {}
		try {
			if (pages.get(selectedPage + 1) != null) {
				inv.setItem(53, generateItem("Page " + (selectedPage + 2), Material.ARROW, false));
			}
		} catch (IndexOutOfBoundsException e) {}
		
	}
	
	
	public void initMenu() {
		
		Collection<? extends Player> playersA = Bukkit.getOnlinePlayers();
		ArrayList<Player> players = new ArrayList<Player>(playersA);
		pages.clear();
		
//		for (int i = 0; i < 64; i++) {
//			players.add(Bukkit.getPlayer("Omega_Trash"));
//		}
		
		int i = 0;
		ArrayList<Player> page = new ArrayList<Player>();
		for (Player player : players) {
			if (i < 45) {
				if (main.getConfig().getBoolean("topluck.show-creative-players") == false) {
					if (player.getGameMode() != GameMode.CREATIVE && player.getGameMode() != GameMode.SPECTATOR) {
						page.add(player);
						i++;						
					}
				}else {
					page.add(player);
					i++;	
				}
			}else {
				i = 0;
				pages.add(page);
				page = new ArrayList<Player>();
			}
		}
		i = 0;
		pages.add(page);
		page = new ArrayList<Player>();
		
	}
	
	
	private ItemStack getPlayerItem(String pseudo) {
		
		ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1, (short)SkullType.PLAYER.ordinal());
		SkullMeta meta = (SkullMeta) skull.getItemMeta();
		
		meta.setOwningPlayer(Bukkit.getPlayer(pseudo));
		meta.setDisplayName("§c" + pseudo);
		skull.setItemMeta(meta);
		
		skull.setItemMeta(main.register.configMeta(skull.getItemMeta(), pseudo));
		
		return skull;
	}
	
	private ItemStack generateItem(String name, Material material, boolean enchanted) {
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
	
	public ItemMeta configMeta(ItemMeta meta, String pseudo) {
		int blocks = players.get(pseudo).getData(TopLuckItem.BLOCKS);
		int cobble = players.get(pseudo).getData(TopLuckItem.COBBLE);
		int diamond = players.get(pseudo).getData(TopLuckItem.DIAMOND);
		int emerald = players.get(pseudo).getData(TopLuckItem.EMERALD);
		int gold = players.get(pseudo).getData(TopLuckItem.GOLD);
		int iron = players.get(pseudo).getData(TopLuckItem.IRON);
		int coal = players.get(pseudo).getData(TopLuckItem.COAL);
		int lapis = players.get(pseudo).getData(TopLuckItem.LAPIS);
		int redstone = players.get(pseudo).getData(TopLuckItem.REDSTONE);
		int nether = players.get(pseudo).getData(TopLuckItem.NETHER);
		
		float percent = 0;
		if (blocks>0) {
			percent = (float)100*(float)diamond/(float)blocks;
		}
		
		meta.setLore(Arrays.asList(
				percent + "%",
				"Blocks: " + blocks,				
				"§8Stone: " + cobble,		
				"§bDiamonds: " + diamond,				
				"§aEmeralds: " + emerald,				
				"§eGold: " + gold,				
				"§7Iron: " + iron,				
				"§8Coal: " + coal,				
				"§4Redstone: " + redstone,				
				"§9Lapis: " + lapis,				
				"§fNether Quartz: " + nether		
				));
		
		return meta;
	}
	
	private int getCount(String path) {
		return main.getCustomConfig().getInt(path);
	}
	
}
