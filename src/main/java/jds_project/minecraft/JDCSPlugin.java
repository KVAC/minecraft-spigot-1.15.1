package jds_project.minecraft;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

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

	boolean fakeOnline = false;

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {

	}

	boolean random = true;
	int fake = 140;
	int min = 40;
	int max = 20000;

	@SuppressWarnings("deprecation") // OUT_SERVER_INFO
	@Override
	public void onEnable() {
		protocolManager = ProtocolLibrary.getProtocolManager();
		protocolManager.addPacketListener((PacketListener) new PacketAdapter(PacketAdapter
				.params((Plugin) this, new PacketType[] { PacketType.Status.Server.OUT_SERVER_INFO }).optionAsync()) {
			public void onPacketSending(PacketEvent event) {
				if (event.getPacket().getServerPings().read(0) instanceof WrappedServerPing) {
					try {
						// System.err.println("PING FROM " + event.getPlayer().getAddress().toString());
						Files.write(new File("PING_LOG").toPath(),
								(event.getPlayer().getAddress().toString() + '\n').getBytes(),
								StandardOpenOption.APPEND);

					} catch (IOException e) {
						e.printStackTrace();
					}
					if (fakeOnline) {
						WrappedServerPing ping = (WrappedServerPing) event.getPacket().getServerPings().read(0);
						ping.setPlayersMaximum(max);
						if (random) {
							Random r = new Random();
							int online = Math.abs(r.nextInt() % (max - min) + 1 + min);
							ping.setPlayersOnline(online);
						} else {
							ping.setPlayersOnline(fake);
						}
					}
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

	@EventHandler
	public void weaponDamageHandler(EntityDamageByEntityEvent event) {
		System.out.println(event.getDamager().getClass());

		// getServer().broadcastMessage(ChatColor.GREEN + metods[i].getName() +
		// ChatColor.RED + " : " +event.getClass().getMethods().);

	}

	@EventHandler
	public void ArrowLaunch(PlayerToggleSneakEvent event) {

		if (event.getPlayer().isGliding()) {
			Projectile projectile = event.getPlayer().launchProjectile(Arrow.class);
			projectile.setVelocity(event.getPlayer().getLocation().getDirection().multiply(100));

		}
		// ITEMSTACK
		ItemStack itemstack = event.getPlayer().getInventory().getItemInMainHand();
		// MATERIAL
		Material itemInMainHand = itemstack.getType();

		if (itemInMainHand.equals(Material.NETHER_STAR)) {
			for (int i = 0; i <= 20; i++) {
				Projectile projectile2 = event.getPlayer().launchProjectile(Arrow.class);
				Vector vel = event.getPlayer().getLocation().getDirection().multiply(100);
				vel.setX(vel.getX() + randDouble(-10, 10));
				vel.setY(vel.getY() + randDouble(-10, 10));
				vel.setZ(vel.getZ() + randDouble(-10, 10));

				projectile2.setVelocity(vel);
				projectile2.setTicksLived(20 * 3);
				// projectile2.setCustomName("aaa");
				// projectile2.setCustomNameVisible(true);
			}
		}

		// ДАЛЬШЕ БЛОК ОРУЖИЯ
		//
		if (itemstack.hasItemMeta()) {
			ItemMeta meta = itemstack.getItemMeta();
			if (meta.hasLore()) {
				List<String> lore = meta.getLore();
				if (lore.size() > 0) {
					String lore0 = lore.get(0);
					if (lore0.equals("aaa")) {
						if (itemInMainHand.equals(Material.WOODEN_SWORD)) {
							Projectile projectile2 = event.getPlayer().launchProjectile(Arrow.class);
							Vector vel = event.getPlayer().getLocation().getDirection().multiply(100);
							vel.setX(vel.getX() + randDouble(-10, 10));
							vel.setY(vel.getY() + randDouble(-10, 10));
							vel.setZ(vel.getZ() + randDouble(-10, 10));

							projectile2.setVelocity(vel);
							projectile2.setTicksLived(20 * 3);
							projectile2.setCustomName("Material.WOODEN_SWORD");
							projectile2.setCustomNameVisible(true);
						}
					}
				}
			}
		}

		else if (itemInMainHand.equals(Material.STONE_SWORD)) {
			for (int i = 1; i <= 3; i++) {
				Projectile projectile2 = event.getPlayer().launchProjectile(Arrow.class);
				Vector vel = event.getPlayer().getLocation().getDirection().multiply(100);
				vel.setX(vel.getX() + randDouble(-10, 10));
				vel.setY(vel.getY() + randDouble(-10, 10));
				vel.setZ(vel.getZ() + randDouble(-10, 10));

				projectile2.setVelocity(vel);
				projectile2.setTicksLived(20 * 3);

			}

		} else if (itemInMainHand.equals(Material.IRON_SWORD)) {
			for (int i = 1; i <= 4; i++) {
				Projectile projectile2 = event.getPlayer().launchProjectile(Arrow.class);
				Vector vel = event.getPlayer().getLocation().getDirection().multiply(100);
				vel.setX(vel.getX() + randDouble(-10, 10));
				vel.setY(vel.getY() + randDouble(-10, 10));
				vel.setZ(vel.getZ() + randDouble(-10, 10));

				projectile2.setVelocity(vel);
				projectile2.setTicksLived(20 * 3);

			}
		} else if (itemInMainHand.equals(Material.GOLDEN_SWORD)) {

			for (int i = 1; i <= 2; i++) {
				Projectile projectile2 = event.getPlayer().launchProjectile(Arrow.class);
				Vector vel = event.getPlayer().getLocation().getDirection().multiply(100);
				vel.setX(vel.getX() + randDouble(-10, 10));
				vel.setY(vel.getY() + randDouble(-10, 10));
				vel.setZ(vel.getZ() + randDouble(-10, 10));

				projectile2.setVelocity(vel);
				projectile2.setTicksLived(20 * 3);

			}
		} else if (itemInMainHand.equals(Material.DIAMOND_SWORD)) {
			Projectile projectile2 = event.getPlayer().launchProjectile(Arrow.class);
			Vector vel = event.getPlayer().getLocation().getDirection().multiply(100);
			vel.setX(vel.getX() + randDouble(-10, 10));
			vel.setY(vel.getY() + randDouble(-10, 10));
			vel.setZ(vel.getZ() + randDouble(-10, 10));

			projectile2.setVelocity(vel);
			projectile2.setTicksLived(20 * 3);
		}
	}

	private static double randDouble(double min, double max) {
		Random r = new Random();
		double randomValue = min + (max - min) * r.nextDouble();
		return randomValue;
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
