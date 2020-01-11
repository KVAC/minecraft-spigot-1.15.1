package jds_project.minecraft;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import jds_project.minecraft.threads.ArtefactInventTask;

public class JDCSPlugin extends JavaPlugin implements Listener {
	public static Thread backgroundTask;

	@Override
	public void onEnable() {
		checkAndInitBackInventory();
		System.err.println(11111111111l);
		super.onEnable();
		this.getCommand("artefact").setExecutor(new CommandKit());
		getServer().getPluginManager().registerEvents(this, this);
	}

	@Override
	public void onLoad() {
		checkAndInitBackInventory();
	}

	@Override
	public void onDisable() {
		if (backgroundTask != null) {
			backgroundTask.interrupt();
		}
		backgroundTask = null;
	}

	private void checkAndInitBackInventory() {
		if (backgroundTask != null) {
			System.exit(0);
			backgroundTask.interrupt();
			backgroundTask = null;
		}
		backgroundTask = new ArtefactInventTask(this);

	}

}
