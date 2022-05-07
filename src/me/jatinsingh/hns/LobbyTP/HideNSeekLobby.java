package me.jatinsingh.hns.LobbyTP;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.jatinsingh.hns.Main;

public class HideNSeekLobby {
	
	private Main plugin;
	
	public HideNSeekLobby(Main plugin) {
		this.plugin = plugin;
	}
	
	public void SetHnsLobby(Player p) {
		
		plugin.getConfig().set("Warps.HideNSeekLobby." + ".world" , p.getLocation().getWorld().getName());
        plugin.getConfig().set("Warps.HideNSeekLobby." + ".x", p.getLocation().getX());
        plugin.getConfig().set("Warps.HideNSeekLobby." + ".y", p.getLocation().getY());
        plugin.getConfig().set("Warps.HideNSeekLobby." + ".z", p.getLocation().getZ());
        plugin.saveConfig();
        p.sendMessage(plugin.prefix + ChatColor.GREEN + "HideNSeek Lobby has been set.");
	}
}