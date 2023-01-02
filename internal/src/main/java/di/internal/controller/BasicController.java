package di.internal.controller;

import java.io.File;
import java.util.logging.Logger;

import di.internal.controller.file.ConfigManager;
import di.internal.controller.file.YamlManager;

/**
 * Interface of internal controllers.
 */
public interface BasicController {

    /**
     * @return Plugin logger.
     */
    Logger getLogger();

    /**
     * Disable the plugin.
     */
    void disablePlugin();

    /**
     * @return Plugin configuration data manager.
     */
    ConfigManager getConfigManager();

    /**
     * @return Plugin language data manager.
     */
    YamlManager getLangManager();

    /**
     * @return Plugin data folder.
     */
    File getDataFolder();

}
