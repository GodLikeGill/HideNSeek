package me.jatinsingh.hns.LobbyTP;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.jatinsingh.hns.Main;

public class SpectatorLobby {
	
private Main plugin;
	
	public SpectatorLobby(Main plugin) {
		this.plugin = plugin;
	}
	
	public void SetSpectatorLobby(Player p) {
		
		plugin.getConfig().set("Warps.SpectatorLobby." + ".world" , p.getLocation().getWorld().getName());
        plugin.getConfig().set("Warps.SpectatorLobby." + ".x", p.getLocation().getX());
        plugin.getConfig().set("Warps.SpectatorLobby." + ".y", p.getLocation().getY());
        plugin.getConfig().set("Warps.SpectatorLobby." + ".z", p.getLocation().getZ());
        plugin.saveConfig();
        p.sendMessage(plugin.prefix + ChatColor.GREEN + "Spectator Lobby has been set.");
	}
}