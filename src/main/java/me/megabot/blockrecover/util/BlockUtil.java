package me.megabot.blockrecover.util;

import me.megabot.blockrecover.model.BlockTimestamp;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;
import java.util.stream.Collectors;

public class BlockUtil {

    private static Map<Material, Material> materialMap = new HashMap<>();
    private static final Queue<BlockTimestamp> blockQueue = new LinkedList<>();

    public static void initializeMaterialMap(FileConfiguration materialsConfig) {
        materialMap = materialsConfig.getConfigurationSection("blocks")
                .getValues(false)
                .entrySet()
                .stream()
                .collect(
                Collectors.toMap(
                    material -> Material.getMaterial(material.getKey()),
                    material -> Material.getMaterial((String) material.getValue())));
    }

    public static Material getSisterMaterial(Material material) {
        return materialMap.get(material);
    }

    public static void updateBlockQueue() {
        if (blockQueue.isEmpty()) return;
        BlockTimestamp blockTimestamp = blockQueue.peek();
        if (System.currentTimeMillis() - blockTimestamp.getTime() < 3000) return;

        blockQueue.poll();
        Block block = blockTimestamp.getBlock();
        block.getWorld().setType(block.getLocation(), blockTimestamp.getPreviousMaterial());
    }

    public static void addBlockQueue(BlockTimestamp blockTimestamp) {
        blockQueue.add(blockTimestamp);
    }

    public static boolean ignoreBlockInQueue(Block block) {
        return blockQueue.contains(new BlockTimestamp(block, block.getType()));
    }
}
