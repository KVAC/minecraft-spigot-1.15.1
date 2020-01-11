package jds_project.minecraft;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import jds_project.minecraft.threads.ArtefactInventTask;

public class JDCSPlugin extends JavaPlugin implements Listener {
	public static ArtefactInventTask backgroundTask;

	@Override
	public void onEnable() {
		checkAndInitBackInventory();
		this.getCommand("artefact").setExecutor(new CommandKit());
		getServer().getPluginManager().registerEvents(this, this);
	}

	@Override
	public void onLoad() {
		checkAndInitBackInventory();
	}

	@Override
	public void onDisable() {
		stopBackInventory();
	}

	private void checkAndInitBackInventory() {
		stopBackInventory();
		startBackInventory();
	}

	private void startBackInventory() {
		backgroundTask = new ArtefactInventTask(this);
		backgroundTask.start();
	}

	private void stopBackInventory() {
		try {
			backgroundTask.setStop(true);
			backgroundTask.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
