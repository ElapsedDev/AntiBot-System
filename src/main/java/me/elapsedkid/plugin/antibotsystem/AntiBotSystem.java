package me.elapsedkid.plugin.antibotsystem;

import me.elapsedkid.plugin.antibotsystem.commands.CommandAntibot;
import me.elapsedkid.plugin.antibotsystem.listeners.PlayerListener;
import me.elapsedkid.plugin.antibotsystem.persist.Blacklisted;
import me.elapsedkid.plugin.antibotsystem.persist.Config;
import me.elapsedkid.plugin.antibotsystem.persist.Whitelisted;
import net.prosavage.baseplugin.BasePlugin;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class AntiBotSystem extends BasePlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        super.onEnable();
        Whitelisted.load();
        Blacklisted.load();
        Config.load();

        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getPluginCommand("antibot").setExecutor(new CommandAntibot());

        new BukkitRunnable(){
            @Override
            public void run() {
                Whitelisted.save();
                Blacklisted.save();
            }
        }.runTaskTimerAsynchronously(this, (20 * 60) * 15, (20 * 60) * 15);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        super.onDisable();
        Whitelisted.save();
        Blacklisted.save();
        Config.save();
    }
}
