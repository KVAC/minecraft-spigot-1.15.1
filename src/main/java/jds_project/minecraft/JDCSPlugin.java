package jds_project.minecraft;

import java.io.File;

import org.bukkit.entity.Player;
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

		player.sendMessage(new File("").getAbsolutePath());
		// ПКМ
		if (event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
			ArtefactType type = Artefact.recognizeArtedact(itemInHand);
			if (type != null) {
			}
		}

		// ЛКМ
		else if (event.getAction().equals(Action.LEFT_CLICK_AIR)) {

			/*
			 * if (itemInHand.getType() == Material.ARROW) {// сравниваю со стрелой Material
			 * item2 = Material.GLOWSTONE; if (player.getInventory().contains(item2)) {//
			 * если есть светокамень создаю летящую стрелу Entity arrow =
			 * player.launchProjectile(Arrow.class);
			 * arrow.setVelocity(player.getLocation().getDirection().multiply(1f)); } }
			 */
		}
	}

}
