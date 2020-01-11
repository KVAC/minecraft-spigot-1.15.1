package jds_project.minecraft;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import jds_project.minecraft.threads.ArtefactInventTask;

public class JDCSPlugin extends JavaPlugin implements Listener {
	ArtefactInventTask artefactInventTask;
	JDCSPlugin plugin = this;

	@Override
	public void onEnable() {
		System.out.println("JDCSPlugin.onEnable()");
		// checkAndInitBackInventory();
		// this.getCommand("artefact").setExecutor(new CommandKit());

		// getServer().getPluginManager().registerEvents(this, this);
	}

	@Override
	public void onLoad() {
	//	checkAndInitBackInventory();
		System.out.println("JDCSPlugin.onLoad()");
	}

	@Override
	public void onDisable() {
		// stopBackInventory();
		System.out.println("JDCSPlugin.onDisable()");
	}

	@EventHandler
	private void name(PlayerInteractEvent event) {

	}

	private void checkAndInitBackInventory() {
		stopBackInventory();
		startBackInventory();
	}

	private void stopBackInventory() {
		if (artefactInventTask != null) {
			artefactInventTask.cancel();
		}
	}

	private void startBackInventory() {
		artefactInventTask = new ArtefactInventTask(plugin);
		artefactInventTask.runTaskAsynchronously(this);
	}

}
