package di.internal.controller;

import java.io.File;

import org.bukkit.plugin.Plugin;

import di.internal.controller.file.ConfigManager;
import di.internal.controller.file.YamlManager;
import lombok.Getter;

/**
 * This controller is in charge of configuring and obtaining the default files
 * of the plugin.
 */
@Getter
public class InternalController implements PluginController {

	/**
	 * The driver for the plugin configuration file.
	 */
	private ConfigManager configManager;

	/**
	 * The driver for the plugin lang file.
	 */
	private YamlManager langManager;

	/**
	 * The bukkit plugin.
	 */
	private Plugin plugin;

	/**
	 * Path of the plugin folder.
	 */
	private File dataFolder;

	/**
	 * Main Class Constructor.
	 * 
	 * @param plugin         Bukkit plugin.
	 * @param coreController Core controller.
	 * @param classLoader    Class loader.
	 */
	public InternalController(Plugin plugin, CoreController coreController, ClassLoader classLoader) {
		this.plugin = plugin;
		this.dataFolder = getInternalPluginDataFolder(plugin, coreController);
		this.configManager = new ConfigManager(this, dataFolder);
		this.langManager = new YamlManager(this, "lang.yml", dataFolder, classLoader);
	}

	/**
	 * Gets the plugin folder located in the DI folder
	 * 
	 * @param plugin         Bukkit plugin.
	 * @param coreController Core controller.
	 * @return Plugin folder.
	 */
	private File getInternalPluginDataFolder(Plugin plugin, CoreController coreController) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(String.valueOf(coreController.getDataFolder().getAbsolutePath()));
		stringBuilder.append("/");
		stringBuilder.append(plugin.getName());
		return new File(stringBuilder.toString());
	}
}