package me.decide.plugin.antibotsystem.persist;

import net.prosavage.baseplugin.serializer.Serializer;

import java.util.LinkedHashSet;
import java.util.Set;

public class Whitelisted {

    private static transient Whitelisted instance = new Whitelisted();

    public static Set<String> whitelisted_ip = new LinkedHashSet<>();

    public static void save() {
        new Serializer().save(instance);
    }

    public static void load() {
        new Serializer().load(instance, Whitelisted.class, "whitelisted");
    }
}
