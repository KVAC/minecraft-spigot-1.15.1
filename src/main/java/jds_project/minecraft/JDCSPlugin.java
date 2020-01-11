package jds_project.minecraft;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import jds_project.minecraft.threads.ArtefactInventTask;

public class JDCSPlugin extends JavaPlugin implements Listener {
	ArtefactInventTask artefactInventTask;
	JDCSPlugin plugin;

	@Override
	public void onEnable() {
		checkAndInitBackInventory();
		this.getCommand("artefact").setExecutor(new CommandKit());

		getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler
	private void name(PlayerInteractEvent event) {

	}

	@Override
	public void onDisable() {
		stopBackInventory();
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
		artefactInventTask = new ArtefactInventTask();
		artefactInventTask.runTaskAsynchronously(this);
	}

}
