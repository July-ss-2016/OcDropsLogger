package vip.ourcraft.mcserverplugins.ocdropslogger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.plugin.java.JavaPlugin;
import vip.ourcraft.mcserverplugins.easyfileloggerforbukkit.EasyFileLogger;
import vip.ourcraft.mcserverplugins.easyfileloggerforbukkit.FileLogger;

import java.io.File;

public class OcDropsLogger extends JavaPlugin implements Listener {
    private FileLogger fileLogger;

    public void onEnable() {
        this.fileLogger = EasyFileLogger.getInstance().getFileLogger(new File(getDataFolder(), "logs"), null, 60);

        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("初始化完毕!");
    }

    @EventHandler
    public void onPlayerDropItemEvent(PlayerDropItemEvent event) {
        Player player = event.getPlayer();

        if (!event.isCancelled()) {
            fileLogger.log("action = drop, player = " + player.getName() + ", loc = " + player.getLocation().toString() + ", item = " + event.getItemDrop().getItemStack().toString());
        }
    }

    @EventHandler
    public void onEntityPickupItemEvent(EntityPickupItemEvent event) {
        if (!event.isCancelled()) {
            Entity entity = event.getEntity();

            if (entity instanceof Player) {
                Player player = (Player) entity;

                fileLogger.log("action = pickup, player = " + player.getName() + ", loc = " + player.getLocation() + ", item = " + event.getItem().getItemStack().toString());
            }
        }
    }
}
