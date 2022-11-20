package me.megabot.blockrecover.model;

import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.Date;

public class BlockTimestamp {
    private final Block block;
    private final Date date;
    private final Material previousMaterial;

    public BlockTimestamp(Block block, Material previousMaterial) {
        this.block = block;
        this.date = new Date();
        this.previousMaterial = previousMaterial;
    }

    public Block getBlock() {
        return block;
    }

    public Long getTime() {
        return date.getTime();
    }

    public Material getPreviousMaterial() {
        return previousMaterial;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BlockTimestamp blockTimestamp)) return false;

        return this.block.equals(blockTimestamp.getBlock());
    }
}
