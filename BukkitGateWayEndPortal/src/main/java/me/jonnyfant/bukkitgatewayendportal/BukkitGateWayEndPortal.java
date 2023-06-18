package me.jonnyfant.bukkitgatewayendportal;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.EndGateway;
import org.bukkit.block.TileState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class BukkitGateWayEndPortal extends JavaPlugin {

    ///setblock ~ ~-1 ~ minecraft:end_gateway{Age:-9223372036854775808L,ExactTeleport:3,ExitPortal:{X:1,Y:2,Z:3}}
    private final static int DEFAULT_RANGE = 16;

    @Override
    public void onEnable() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String commandName = command.getName();
        if (sender.hasPermission("totemstorage.use") && sender instanceof Player) {
            if (commandName.toLowerCase().equals("transformendgateway")) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage("This command is for players only");
                    return false;
                }

                int dist = DEFAULT_RANGE;
                try {
                    dist = Integer.parseInt(args[0]);
                } catch (Exception e) {
                }

                Location start = ((Player) sender).getLocation();

                for (int x = start.getBlockX() - dist; x < start.getBlockX() + dist; x++) {
                    for (int y = start.getBlockY() - dist; y < start.getBlockY() + dist; y++) {
                        for (int z = start.getBlockZ() - dist; z < start.getBlockZ() + dist; z++) {
                            Location l = start.clone();
                            l.setX(x);
                            l.setY(y);
                            l.setZ(z);
                            if (l.getBlock().getType().equals(Material.END_GATEWAY)) {
                                BlockState state = l.getBlock().getState();

                                if (state instanceof EndGateway) {
                                    // Modify the gateway properties here
                                    ((EndGateway) state).setAge(-9223372036854775808L);
                                    ((EndGateway) state).setExactTeleport(true);
                                    World end = null;
                                    for (World w : Bukkit.getWorlds()) {
                                        if (w.getEnvironment() == World.Environment.THE_END) {
                                            end = w;
                                            break;
                                            //I Sure hope that you do not have multiple "End" worlds, otherwise this might not work correctly
                                        }
                                    }

                                    if (end != null) {
                                        ((EndGateway) state).setExitLocation(end.getSpawnLocation());
                                    }
                                    state.update(true);
                                }
                            }
                        }
                    }
                }

            }
        }
        return false;
    }
}
