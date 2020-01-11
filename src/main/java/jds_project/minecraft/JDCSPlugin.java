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
		startBackInventory();
		this.getCommand("artefact").setExecutor(new CommandKit());

		getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler
	private void name(PlayerInteractEvent event) {

	}

	@Override
	public void onDisable() {
	}

	private void startBackInventory() {
		new ArtefactInventTask().runTaskAsynchronously(this);
	}

}
