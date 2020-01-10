package jds_project.minecraft;

import java.util.Collection;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

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
						System.err.println(player);
					}
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
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
