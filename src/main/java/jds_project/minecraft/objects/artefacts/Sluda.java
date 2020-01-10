package jds_project.minecraft.objects.artefacts;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Sluda extends Artefact {
	public static String lore0 = "aaa";
	List<String> lore;
	ItemStack a;
	ItemMeta meta;

	public Sluda(int count) {
		setAmount(count);
		initSluda();
	}

	public Sluda() {
		setAmount(1);
		initSluda();
	}

	private void initSluda() {

		a = new ItemStack(Material.SCUTE);
		meta = a.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "Слюда");
		setType(Material.SCUTE);
		lore = new ArrayList<String>();
		lore.add(Sluda.lore0);
		meta.setLore(lore);
		this.setItemMeta(meta);
	}
}
