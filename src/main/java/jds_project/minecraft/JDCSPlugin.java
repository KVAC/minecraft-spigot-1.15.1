package jds_project.minecraft;

import java.util.ArrayList;
import java.util.Random;

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
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;
import com.comphenix.protocol.wrappers.WrappedServerPing;

import jds_project.minecraft.chat.spam.SpamUtils;
import jds_project.minecraft.objects.artefacts.sample.Artefact;
import jds_project.minecraft.objects.artefacts.sample.Artefact.ArtefactType;
import jds_project.minecraft.threads.ArtefactInventTask;

public class JDCSPlugin extends JavaPlugin implements Listener {
	ArtefactInventTask artefactInventTask;
	JDCSPlugin plugin = this;

	public static ArrayList<String> domains = new ArrayList<String>();

	private ProtocolManager protocolManager;

	@Override
	public void onEnable() {
		protocolManager = ProtocolLibrary.getProtocolManager();
		protocolManager.addPacketListener((PacketListener) new PacketAdapter(
				// FIXME возможно PacketType.Status.Server.OUT_SERVER_INFO а не PONG
				PacketAdapter.params((Plugin) this, new PacketType[] { PacketType.Status.Server.PONG }).optionAsync()) {
			public void onPacketSending(PacketEvent event) {
				WrappedServerPing ping = (WrappedServerPing) event.getPacket().getServerPings().read(0);
				ping.setPlayersMaximum(20000);
				boolean random = true;
				int fake = 140;
				int min = 40;
				int max = 2000;
				if (random) {
					Random r = new Random();
					int online = Math.abs(r.nextInt() % (max - min) + 1 + min);
					ping.setPlayersOnline(online);
				} else {
					ping.setPlayersOnline(fake);
				}
			}
		});

		for (int i = 0; i <= 9; i++) {
			for (int j = 0; j <= 9; j++) {
				domains.add(i + ":" + j);
			}
		}
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
		if (SpamUtils.spamContains(event.getMessage())) {
			event.getPlayer().sendMessage("NAX");
			event.setCancelled(true);
		}
	}

	@EventHandler
	private void damageFall(EntityDamageEvent event) {
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
