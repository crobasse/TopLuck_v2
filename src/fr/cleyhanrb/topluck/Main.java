package fr.cleyhanrb.topluck;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import fr.cleyhanrb.topluck.commands.FlyCommand;
import fr.cleyhanrb.topluck.commands.FreezeCommand;
import fr.cleyhanrb.topluck.commands.GodCommand;
import fr.cleyhanrb.topluck.commands.InventoryCommand;
import fr.cleyhanrb.topluck.commands.TopLuckCommand;


public class Main extends JavaPlugin {

	private File customConfigFile;
    private FileConfiguration customConfig;
	public TopLuckRegister register;
    
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		createCustomConfig("topluck.yml");
		register = new TopLuckRegister(this);
		
		getCommand("topluck").setExecutor(new TopLuckCommand(this));
		getCommand("fly").setExecutor(new FlyCommand(this));
		getCommand("god").setExecutor(new GodCommand(this));
		
		getCommand("ec").setExecutor(new InventoryCommand());
		getCommand("inv").setExecutor(new InventoryCommand());
		getCommand("freeze").setExecutor(new FreezeCommand(this));
		getCommand("unfreeze").setExecutor(new FreezeCommand(this));
		
		getServer().getPluginManager().registerEvents(new Listeners(this), this);
	}
	
	public FileConfiguration getCustomConfig() {
        return this.customConfig;
    }
	
	public void saveCustomConfig() {
		try {
			customConfig.save(customConfigFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    private void createCustomConfig(String filename) {
        customConfigFile = new File(getDataFolder(), filename);
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            saveResource(filename, false);
         }

        customConfig= new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
	
}
