package com.earth418.veinminer;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BlockPlugin implements Listener {
    
    VeinMiner vM;
    private int MAX_BLOCKS = 128;
    private int currentBlocks = 0;
    Material blockBroken;
    Material[] VeinMinables = {Material.COAL_ORE, Material.IRON_ORE, Material.DIAMOND_ORE, Material.EMERALD_ORE, 
    		Material.REDSTONE_ORE, Material.GLOWING_REDSTONE_ORE, Material.GOLD_ORE, Material.LAPIS_BLOCK,
    		Material.QUARTZ_ORE, Material.LOG, Material.LOG_2, Material.OBSIDIAN
    };

    public BlockPlugin(VeinMiner in) {
        in.getServer().broadcastMessage("VeinMiner Installed. SNEAK while breaking a block to use vein miner.");
        vM = in;
    }
    
    private boolean matArrayContains(Material[] arr, Material item) {
    	for (Material m : arr) {
    		if (item.equals(m)) return true;
    	}
    	return false;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        blockBroken = event.getBlock().getType();
        if (event.getPlayer().isSneaking() && vM.veinMiners.contains(event.getPlayer()) && matArrayContains(VeinMinables, blockBroken)) {
            currentBlocks = 0;
            veinMine(event.getBlock(), event.getPlayer());
        }
    }

    public void veinMine(Block firstBlock, Player player) {
        int[] locations = {-1, 0, 1};
        for (int dx : locations) {
            for (int dy : locations) {
                for (int dz : locations) {
                    if (currentBlocks >= MAX_BLOCKS) {
                        return;
                    }
                    Block newBlock = firstBlock.getLocation().add(dx, dy, dz).getBlock();
                    if (newBlock.getType().equals(blockBroken)) {
                        ItemStack tool = player.getInventory().getItemInMainHand();
                    	newBlock.breakNaturally(tool);
                        if (Math.random() < (1 / (1 + tool.getEnchantmentLevel(Enchantment.DURABILITY)))) {
                            tool.setDurability((short)(tool.getDurability() + 1));
                            if (tool.getDurability() >= tool.getType().getMaxDurability() && tool.getMaxStackSize() <= 1 && !tool.getType().isBlock())
                            		tool.setType(Material.AIR);
                        }
                        veinMine(newBlock, player);
                    }
                }
            }
        }
    }
}