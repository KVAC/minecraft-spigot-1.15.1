package jds_project.minecraft;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

import jds_project.minecraft.objects.artefacts.sample.Artefact;
import jds_project.minecraft.objects.artefacts.sample.Artefact.ArtefactType;
import jds_project.minecraft.threads.ArtefactInventTask;

public class JDCSPlugin extends JavaPlugin implements Listener {
	ArtefactInventTask artefactInventTask;
	JDCSPlugin plugin = this;

	public static ArrayList<String> domains = new ArrayList<String>();

	@Override
	public void onEnable() {
		domains.add(".com");
		domains.add("0:1");
		domains.add("0:2");
		domains.add("0:3");
		domains.add("0:4");
		domains.add("0:5");
		domains.add("0:6");
		domains.add("0:7");
		domains.add("0:8");
		domains.add("0:9");
		domains.add("1:0");
		domains.add("1:1");
		domains.add("1:2");
		domains.add("1:3");
		domains.add("1:4");
		domains.add("1:5");
		domains.add("1:6");
		domains.add("1:7");
		domains.add("1:8");
		domains.add("1:9");
		domains.add("2:0");
		domains.add("2:1");
		domains.add("2:2");
		domains.add("2:3");
		domains.add("2:4");
		domains.add("2:5");
		domains.add("2:6");
		domains.add("2:7");
		domains.add("2:8");
		domains.add("2:9");
		domains.add("3:0");
		domains.add("3:1");
		domains.add("3:2");
		domains.add("3:3");
		domains.add("3:4");
		domains.add("3:5");
		domains.add("3:6");
		domains.add("3:7");
		domains.add("3:8");
		domains.add("3:9");
		domains.add("4:0");
		domains.add("4:1");
		domains.add("4:2");
		domains.add("4:3");
		domains.add("4:4");
		domains.add("4:5");
		domains.add("4:6");
		domains.add("4:7");
		domains.add("4:8");
		domains.add("4:9");
		domains.add("5:0");
		domains.add("5:1");
		domains.add("5:2");
		domains.add("5:3");
		domains.add("5:4");
		domains.add("5:5");
		domains.add("5:6");
		domains.add("5:7");
		domains.add("5:8");
		domains.add("5:9");
		domains.add("6:0");
		domains.add("6:1");
		domains.add("6:2");
		domains.add("6:3");
		domains.add("6:4");
		domains.add("6:5");
		domains.add("6:6");
		domains.add("6:7");
		domains.add("6:8");
		domains.add("6:9");
		domains.add("7:0");
		domains.add("7:1");
		domains.add("7:2");
		domains.add("7:3");
		domains.add("7:4");
		domains.add("7:5");
		domains.add("7:6");
		domains.add("7:7");
		domains.add("7:8");
		domains.add("7:9");
		domains.add("8:0");
		domains.add("8:1");
		domains.add("8:2");
		domains.add("8:3");
		domains.add("8:4");
		domains.add("8:5");
		domains.add("8:6");
		domains.add("8:7");
		domains.add("8:8");
		domains.add("8:9");
		domains.add("9:0");
		domains.add("9:1");
		domains.add("9:2");
		domains.add("9:3");
		domains.add("9:4");
		domains.add("9:5");
		domains.add("9:6");
		domains.add("9:7");
		domains.add("9:8");
		domains.add("9:9");
		domains.add(".com");
		domains.add(".com");
		domains.add(".com");

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
	public void antiSpam(AsyncPlayerChatEvent event) {
		if (spamContains(event.getMessage())) {

			event.getPlayer().sendMessage("NAX");
			event.setCancelled(true);
		}
	}

	public static boolean containsDomain(String input) {
		for (String string : domains) {
			if (input.toLowerCase().contains(string.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	public static boolean spamContains(String forCheck) {
		String[] splited = forCheck.split(" ");
		for (int i = 0; i < splited.length; i++) {
			if (containsDomain(splited[i])) {
				return true;
			}
			String[] splitedIP = splited[i].split(":");
			for (int j = 0; j < splitedIP.length; j++) {
				if (validIP(splitedIP[j])) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean validIP(String ip) {
		try {
			if (ip == null || ip.isEmpty()) {
				return false;
			}

			String[] parts = ip.split("\\.");
			if (parts.length != 4) {
				return false;
			}
			for (String s : parts) {
				int i = Integer.parseInt(s);
				if ((i < 0) || (i > 255)) {
					return false;
				}
			}
			if (ip.endsWith(".")) {
				return false;
			}

			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
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
									double defaultHEAL = 0.1119;
									if (damage > 19.3) {
										event.setDamage(0);
										double addHeal = ((double) count * 0.5)
												+ ((double) count * 0.5) / 100 * defaultHEAL;
										player.sendMessage("" + addHeal);
										if (addHeal >= 20) {
											player.setHealth(20);
										} else if (addHeal <= 20) {
											player.setHealth(addHeal);
										}
									} else {
										event.setDamage(0);
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
