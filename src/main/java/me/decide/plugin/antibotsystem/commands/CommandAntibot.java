package me.decide.plugin.antibotsystem.commands;

import me.decide.plugin.antibotsystem.persist.Blacklisted;
import me.decide.plugin.antibotsystem.persist.Config;
import me.decide.plugin.antibotsystem.persist.Whitelisted;
import me.decide.plugin.antibotsystem.utils.ChatUtility;
import me.decide.plugin.antibotsystem.utils.CheckUtility;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandAntibot implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender.hasPermission(Config.bot_command_permission) || sender.isOp()) {

            if (args.length == 0) {
                sender.sendMessage(ChatUtility.color("&7&m-------------------"));
                sender.sendMessage(ChatUtility.color("&e/antibot reload"));
                sender.sendMessage(ChatUtility.color("&e/antibot check <player>"));
                sender.sendMessage(ChatUtility.color("&7&m-------------------"));
                return true;
            }

            switch (args[0].toLowerCase()) {
                case "save":
                    Blacklisted.save();
                    Whitelisted.save();
                    sender.sendMessage(ChatUtility.color("&aData has been saved!"));
                    break;
                case "load":
                    Blacklisted.load();
                    Whitelisted.load();
                    sender.sendMessage(ChatUtility.color("&aData has been loaded!"));
                    break;
                case "reload":
                    Config.load();
                    sender.sendMessage(ChatUtility.color("&aConfig has been reloaded!"));
                    break;
                case "check":

                    if (args.length < 2) {
                        sender.sendMessage(ChatUtility.color("Please Provide a player!"));
                        return true;
                    }

                    Player target = Bukkit.getServer().getPlayer(args[1]);

                    if (target == null) {
                        sender.sendMessage(ChatUtility.color("&cPlayer must be online!"));
                        return true;
                    }

                    if (Whitelisted.whitelisted_ip.contains(target.getAddress().getAddress().toString())) {
                        sender.sendMessage(ChatUtility.color("&aThis player is clear!"));
                        return true;
                    }

                    boolean botStatus = CheckUtility.getBotStatus(target.getAddress().getAddress().toString().replace("/", ""));

                    if (botStatus) {
                        Blacklisted.blacklisted_ip.add(target.getAddress().getAddress().toString().replace("/", ""));
                    }

                    String status = botStatus ? "&cBot Detected" : "&aNormal Player";

                    sender.sendMessage(ChatUtility.color("&7&m---------------------------------------------------------"));
                    sender.sendMessage(ChatUtility.color("&e" + target.getName()) + " " + ChatUtility.color(status));
                    sender.sendMessage(ChatUtility.color("&7&m---------------------------------------------------------"));
                    break;
            }
        }
        return false;
    }

}
