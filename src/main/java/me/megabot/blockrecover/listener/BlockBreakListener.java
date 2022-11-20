package me.megabot.blockrecover.listener;

import me.megabot.blockrecover.model.BlockTimestamp;
import me.megabot.blockrecover.util.BlockUtil;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class BlockBreakListener implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (BlockUtil.ignoreBlockInQueue(block)) {
            event.setCancelled(true);
            return;
        }
        Material sisterMaterial = BlockUtil.getSisterMaterial(block.getType());
        if (sisterMaterial == null) return;

        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.SURVIVAL) {
            ItemStack handItem = player.getInventory().getItemInMainHand();
            final List<ItemStack> drops = (List<ItemStack>) block.getDrops(handItem, player);
            drops.forEach(drop -> block.getWorld().dropItemNaturally(block.getLocation(), drop));
        }

        BlockUtil.addBlockQueue(new BlockTimestamp(block, block.getType()));
        block.setType(sisterMaterial);
        event.setCancelled(true);
    }

}
