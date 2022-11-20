package me.megabot.blockrecover;

import me.megabot.blockrecover.listener.BlockBreakListener;
import me.megabot.blockrecover.util.BlockUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class BlockRecover extends JavaPlugin {
    private FileConfiguration materialsConfig;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        initializeConfigs();
        BlockUtil.initializeMaterialMap(materialsConfig);

        getServer()
                .getScheduler()
                .scheduleSyncRepeatingTask(
                        this,
                        BlockUtil::updateBlockQueue, 0L, 1L);
    }

    @Override
    public void onDisable() {
    }

    private void initializeConfigs() throws NullPointerException {
        File materialsConfigFile = new File(getDataFolder(), "materials.yml");
        if (!materialsConfigFile.exists()) {
            materialsConfigFile.getParentFile().mkdirs();
            saveResource("materials.yml", false);
        }
        materialsConfig = YamlConfiguration.loadConfiguration(materialsConfigFile);
    }
}
