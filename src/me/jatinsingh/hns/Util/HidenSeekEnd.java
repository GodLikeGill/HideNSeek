package me.jatinsingh.hns.Util;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import me.jatinsingh.hns.HnsInfo;
import me.jatinsingh.hns.Main;

public class HidenSeekEnd {
	
	HashMap<UUID, PermissionAttachment> perms = new HashMap<UUID, PermissionAttachment>();
	
	private Main plugin;
	private PlayerLeave playerLeave;
	
	public HidenSeekEnd(Main plugin) {
		this.plugin = plugin;
		playerLeave = new PlayerLeave(this.plugin);
	}
	
	public void HnsEnd(String winner) {
		
		for(String s : HnsInfo.getPlayers()) {
			Player p = (Player) Bukkit.getPlayer(s);
			playerLeave.PlayerLeaveTask(p);
		}
		
		if(winner.contains("Seekers")) {
			for(String s : HnsInfo.getSeekers()) {
				
				Player p = (Player) Bukkit.getPlayer(s);
				
				p.sendMessage(plugin.prefix + color(plugin.getConfig().getString("Rewards.Seekers.Message")));
				
				for(String key : plugin.getConfig().getConfigurationSection("Rewards.Seekers.Commands").getKeys(false)) {
					String cmd1 = plugin.getConfig().getString("Rewards.Seekers.Commands." + key);
					String cmd2 = cmd1.replace("%player%", s);
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd2);
				}
			}
		}
		
		if(winner.contains("Hiders")) {
			for(String s : HnsInfo.getHiders()) {
				
				Player p = (Player) Bukkit.getPlayer(s);
				
				p.sendMessage(color(plugin.prefix + plugin.getConfig().getString("Rewards.Hiders.Message")));
				
				for(String key : plugin.getConfig().getConfigurationSection("Rewards.Hiders.Commands").getKeys(false)) {
					String cmd1 = plugin.getConfig().getString("Rewards.Hiders.Commands." + key);
					String cmd2 = cmd1.replace("%player%", s);
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd2);
				}
			}
		}
		
		HnsInfo.getHiders().clear();
		HnsInfo.getSeekers().clear();
		HnsInfo.getSpectators().clear();
		HnsInfo.getPlayers().clear();
		HnsInfo.getVoters().clear();
		HnsInfo.setHnSCountdown(false);
		HnsInfo.setHnS(false);
	}
	
	private String color(String s) {
	    return ChatColor.translateAlternateColorCodes('&', s);
	}
}