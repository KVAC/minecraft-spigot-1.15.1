package jds_project.minecraft;

import java.util.Collection;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

import jds_project.minecraft.objects.artefacts.Sluda;

public class JDCSPlugin extends JavaPlugin implements Listener {
	public static Thread backgroundTask;

	@Override
	public void onEnable() {
		super.onEnable();
		this.getCommand("artefact").setExecutor(new CommandKit());
		getServer().getPluginManager().registerEvents(this, this);

		backgroundTask = new Thread(new Runnable() {

			@Override
			public void run() {
				do {
					Collection<? extends Player> players = getServer().getOnlinePlayers();
					for (Player player : players) {
						PlayerInventory inventory = player.getInventory();
						if (inventory.contains(Material.STONE)) {
							inventory.addItem(new Sluda(1));
						}
					}
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} while (true);
			}
		});
		backgroundTask.start();
	}

	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		super.onDisable();
	}

}
