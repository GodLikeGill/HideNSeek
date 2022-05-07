package me.jatinsingh.hns.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.jatinsingh.hns.Main;
import me.jatinsingh.hns.LobbyTP.HideNSeekLobby;
import me.jatinsingh.hns.LobbyTP.QueueLobby;
import me.jatinsingh.hns.LobbyTP.SignLobby;
import me.jatinsingh.hns.LobbyTP.SpectatorLobby;
import me.jatinsingh.hns.Util.HidenSeekEnd;
import me.jatinsingh.hns.Util.PlayerLeave;

public class HnS implements CommandExecutor {
	
	public String cmd1 = "hns";
	
	private Main plugin;
	private HidenSeekEnd hnsEnd;
	private QueueLobby queueLobby;
	private SpectatorLobby spectatorLobby;
	private SignLobby signLobby;
	private HideNSeekLobby hnsLobby;
	private PlayerLeave playerLeave;
	
	public HnS(Main plugin) {
		this.plugin = plugin;
		hnsEnd = new HidenSeekEnd(this.plugin);
		hnsLobby = new HideNSeekLobby(this.plugin);
		queueLobby = new QueueLobby(this.plugin);
		spectatorLobby = new SpectatorLobby(this.plugin);
		signLobby = new SignLobby(this.plugin);
		playerLeave = new PlayerLeave(this.plugin);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase(cmd1)) {
			if(sender instanceof Player) {
				
				Player p = (Player) sender;
				
				if(args.length == 0) {
					p.sendMessage(color("&7---------------- &r&aHNS &7----------------"
							+           "\n&e    Hide and Seek plugin by Jatinsingh."
							+           "\n&7------------------------------------"));
				}
				
				if(args.length == 1) {
					
					switch(args[0].toLowerCase()) {
					
					case "quit":
						playerLeave.PlayerLeaveCommand(p);
						break;
					
					case "setqueuelobby":
						if(p.hasPermission("hns.admin")) {
							queueLobby.SetQueueLobby(p);
							break;
						}
						else {
							p.sendMessage(plugin.prefix + color("&cYou don't have permission to use this."));
							break;
						}
						
					case "setspeclobby":
						if(p.hasPermission("hns.admin")) {
							spectatorLobby.SetSpectatorLobby(p);
							break;
						}
						else {
							p.sendMessage(plugin.prefix + color("&cYou don't have permission to use this."));
							break;
						}
						
					case "setsignlobby":
						if(p.hasPermission("hns.admin")) {
							signLobby.SetSignLobby(p);
							break;
						}
						else {
							p.sendMessage(plugin.prefix + color("&cYou don't have permission to use this."));
							break;
						}
						
					case "sethnslobby":
						if(p.hasPermission("hns.admin")) {
							hnsLobby.SetHnsLobby(p);
							break;
						}
						else {
							p.sendMessage(plugin.prefix + color("&cYou don't have permission to use this."));
							break;
						}
						
					case "reload":
						if(p.hasPermission("hns.admin")) {
							plugin.reloadConfig();
							plugin.saveConfig();
							p.sendMessage(plugin.prefix + color("&aConfig file has been reloaded successfully."));
							break;
						}
						else {
							p.sendMessage(plugin.prefix + color("&cYou don't have permission to use this."));
							break;
						}
						
					case "debug":
						if(p.hasPermission("hns.admin")) {
							hnsEnd.HnsEnd("");
							p.sendMessage("The Hide n Seek plugin has been debugged.");
							break;
						}
						else {
							p.sendMessage(plugin.prefix + color("&cYou don't have permission to use this."));
							break;
						}
						
					case "help":
						
						if(p.hasPermission("hns.admin")) {
							
							p.sendMessage(color("&a/hns setsignlobby"
									+ "\n&a/hns setqueuelobby"
									+ "\n&a/hns sethnslobby"
									+ "\n&a/hns setspeclobby"));
							break;
						}
						else{
							p.sendMessage(plugin.prefix + color("&cYou don't have permission to use this."));
							break;
						}
						
					default:
						p.sendMessage(plugin.prefix + color("&cWrong Sub-Command. Do '/hns help' for info."));
						break;
					}
				}
			}
		}
		return true;
	}
	
	private String color(String s) {
	    return ChatColor.translateAlternateColorCodes('&', s);
	}
}