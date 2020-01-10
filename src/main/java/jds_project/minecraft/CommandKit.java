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
		if (arg3.length == 3) {
			// GIVE
			if (arg3[0].toLowerCase().equals("give".toLowerCase())) {
				if (arg3[1].toLowerCase().equals("sluda".toLowerCase())) {
					Sluda sluda = new Sluda();
					player.getInventory().addItem(sluda);
				}
				// Если не указан
				else {
					player.sendMessage("Не указан артефакт\nNo artifact specified");
				}
			}
			// НЕ УКАЗАНО ДЕЙСТВИЕ
			else {
				player.sendMessage("Не указано действие \nNo action specified");
			}
		}
		return true;
	}

}
