package me.decide.plugin.antibotsystem.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class TitleUtility {

    public static void sendActionBar(Player player, String message){
        try {
            Class<?> c1 = getCraftBukkitClass("entity.CraftPlayer");
            Object p = c1.cast(player);
            Object ppoc = null;
            Class<?> c4 = getNMSClass("PacketPlayOutChat");
            Class<?> c5 = getNMSClass("Packet");
            Class<?> c2 = getNMSClass("ChatComponentText");
            Class<?> c3 = getNMSClass("IChatBaseComponent");
            Object o = c2.getConstructor(new Class<?>[] {String.class}).newInstance(new Object[] {message});
            ppoc = c4.getConstructor(new Class<?>[] {c3, byte.class}).newInstance(new Object[] {o, (byte) 2});
            Method m1 = c1.getDeclaredMethod("getHandle", new Class<?>[] {});
            Object h = m1.invoke(p);
            Field f1 = h.getClass().getDeclaredField("playerConnection");
            Object pc = f1.get(h);
            Method m5 = pc.getClass().getDeclaredMethod("sendPacket",new Class<?>[] {c5});
            m5.invoke(pc, ppoc);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static Class<?> getNMSClass(String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Class<?> getCraftBukkitClass(String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("org.bukkit.craftbukkit." + version + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}
