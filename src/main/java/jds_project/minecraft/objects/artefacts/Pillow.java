package jds_project.minecraft.objects.artefacts;

import org.bukkit.Material;

import jds_project.minecraft.objects.artefacts.sample.Artefact;

public class Pillow extends Artefact {

	// VALUE
	public static String artefactID = "Pillow_a";
	public static Material material = Material.PHANTOM_MEMBRANE;

	public static String name = "Подушка";
	public static String lore0 = "Спасает от гибели ";

	public static int lore1_cost = 500000;
	// VALUE

	public Pillow() {
		setAmount(1);
		initART(this);
	}

	public Pillow(int amount) {
		setAmount(amount);
		initART(this);
	}

}
