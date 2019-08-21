package me.decide.plugin.antibotsystem.listeners;

import me.decide.plugin.antibotsystem.persist.Blacklisted;
import me.decide.plugin.antibotsystem.persist.Config;
import me.decide.plugin.antibotsystem.persist.Whitelisted;
import me.decide.plugin.antibotsystem.utils.ChatUtility;
import me.decide.plugin.antibotsystem.utils.CheckUtility;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerLoginEvent event) {

        String ip = event.getAddress().getHostAddress();
        Player player = event.getPlayer();

        if (Whitelisted.whitelisted_ip.contains(ip)) {
            return;
        }

        if (Blacklisted.blacklisted_ip.contains(ip)) {
            player.kickPlayer(null);
            return;
        }

        if (CheckUtility.getBotStatus(ip)) {
            event.disallow(PlayerLoginEvent.Result.KICK_BANNED, "Bot Detected");
            Blacklisted.blacklisted_ip.add(ip);
            if (Config.notify_staff) notifyStaff(player, true);
            return;
        }

        Whitelisted.whitelisted_ip.add(ip);
        if (Config.notify_staff) notifyStaff(player, false);
    }

    private void notifyStaff(Player player, boolean status) {
        for (Player staff : Bukkit.getServer().getOnlinePlayers()) {

            if (staff.hasPermission(Config.notify_staff_permission) || staff.isOp()) {
                staff.sendMessage(ChatUtility.color("&7&m---------------------------------------------------------"));
                staff.sendMessage(ChatUtility.color("&e" + player.getName() + "&7: ") +  (status ? ChatUtility.color("&cBot Detected") : ChatUtility.color("&aPassed Checks")));
                staff.sendMessage(ChatUtility.color("&7&m---------------------------------------------------------"));
                //TitleUtility.sendActionBar(staff, ChatUtility.color("&a" + player.getName()) + " " +  (status ? ChatUtility.color("&cBot Detected") : ChatUtility.color("&aPassed Checks")));
            }

        }
    }

}
