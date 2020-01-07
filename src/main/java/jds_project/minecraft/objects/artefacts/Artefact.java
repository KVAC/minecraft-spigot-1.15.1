package jds_project.minecraft.objects.artefacts;

import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Artefact extends ItemStack {
	public static enum ArtefactType {
		SLUDA
	}

	private int cost = 1000;

	public static ArtefactType recognizeArtedact(ItemStack itemStack) {
		ArtefactType type = null;

		ItemMeta meta = itemStack.getItemMeta();
		if (meta != null) {
			List<String> lore = meta.getLore();
			if (lore != null && lore.size() > 0) {
				// SLUDA
				if (lore.get(0).equals(Sluda.lore0)) {
					return ArtefactType.SLUDA;
				}
				// SLUDA

			}

		}
		return type;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

}
