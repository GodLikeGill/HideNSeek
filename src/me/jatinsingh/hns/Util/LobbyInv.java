package me.jatinsingh.hns.Util;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LobbyInv {
	
	public void LobbyInventory(Player p) {
		
		ItemStack shop = new ItemStack(Material.EMERALD, 1);
		ItemMeta shopMeta = shop.getItemMeta();
		shopMeta.setDisplayName(color("&aShop"));
		shopMeta.setLore(Arrays.asList(color("&7Right click me to open Shop.")));
		shop.setItemMeta(shopMeta);
		
		ItemStack vote = new ItemStack(Material.DIAMOND, 1);
		ItemMeta voteMeta = vote.getItemMeta();
		voteMeta.setDisplayName(color("&aVote"));
		voteMeta.setLore(Arrays.asList(color("&7Right click me to vote to start game.")));
		vote.setItemMeta(voteMeta);
		
		ItemStack leave = new ItemStack(Material.PAPER, 1);
		ItemMeta leaveMeta = leave.getItemMeta();
		leaveMeta.setDisplayName(color("&cLeave Queue"));
		leaveMeta.setLore(Arrays.asList(color("&7Right click me to leave the queue.")));
		leave.setItemMeta(leaveMeta);
		
		p.getInventory().clear();
		
		p.getInventory().setItem(0, shop);
		p.getInventory().setItem(1, vote);
		p.getInventory().setItem(8, leave);
	}
	
	private String color(String s) {
	    return ChatColor.translateAlternateColorCodes('&', s);
	}
}