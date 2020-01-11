package jds_project.minecraft;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import jds_project.minecraft.threads.ArtefactInventTask;

public class JDCSPlugin extends JavaPlugin implements Listener {
	ArtefactInventTask artefactInventTask;
	JDCSPlugin plugin = this;

	@Override
	public void onEnable() {
		this.getCommand("artefact").setExecutor(new CommandKit());
		getServer().getPluginManager().registerEvents(this, this);
		checkAndInitBackInvent();
	}

	@Override
	public void onLoad() {
		checkAndInitBackInvent();
	}

	@Override
	public void onDisable() {
		stopBackInventory();
	}

	private void checkAndInitBackInvent() {
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
		artefactInventTask.runTaskAsynchronously(plugin);
	}

}
