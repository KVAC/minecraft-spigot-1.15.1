package jds_project.minecraft;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import jds_project.minecraft.objects.artefacts.Sluda;
import jds_project.minecraft.permissions.Permissions;

public class CommandKit implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		if (!(sender instanceof Player)) {
			return false;
		}

		Player player = (Player) sender;
		if (player.hasPermission(Permissions.STALKER_ARTEFACT_GIVE)) {
			if (arg3.length >= 2) {
				// GIVE
				if (arg3[0].toLowerCase().equals("give".toLowerCase())) {

					if (arg3[1].toLowerCase().equals("sluda".toLowerCase())) {
						// КОЛВО
						int count = 1;
						if (arg3.length == 3) {
							if (arg3[2] != null) {
								try {
									count = Integer.parseInt(arg3[2]);
								} catch (Exception e) {
								}
							}
						} // КОЛВО
						Sluda sluda = new Sluda(count);
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
		} else {
			player.sendMessage("You do not have permission '" + Permissions.STALKER_ARTEFACT_GIVE + "'");
		}
		return true;
	}

}
