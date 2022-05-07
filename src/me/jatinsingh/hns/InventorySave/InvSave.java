package me.jatinsingh.hns.InventorySave;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.jatinsingh.hns.Main;

public class InvSave {
	
	private Main plugin;
	
	public InvSave(Main plugin) {
		this.plugin = plugin;
	}
	
	public void saveInventory(Player p) throws IOException {
        File f = new File(plugin.getDataFolder().getAbsolutePath(), "data.yml");
        FileConfiguration c = YamlConfiguration.loadConfiguration(f);
        c.set("inventory." + p.getName() + ".armor", p.getInventory().getArmorContents());
        c.set("inventory." + p.getName() + ".content", p.getInventory().getContents());
        c.save(f);
    }

    @SuppressWarnings("unchecked")
    public void restoreInventory(Player p) throws IOException {
        File f = new File(plugin.getDataFolder().getAbsolutePath(), "data.yml");
        FileConfiguration c = YamlConfiguration.loadConfiguration(f);
        ItemStack[] content = ((List<ItemStack>) c.get("inventory." + p.getName() + ".armor")).toArray(new ItemStack[0]);
        p.getInventory().setArmorContents(content);
        content = ((List<ItemStack>) c.get("inventory." + p.getName() + ".content")).toArray(new ItemStack[0]);
        p.getInventory().setContents(content);
    }
}