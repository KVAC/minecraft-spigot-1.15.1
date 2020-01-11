package jds_project.minecraft.threads;

import org.bukkit.scheduler.BukkitRunnable;

public class ArtefactInventTask extends BukkitRunnable {
	
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
