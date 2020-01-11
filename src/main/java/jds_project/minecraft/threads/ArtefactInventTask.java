package jds_project.minecraft.threads;

import java.util.Collection;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import jds_project.minecraft.JDCSPlugin;
import jds_project.minecraft.objects.artefacts.Sluda;

public class ArtefactInventTask extends Thread implements Runnable {
	JDCSPlugin plugin;
	private boolean stop = false;

	public ArtefactInventTask(JDCSPlugin jdcsPlugin) {
		this.plugin = jdcsPlugin;
	}

	@Override
	public void run() {
		do {
			Collection<? extends Player> players = plugin.getServer().getOnlinePlayers();
			for (Player player : players) {
				PlayerInventory inventory = player.getInventory();
				if (inventory.contains(Material.STONE)) {
					Sluda sludaItem = new Sluda();
					inventory.addItem(sludaItem);
					sludaItem = null;
				}
				inventory = null;
				player = null;
			}

			players = null;
			System.err.println(123);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (true);
	}

	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}
}
