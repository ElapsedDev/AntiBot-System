package me.decide.plugin.antibotsystem.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.decide.plugin.antibotsystem.persist.Config;

import java.io.InputStreamReader;
import java.net.URL;

public class CheckUtility {

    public static boolean getBotStatus(String IP) {
        try {
            URL url = new URL("http://check.getipintel.net/check.php?ip=" + IP.replace("/", "") + "&contact=" + Config.email + "&format=json");
            InputStreamReader reader = new InputStreamReader(url.openStream());

            JsonObject jsonObject = new JsonParser().parse(reader).getAsJsonObject();

             if (jsonObject.get("status").getAsString().equalsIgnoreCase("success")) {
                 if (jsonObject.get("result").getAsDouble() > Config.ratio_check) {
                     return true;
                 }
             } else {
                 return false;
             }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

}
