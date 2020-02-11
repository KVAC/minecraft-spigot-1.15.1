package jds_project.minecraft.threads;

import java.util.Collection;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import jds_project.minecraft.JDCSPlugin;
import jds_project.minecraft.objects.artefacts.Sluda;
import jds_project.minecraft.objects.artefacts.sample.Artefact;
import jds_project.minecraft.objects.artefacts.sample.Artefact.ArtefactType;

public class ArtefactInventTask extends BukkitRunnable {
	private boolean stopeed = false;
	private JDCSPlugin main;

	public JDCSPlugin getMain() {
		return main;
	}

	public void setMain(JDCSPlugin main) {
		this.main = main;
	}

	public ArtefactInventTask(JDCSPlugin plugin) {
		this.main = plugin;
	}

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

			Collection<? extends Player> players = this.main.getServer().getOnlinePlayers();

			for (Player player : players) {
				/*
				 * // RESPAWN if (player.isDead()) { try { ((CraftPlayer)
				 * player).getHandle().playerConnection .a(new
				 * PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN)); } catch
				 * (Exception e) { e.printStackTrace(); } } // RESPAWN
				 */
				// ART
				PlayerInventory inventory = player.getInventory();
				
				try {

					ItemStack[] itemstacks = inventory.getStorageContents();
					if (itemstacks.length > 0) {
						for (int i = 0; i < itemstacks.length; i++) {
							if (itemstacks[i] != null) {
								ItemStack itemStack = itemstacks[i];
								ArtefactType typeART = Artefact.recognizeArtedact(itemStack);
								if (typeART != null) {
									double count = (double) itemStack.getAmount();
									double currentHealth = player.getHealth();

									if (typeART.equals(ArtefactType.SLUDA)) {
										if ((currentHealth + count) <= 20) {
											player.setHealth(currentHealth + count);
										} else if ((currentHealth + count) >= 20) {
											player.setHealth(20);
										}
									}
								}
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
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
