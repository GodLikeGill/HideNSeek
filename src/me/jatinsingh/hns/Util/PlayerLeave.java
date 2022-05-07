package me.jatinsingh.hns.Util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.potion.PotionEffect;

import me.jatinsingh.hns.HnsInfo;
import me.jatinsingh.hns.Main;
import me.jatinsingh.hns.InventorySave.InvSave;

public class PlayerLeave {
	
	private Main plugin;
	private InvSave invSave;
	
	public PlayerLeave(Main plugin) {
		this.plugin = plugin;
		invSave = new InvSave(this.plugin);
	}
	
	HashMap<UUID, PermissionAttachment> perms = new HashMap<UUID, PermissionAttachment>();
	
	public void PlayerLeaveTask(Player p) {
		
		if(!HnsInfo.getPlayers().contains(p.getName())) {
			p.sendMessage(plugin.prefix + color("&cYou are not in HnS Queue."));
			return;
		}
		
		World w = Bukkit.getServer().getWorld(plugin.getConfig().getString("Warps.SignLobby.world"));
        double x = plugin.getConfig().getDouble("Warps.SignLobby.x");
        double y = plugin.getConfig().getDouble("Warps.SignLobby.y");
        double z = plugin.getConfig().getDouble("Warps.SignLobby.z");
        p.teleport(new Location(w, x, y, z));
        
        PermissionAttachment attachment = p.addAttachment(plugin);
		perms.put(p.getUniqueId(), attachment);
		perms.get(p.getUniqueId()).unsetPermission("hns.playing");
        
        for(PotionEffect effect : p.getActivePotionEffects()) {
        	p.removePotionEffect(effect.getType());
        }
        p.getInventory().clear();
        
        File f = new File(plugin.getDataFolder().getAbsolutePath(), "data.yml");
        FileConfiguration c = YamlConfiguration.loadConfiguration(f);
        
        if(c.contains("inventory." + p.getName())) {
        	try {
				invSave.restoreInventory(p);
				c.set("inventory." + p.getName(), null);
				c.save(f);
			} catch (IOException e) {
				p.sendMessage(plugin.prefix + color("&cThere was an error restoring your inventory."));
				e.printStackTrace();
			}
        }
	}
	
	public void PlayerLeaveCommand(Player p) {
		
		if(!HnsInfo.getPlayers().contains(p.getName())) {
			p.sendMessage(plugin.prefix + color("&cYou are not in HnS Queue."));
			return;
		}
		
		World w = Bukkit.getServer().getWorld(plugin.getConfig().getString("Warps.SignLobby.world"));
        double x = plugin.getConfig().getDouble("Warps.SignLobby.x");
        double y = plugin.getConfig().getDouble("Warps.SignLobby.y");
        double z = plugin.getConfig().getDouble("Warps.SignLobby.z");
        p.teleport(new Location(w, x, y, z));
        
        PermissionAttachment attachment = p.addAttachment(plugin);
		perms.put(p.getUniqueId(), attachment);
		perms.get(p.getUniqueId()).unsetPermission("hns.playing");
        
        for(PotionEffect effect : p.getActivePotionEffects()) {
        	p.removePotionEffect(effect.getType());
        }
        p.getInventory().clear();
        
        File f = new File(plugin.getDataFolder().getAbsolutePath(), "data.yml");
        FileConfiguration c = YamlConfiguration.loadConfiguration(f);
        
        if(c.contains("inventory." + p.getName())) {
        	try {
				invSave.restoreInventory(p);
				c.set("inventory." + p.getName(), null);
				c.save(f);
			} catch (IOException e) {
				p.sendMessage(plugin.prefix + color("&cThere was an error restoring your inventory."));
				e.printStackTrace();
			}
        }
        
        HnsInfo.getPlayers().remove(p.getName());
        
        if(HnsInfo.getVoters().contains(p.getName()))
            HnsInfo.getVoters().remove(p.getName());
            
        if(HnsInfo.getHiders().contains(p.getName()))
            HnsInfo.getHiders().remove(p.getName());
        
        if(HnsInfo.getSeekers().contains(p.getName()))
            HnsInfo.getSeekers().remove(p.getName());
        
        if(HnsInfo.getSpectators().contains(p.getName()))
            HnsInfo.getSpectators().remove(p.getName());
	}
	
	private String color(String s) {
	    return ChatColor.translateAlternateColorCodes('&', s);
	}
}