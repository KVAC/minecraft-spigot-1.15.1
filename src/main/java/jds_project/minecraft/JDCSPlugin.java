package jds_project.minecraft;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
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
		Player player = event.getPlayer();
		ItemStack itemInHand = player.getInventory().getItemInMainHand();

		// ПКМ
		if (event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
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

		}
		// ЛКМ
		else if (event.getAction().equals(Action.LEFT_CLICK_AIR)) {
			player.sendMessage(event.getAction().toString());
			// СТРЕЛА
			if (itemInHand.getType() == Material.ARROW) {// сравниваю со стрелой
				Material item2 = Material.GLOWSTONE;
				if (player.getInventory().contains(item2)) {// если есть светокамень создаю летящую стрелу
					Entity arrow = player.launchProjectile(Arrow.class);
					// arrow.setGlowing(true);
					arrow.setVelocity(player.getLocation().getDirection().multiply(1030f));
				}

			}
		}
	}

}
