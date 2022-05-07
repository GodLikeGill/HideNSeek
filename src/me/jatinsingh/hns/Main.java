package me.jatinsingh.hns;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import me.jatinsingh.hns.Commands.HnS;
import me.jatinsingh.hns.Listeners.LobbyListener;
import me.jatinsingh.hns.Listeners.PlayerQuitListener;
import me.jatinsingh.hns.Listeners.PreCommandListener;
import me.jatinsingh.hns.Listeners.ShopListener;
import me.jatinsingh.hns.Listeners.SignListener;
import me.jatinsingh.hns.Listeners.TagStickListener;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin {
	
	public String prefix = color("&c[&eHnS&c]&r ");
	
	public static Economy economy = null;
	private HnS command1 = new HnS(this);
	
	public void onEnable() {
		
		if(!setupEconomy()) {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "No Economy set!");
		}
		
		if(getConfig().getName() != "config.yml") {
			getConfig().options().copyDefaults(true);
			saveDefaultConfig();
		}
		
		File f = new File(getDataFolder().getAbsolutePath(), "data.yml");
		
		if(!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		getCommand(command1.cmd1).setExecutor(command1);
		
		Bukkit.getPluginManager().registerEvents(new ShopListener(this), this);
		Bukkit.getPluginManager().registerEvents(new SignListener(this), this);
		Bukkit.getPluginManager().registerEvents(new LobbyListener(this), this);
		Bukkit.getPluginManager().registerEvents(new TagStickListener(this), this);
		Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(this), this);
		Bukkit.getPluginManager().registerEvents(new PreCommandListener(this), this);
	}
		
	private boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }
        return (economy != null);
    }
	
	private String color(String s) {
	    return ChatColor.translateAlternateColorCodes('&', s);
	}
}