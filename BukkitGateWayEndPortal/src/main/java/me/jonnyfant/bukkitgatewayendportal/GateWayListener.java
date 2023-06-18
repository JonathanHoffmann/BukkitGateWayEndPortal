package me.jonnyfant.bukkitgatewayendportal;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class GateWayListener implements Listener {

    @EventHandler
    public void onPlayerGateWay(PlayerTeleportEvent event) {
        //Bukkit.broadcastMessage("Player teleport event was triggered with the cause " + event.getCause());
        if (handleTeleport(event.getPlayer(), event.getFrom(), event.getTo(), event.getCause())) {
            event.setCancelled(true);
        }
    }
    /*@EventHandler
    public void onEntityGateWay(EntityPortalEvent event)
    {
        Bukkit.broadcastMessage("Entity teleport event was triggered");
        if(handleTeleport(event.getEntity(), event.getFrom(), event.getTo(), null))
        {
            event.setCancelled(true);
        }
    }*/

    public boolean handleTeleport(Entity entity, Location from, Location to, PlayerTeleportEvent.TeleportCause cause) {
        if (cause == PlayerTeleportEvent.TeleportCause.END_GATEWAY) {
            //Bukkit.broadcastMessage("Right cause");
        } else if (from.getBlock().getType() == Material.END_GATEWAY) {
            //Bukkit.broadcastMessage("teleportation from an End GateWay.");
        } else {
            //Bukkit.broadcastMessage("couldn't establish End Gateway as cause");
            return false;
        }

        if (entity.getLocation().getWorld().getEnvironment() != World.Environment.NORMAL) {
            //Bukkit.broadcastMessage("teleportation from not in the overworld, ignoring");
            //not in the overworld, dismissing
            return false;
        }

        if (to.getWorld().getEnvironment() == World.Environment.THE_END) {
            //Bukkit.broadcastMessage("Teleportation target is in the end, ignoring, probably already called this.");
            //nothing to do here, already teleporting to the end
            return false;
        }

        //find end world
        World end = null;
        for (World w : Bukkit.getWorlds()) {
            if (w.getEnvironment() == World.Environment.THE_END) {
                end = w;
                break;
                //I Sure hope that you do not have multiple "End" worlds, otherwise this might not work correctly
            }
        }

        if (end == null) {
            //no end world was found to teleport to, aborting
            return false;
        }
        entity.teleport(end.getSpawnLocation());

        //EntityTeleportEvent newEvent = new EntityTeleportEvent(entity, from, end.getSpawnLocation());
        //Bukkit.getServer().getPluginManager().callEvent(newEvent);
        return true;
    }
}
