package me.jatinsingh.hns.LobbyTP;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.jatinsingh.hns.Main;

public class QueueLobby {
	
	private Main plugin;
	
	public QueueLobby(Main plugin) {
		this.plugin = plugin;
	}
	
	public void SetQueueLobby(Player p) {
		
		plugin.getConfig().set("Warps.QueueLobby." + ".world" , p.getLocation().getWorld().getName());
        plugin.getConfig().set("Warps.QueueLobby." + ".x", p.getLocation().getX());
        plugin.getConfig().set("Warps.QueueLobby." + ".y", p.getLocation().getY());
        plugin.getConfig().set("Warps.QueueLobby." + ".z", p.getLocation().getZ());
        plugin.saveConfig();
        p.sendMessage(plugin.prefix + ChatColor.GREEN + "Queue Lobby has been set.");
	}
}