package jds_project.minecraft.threads;

import org.bukkit.scheduler.BukkitRunnable;

public class ArtefactInventTask extends BukkitRunnable {
	private boolean stopeed = false;

	@Override
	public void run() {
		do {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (stopeed == true) {
				break;
			}
			System.out.println("awe222");
		} while (isStopeed() == false);
	}

	@Override
	public synchronized void cancel() throws IllegalStateException {
		stopeed = true;
	}

	public boolean isStopeed() {
		return stopeed;
	}

	public void setStopeed(boolean stopeed) {
		this.stopeed = stopeed;
	}
}
