package jds_project.minecraft;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public class JDCSPlugin extends JavaPlugin implements Listener {
	public static Thread backgroundTask;

	@Override
	public PluginCommand getCommand(String name) {
		// TODO Auto-generated method stub
		return super.getCommand(name);
	}

	@Override
	public FileConfiguration getConfig() {
		// TODO Auto-generated method stub
		return super.getConfig();
	}

	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		// TODO Auto-generated method stub
		return super.getDefaultWorldGenerator(worldName, id);
	}

	@Override
	protected File getFile() {
		// TODO Auto-generated method stub
		return super.getFile();
	}

	@Override
	public Logger getLogger() {
		// TODO Auto-generated method stub
		return super.getLogger();
	}

	@Override
	public InputStream getResource(String filename) {
		// TODO Auto-generated method stub
		return super.getResource(filename);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// TODO Auto-generated method stub
		return super.onCommand(sender, command, label, args);
	}

	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		super.onDisable();
	}

	@Override
	public void onLoad() {
		System.err.println(12312323);
		System.exit(0);
		super.onLoad();
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		// TODO Auto-generated method stub
		return super.onTabComplete(sender, command, alias, args);
	}

	@Override
	public void reloadConfig() {
		// TODO Auto-generated method stub
		super.reloadConfig();
	}

	@Override
	public void saveConfig() {
		// TODO Auto-generated method stub
		super.saveConfig();
	}

	@Override
	public void saveDefaultConfig() {
		// TODO Auto-generated method stub
		super.saveDefaultConfig();
	}

	@Override
	public void saveResource(String resourcePath, boolean replace) {
		// TODO Auto-generated method stub
		super.saveResource(resourcePath, replace);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	@Override
	public void onEnable() {
		super.onEnable();
		this.getCommand("artefact").setExecutor(new CommandKit());
		getServer().getPluginManager().registerEvents(this, this);
	}

}
