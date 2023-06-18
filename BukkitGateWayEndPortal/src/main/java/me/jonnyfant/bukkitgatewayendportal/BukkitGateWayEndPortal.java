package me.jonnyfant.bukkitgatewayendportal;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class BukkitGateWayEndPortal extends JavaPlugin {

    ///setblock ~ ~-1 ~ minecraft:end_gateway{Age:-9223372036854775808L,ExactTeleport:3,ExitPortal:{X:1,Y:2,Z:3}}
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new GateWayListener(this), this);
    }
}
