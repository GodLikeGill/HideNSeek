package me.jatinsingh.hns.Util;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.jatinsingh.hns.Main;

public class TagStickItem {
	
	private Main plugin;
	
	public TagStickItem(Main plugin) {
		this.plugin = plugin;
	}
	
	public ItemStack TagStick() {
		
		ItemStack tagstick = new ItemStack(Material.STICK, 1);
		ItemMeta tagstickm = tagstick.getItemMeta();
		tagstickm.setDisplayName(color(plugin.getConfig().getString("TagStick.display_name")));
		tagstickm.setLore(color(plugin.getConfig().getStringList("TagStick.lore")));
		tagstickm.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		tagstick.setItemMeta(tagstickm);
		tagstick.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		
		return tagstick;
	}
	
	public boolean isTagStick(ItemStack item) {
		if(item.hasItemMeta()) {
			if(item.getItemMeta().hasDisplayName() && item.getItemMeta().hasLore()) {
				String name = color(plugin.getConfig().getString("TagStick.display_name"));
				List<String> lore = color(plugin.getConfig().getStringList("TagStick.lore"));
				if(item.getItemMeta().getDisplayName().contains(name) && item.getItemMeta().getLore().containsAll(color(lore))) {
					return true;
				}
				else {
					return false;
				}
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
	
	private String color(String s){
	    return ChatColor.translateAlternateColorCodes('&', s);
	}
	
	private List<String> color(List<String> lore){
	    return lore.stream().map(this::color).collect(Collectors.toList());
	}
}