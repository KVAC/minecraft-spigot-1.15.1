package jds_project.minecraft;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import jds_project.minecraft.objects.artefacts.Object639;
import jds_project.minecraft.objects.artefacts.Pillow;
import jds_project.minecraft.objects.artefacts.Sluda;
import jds_project.minecraft.objects.artefacts.sample.Artefact.ArtefactType;
import jds_project.minecraft.permissions.Permissions;

public class CommandAddART implements CommandExecutor {

	enum commands {
		give, nulled
	}

	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] cmdArgs) {
		StringBuilder errorBuilder = new StringBuilder();
		try {
			if (!(sender instanceof Player)) {
				return false;
			}
			Player playerSender = (Player) sender;
			if (cmdArgs.length == 0) {
				return false;
			}

			commands cmd = null;
			try {
				cmd = commands.valueOf(cmdArgs[0]);
			} catch (Exception e) {
				playerSender.sendMessage("Доступные действия:");
				for (commands cmds : commands.values()) {
					if (cmds.toString().equals(commands.nulled.toString())) {
						continue;
					}
					playerSender.sendMessage(cmds.toString());
				}
				cmd = commands.nulled;
				return false;
			}

			if (!playerSender.hasPermission(Permissions.STALKER_ARTEFACT_GIVE)) {
				playerSender.sendMessage("You do not have permission '" + Permissions.STALKER_ARTEFACT_GIVE + "'");
				return false;
			}
			Player playerTo = Bukkit.getPlayer(cmdArgs[1]);
			if (playerTo == null) {
				playerSender.sendMessage(ChatColor.RED + "Нет такого игрока:" + cmdArgs[1]);
				return false;
			}
			ArtefactType addWhat;
			try {
				addWhat = ArtefactType.valueOf(cmdArgs[2]);
			} catch (Exception e) {
				playerSender.sendMessage("Доступные артефакты:");
				for (ArtefactType types : ArtefactType.values()) {
					playerSender.sendMessage(types.toString());
				}
				return false;
			}

			int count = 1;
			try {
				count = Integer.parseInt(cmdArgs[3]);
			} catch (Exception e) {
			}

			for (int i = 0; i < cmdArgs.length; i++) {
				playerSender.sendMessage(cmdArgs[i]);
			}
			playerSender.sendMessage("cmd:" + cmd + " кому:" + playerTo + " что:" + addWhat + " сколько:" + count);

			if (addWhat.equals(ArtefactType.SLUDA)) {
				playerSender.getInventory().addItem(new Sluda(count));
			} else if (addWhat.equals(ArtefactType.OBJECT639)) {
				playerSender.getInventory().addItem(new Object639(count));
			} else if (addWhat.equals(ArtefactType.PILLOW)) {
				playerSender.getInventory().addItem(new Pillow(count));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		sender.sendMessage(errorBuilder.toString());
		return true;
	}
}