package jds_project.minecraft;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

import jds_project.minecraft.objects.artefacts.sample.Artefact;
import jds_project.minecraft.objects.artefacts.sample.Artefact.ArtefactType;
import jds_project.minecraft.threads.ArtefactInventTask;

public class JDCSPlugin extends JavaPlugin implements Listener {
	ArtefactInventTask artefactInventTask;
	JDCSPlugin plugin = this;

	@Override
	public void onEnable() {
		System.out.println("JDCSPlugin.onEnable()");
		checkAndInitBackInventory();
		this.getCommand("artefact").setExecutor(new CommandAddART());
		getServer().getPluginManager().registerEvents(this, this);
	}

	@Override
	public void onLoad() {
		stopBackInventory();
		System.out.println("JDCSPlugin.onLoad()");
	}

	@Override
	public void onDisable() {
		stopBackInventory();
		System.out.println("JDCSPlugin.onDisable()");
	}

	@EventHandler
	private void name(EntityDamageEvent event) {
		if (event.getCause().equals(DamageCause.FALL)) {
			Entity entity = event.getEntity();
			double damage = event.getDamage();
			if (entity instanceof Player) {
				Player player = (Player) entity;

				PlayerInventory inventory = player.getInventory();

				ItemStack[] storage = inventory.getStorageContents();
				for (int i = 0; i < storage.length; i++) {
					ItemStack itemStack = storage[i];
					if (itemStack != null) {
						if (itemStack.getType().equals(Material.PHANTOM_MEMBRANE)) {
							ArtefactType typeART = Artefact.recognizeArtedact(itemStack);
							if (typeART != null) {
								int count = itemStack.getAmount();
								if (typeART.equals(ArtefactType.PILLOW)) {
									if (damage > 19.3) {
										event.setDamage(0);
										double defaultHEAL = 0.1119;
										double addHeal = ((double) count * 0.5)
												+ ((double) count * 0.5) / 100 * defaultHEAL;
										player.sendMessage("" + addHeal);
										if (addHeal >= 20) {
											player.setHealth(20);
										} else if (addHeal <= 20) {
											player.setHealth(addHeal);
										}
									}
								}
							}
						}
					}
				}
			}
		}
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
		artefactInventTask = new ArtefactInventTask(plugin);
		artefactInventTask.runTaskAsynchronously(this);
	}

}
