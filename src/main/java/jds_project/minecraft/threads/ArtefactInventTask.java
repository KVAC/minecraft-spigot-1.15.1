package jds_project.minecraft.threads;

import org.bukkit.scheduler.BukkitRunnable;

import jds_project.minecraft.JDCSPlugin;

public class ArtefactInventTask extends BukkitRunnable {
	JDCSPlugin plugin;
	private boolean stop = false;

	public ArtefactInventTask(JDCSPlugin jdcsPlugin) {
		this.plugin = jdcsPlugin;
	}

	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

	@Override
	public void run() {
		do {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("awe222");
		} while (true);
	}

}
