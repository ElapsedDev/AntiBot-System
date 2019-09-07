package me.elapsedkid.plugin.antibotsystem.persist;

import net.prosavage.baseplugin.serializer.Serializer;

import java.util.LinkedHashSet;
import java.util.Set;

public class Blacklisted {

    private static transient Blacklisted instance = new Blacklisted();

    public static Set<String> blacklisted_ip = new LinkedHashSet<>();

    public static void save() {
        new Serializer().save(instance);
    }

    public static void load() {
        new Serializer().load(instance, Blacklisted.class, "blacklisted");
    }
}
