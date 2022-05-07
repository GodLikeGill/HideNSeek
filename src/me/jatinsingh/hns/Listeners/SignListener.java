package me.jatinsingh.hns.Listeners;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.PermissionAttachment;

import me.jatinsingh.hns.HnsInfo;
import me.jatinsingh.hns.Main;
import me.jatinsingh.hns.InventorySave.InvSave;
import me.jatinsingh.hns.Util.HidenSeekStart;
import me.jatinsingh.hns.Util.LobbyInv;

public class SignListener implements Listener {
	
	HashMap<UUID, PermissionAttachment> perms = new HashMap<UUID, PermissionAttachment>();
	
	private Main plugin;
	private InvSave invSave;
	private LobbyInv lobbyInv;
	private HidenSeekStart hnsStart;
	
	public SignListener(Main plugin) {
		this.plugin = plugin;
		invSave = new InvSave(this.plugin);
		lobbyInv = new LobbyInv();
		hnsStart = new HidenSeekStart(this.plugin);
	}
	
	@EventHandler
	public void onSignChange(SignChangeEvent e) {
		if(e.getLine(0).equals("[HideNSeek]")) {
			
			if(plugin.getConfig().isConfigurationSection("Warps.QueueLobby") && plugin.getConfig().isConfigurationSection("Warps.SignLobby") && plugin.getConfig().isConfigurationSection("Warps.SpectatorLobby") && plugin.getConfig().isConfigurationSection("Warps.HideNSeekLobby")) {
				e.setLine(0, "§m----------------");
				e.setLine(1, "HideAndSeek");
				e.setLine(2, "[§2" + HnsInfo.getPlayers().size() + "/20§0] Joined");
				e.setLine(3, "§m----------------");
				e.getPlayer().sendMessage(plugin.prefix + color("&aHide and Seek Sign placed."));
				return;
			}
			
			e.setCancelled(true);
			e.getPlayer().sendMessage(plugin.prefix + "Set all warps before placing Sign.");
		}
	}
	
	@EventHandler
	public void onSignClick(PlayerInteractEvent e) {
		
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			
			if(e.getClickedBlock().getState() instanceof Sign) {
				
				Sign sign = (Sign) e.getClickedBlock().getState();
				
				if(sign.getLine(0).equalsIgnoreCase("§m----------------") 
						&& sign.getLine(1).equalsIgnoreCase("HideAndSeek") 
						&&  sign.getLine(3).equalsIgnoreCase("§m----------------")) {
					
					Player p = (Player) e.getPlayer();
					
					if(!p.hasPermission("hns.participate")) {
						p.sendMessage(plugin.prefix + color("&cYou don't have permission to use this."));
						return;
					}
					
					if(!HnsInfo.isHnS()) {
						
						if(HnsInfo.getPlayers().contains(p.getName())) {
							p.sendMessage(plugin.prefix + color("&cYou are already in the queue."));
							return;
						}
						
						p.getInventory().setHeldItemSlot(2);
						HnsInfo.getPlayers().add(p.getName());
						e.getPlayer().sendMessage(color("&aYou are added in Hide n Seek queue."));
						
						PermissionAttachment attachment = p.addAttachment(plugin);
						perms.put(p.getUniqueId(), attachment);
						PermissionAttachment pperms = perms.get(p.getUniqueId());
						pperms.setPermission("hns.playing", true);
						
						sign.setLine(2, "[§2" + HnsInfo.getPlayers().size() + "/20§0] Joined");
						sign.update();
						
						if(!(isInvEmpty(p))) {
							try {
								invSave.saveInventory(p);
							} catch (IOException e1) {
								p.sendMessage(plugin.prefix + color("&cThere was an error saving your inventory."));
								e1.printStackTrace();
							}
						}
						
						lobbyInv.LobbyInventory(p);
						
						World w = Bukkit.getServer().getWorld(plugin.getConfig().getString("Warps.QueueLobby.world"));
	                    double x = plugin.getConfig().getDouble("Warps.QueueLobby.x");
	                    double y = plugin.getConfig().getDouble("Warps.QueueLobby.y");
	                    double z = plugin.getConfig().getDouble("Warps.QueueLobby.z");
	                    p.teleport(new Location(w, x, y, z));
	                    
	                    if(HnsInfo.getPlayers().size() >= plugin.getConfig().getInt("MinimumPlayers"))
	                    	hnsStart.hnsStart();
					}
					else {
						p.sendMessage(plugin.prefix + color("&cHide N Seek has already started, wait for it to end."));
						return;
					}
				}
			}
		}		
	}
	
	private boolean isInvEmpty(Player p) {
		for(ItemStack item : p.getInventory().getContents()) {
			if(item != null) {
				return false;
			}
		}
		for(ItemStack armor : p.getInventory().getArmorContents()) {
			if(armor != null) {
				return false;
			}
		}
		return true;
	}
	
	private String color(String s) {
	    return ChatColor.translateAlternateColorCodes('&', s);
	}
}