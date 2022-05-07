package me.jatinsingh.hns.LobbyTP;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.jatinsingh.hns.Main;

public class SignLobby {
private Main plugin;
	
	public SignLobby(Main plugin) {
		this.plugin = plugin;
	}
	
	public void SetSignLobby(Player p) {
		
		plugin.getConfig().set("Warps.SignLobby." + ".world" , p.getLocation().getWorld().getName());
        plugin.getConfig().set("Warps.SignLobby." + ".x", p.getLocation().getX());
        plugin.getConfig().set("Warps.SignLobby." + ".y", p.getLocation().getY());
        plugin.getConfig().set("Warps.SignLobby." + ".z", p.getLocation().getZ());
        plugin.saveConfig();
        p.sendMessage(plugin.prefix + ChatColor.GREEN + "Sign Lobby has been set.");
	}
}