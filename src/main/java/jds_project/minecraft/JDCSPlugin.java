package jds_project.minecraft;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import jds_project.minecraft.objects.artefacts.Artefact;
import jds_project.minecraft.objects.artefacts.Artefact.ArtefactType;

public class JDCSPlugin extends JavaPlugin implements Listener {
	@Override
	public void onEnable() {
		super.onEnable();
		this.getCommand("artefact").setExecutor(new CommandKit());
		getServer().getPluginManager().registerEvents(this, this);
	}

	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		super.onDisable();
	}

	@EventHandler
	public void killed(PlayerInteractEvent event) {

		if (event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
			Player player = event.getPlayer();

			ItemStack itemInHand = player.getInventory().getItemInMainHand();
			ArtefactType type = Artefact.recognizeArtedact(itemInHand);
			if (type != null) {
				if (type.equals(ArtefactType.SLUDA)) {

					List<Entity> lis = player.getNearbyEntities(100, 100, 100);
					ArrayList<LivingEntity> LivingEntityList = new ArrayList<LivingEntity>();
					for (int i = 0; i < lis.size(); i++) {
						if (lis.get(i) instanceof LivingEntity) {
							LivingEntityList.add((LivingEntity) lis.get(i));
						}
					}
					for (int i = 0; i < LivingEntityList.size(); i++) {
						if (!(LivingEntityList.get(i) instanceof Player)) {
							if (LivingEntityList instanceof Villager) {
								continue;
							}
							LivingEntityList.get(i).setFireTicks(20 * 10);
							LivingEntityList.get(i).setHealth(0);
						}
					}
					double countD = 25;// -20%
					double health = player.getHealth();
					double countC = (health / 100 * countD);

					if (((health - countC) < 0) || health < 1) {
						// FIX FOR DOS 1.15.1
						if (player.getGameMode().equals(GameMode.CREATIVE)) {
						} else {
							player.setHealth(0);// kill
						}
					} else {
						player.setHealth((health - countC));
						player.sendMessage("Health:" + player.getHealth());
					}
				}
			}

			if (itemInHand.getType() == Material.ARROW) {// сравниваю со стрелой
				Material item2 = Material.GLOWSTONE;
				if (player.getInventory().contains(item2)) {// если есть светокамень создаю летящую стрелу
					Entity arrow = player.launchProjectile(Arrow.class);
					// arrow.setGlowing(true);
					arrow.setVelocity(player.getLocation().getDirection().multiply(1030f));

				}

			} else if (itemInHand.getType() == Material.PHANTOM_MEMBRANE) {// если в руках мембрана
				// получаю локацию игрока
				Location location = event.getPlayer().getLocation();
				// получаю все сущьности в радиусе 60 блоков
				Collection<Entity> listEntity = event.getPlayer().getWorld().getNearbyEntities(location, 60, 60, 60);
				int exp = 0;
				int ent = 0;
				int heal = 0;
				for (Iterator<Entity> iterator = listEntity.iterator(); iterator.hasNext();) {
					Entity entity = (Entity) iterator.next();
					// если не игрок (кто вызывал)
					if (entity != player) {
						// проверка на живых
						if (entity instanceof LivingEntity) {
							// проверка на то что сущьность не горит
							if (!(entity.getFireTicks() >= 1)) {
								// проверяю чтобы сущьность не была в воде
								Material b = entity.getLocation().getBlock().getType();
								if (b == Material.WATER) {

									/*
									 * ТУТ БЫЛА ОТЛАДКА double x = entity.getLocation().getX(); double y =
									 * entity.getLocation().getY(); double z = entity.getLocation().getZ();
									 * Bukkit.broadcastMessage(":water:" + x + ":" + y + ":" + z);
									 */
								} else {
									ent++;
									// подсвечиваю сущность
									entity.setGlowing(true);
									// поджигаю на мин
									entity.setFireTicks(20 * 60);
									// выдаю опыт
									player.giveExp(1);
									exp++;
									// проверка на колво жизней и выдача
									if (!((player.getHealth() + 1) > 20)) {

										if (player.getHealth() < 20) {
											player.setHealth(player.getHealth() + 1);
											heal++;
										}
									}
								}

							}

						} else {
							// не живые

							// проверка на лежащие вещи
							if (entity.getType() == EntityType.DROPPED_ITEM) {
								/*
								 * String message1=entity.getType().getEntityClass().;
								 * player.sendMessage("M1:"+message1);
								 */

								Item droppedItem = (Item) entity;
								ItemStack iStack = droppedItem.getItemStack();
								if (iStack.getType() == Material.CLAY_BALL) {
									List<Entity> list = entity.getNearbyEntities(2, 2, 2);
									List<Entity> list2 = new ArrayList<Entity>();
									for (int i = 0; i < list.size(); i++) {
										if (list.get(i).getType() == EntityType.DROPPED_ITEM) {
											list2.add(list.get(i));
										}
									}

									boolean GLOWSTONE_DUST = false;
									Item droppedItem2 = null;
									for (Iterator<Entity> iterator2 = list2.iterator(); iterator2.hasNext();) {
										Entity entity2 = (Entity) iterator2.next();

										droppedItem2 = (Item) entity2;
										if (droppedItem2.getItemStack().getType() == Material.GLOWSTONE_DUST) {
											GLOWSTONE_DUST = true;
										}

									}
									if (GLOWSTONE_DUST == true) {
										if (droppedItem.getItemStack().getAmount() == 1
												&& droppedItem2.getItemStack().getAmount() == 1) {
											droppedItem.remove();
											droppedItem2.remove();
											ItemStack awe = new ItemStack(Material.NETHER_STAR);
											droppedItem.getWorld().dropItem(droppedItem.getLocation(), awe);

										}

									}
								}
							}
							entity.setGlowing(true);
						}
					}

				}
				if (exp + heal + ent >= 1) {
					player.sendMessage("Подожено:" + ent + "\n" + "Восстановлено здоровья:" + heal + "\n"
							+ "Получено опыта:" + exp);

				}
			}

		}
	}

}
