package me.elapsedkid.plugin.antibotsystem.persist;

import net.prosavage.baseplugin.serializer.Serializer;

public class Config {

    private static transient Config instance = new Config();

    public static String email = "";
    public static double ratio_check = 0.7;
    public static boolean notify_staff = true;
    public static String notify_staff_permission = "antibot.notify";
    public static String bot_command_permission = "antibot.command";

    public static void save() {
        new Serializer().save(instance);
    }

    public static void load() {
        new Serializer().load(instance, Config.class, "config");
    }

}
