package jds_project.minecraft;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import jds_project.minecraft.objects.artefacts.Sluda;

public class CommandKit implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		if (!(sender instanceof Player)) {
			return false;
		}

		Player player = (Player) sender;
		if (!player.hasPermission("stalker.artefact.give")) {
			player.sendMessage("You do not have permission 'stalker.artefact.give'");
			return false;
		}
		Sluda sluda = new Sluda(3);
		player.getInventory().addItem(sluda);
		return true;
	}

}
