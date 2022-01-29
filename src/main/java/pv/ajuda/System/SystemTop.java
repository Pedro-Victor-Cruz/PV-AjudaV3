package pv.ajuda.System;
import org.bukkit.entity.Player;
import pv.ajuda.Config.FileYaml;
import pv.ajuda.Main;

import java.util.UUID;

public class SystemTop {

    private static FileYaml database = Main.instance().database;

    public static void setPoint(Player player, int point) {
        if(point >= 0) {
            if (database.contains("TOP." + player.getUniqueId())) {
                database.set("TOP." + player.getUniqueId() + ".Point", point);
            } else {
                database.set("TOP." + player.getUniqueId() + ".Point", point);
                database.set("TOP." + player.getUniqueId() + ".Nick", player.getDisplayName());
            }
        }
    }

    public static void setPoint(UUID uuid, String name, int point) {
        if(point >= 0) {
            if (database.contains("TOP." + uuid)) {
                database.set("TOP." + uuid + ".Point", point);
            } else {
                database.set("TOP." + uuid + ".Point", point);
                database.set("TOP." + uuid + ".Nick", name);
            }
        }
    }

    public static void addPoint(Player player, int point) {
        if(database.contains("TOP." + player.getUniqueId())) {
            database.set("TOP." + player.getUniqueId() + ".Point", getPoint(player) + point);
        } else {
            database.set("TOP." + player.getUniqueId() + ".Point", point);
            database.set("TOP." + player.getUniqueId() + ".Nick", player.getDisplayName());
        }
    }

    public static void addPoint(UUID uuid,String name, int point) {
        if(database.contains("TOP." + uuid)) {
            database.set("TOP." + uuid + ".Point", getPoint(uuid) + point);
        } else {
            database.set("TOP." + uuid + ".Point", point);
            database.set("TOP." + uuid + ".Nick", name);
        }
    }

    public static void removeTop(UUID uuid) {
        if(database.contains("TOP." + uuid)){
            database.set("TOP." + uuid, null);
        }
    }

    public static void removePoint(UUID uuid,String name, int point) {
        int remover = getPoint(uuid) - point;
        if (database.contains("TOP." + uuid)) {
            if(remover < 0) {
                database.set("TOP." + uuid + ".Point", 0);
            } else {
                database.set("TOP." + uuid + ".Point", remover);
            }
        } else {
            database.set("TOP." + uuid + ".Point", point);
            database.set("TOP." + uuid + ".Nick", name);
        }
    }

    public static int getPoint(Player player) {
       if(database.contains("TOP." + player.getUniqueId())) {
           return database.getInt("TOP." + player.getUniqueId() + ".Point");
       }
       return 0;
    }

    public static int getPoint(UUID uuid) {
        if(database.contains("TOP." + uuid)) {
            return database.getInt("TOP." + uuid + ".Point");
        }
        return 0;
    }

    public static String getName(UUID uuid) {
        if(database.contains("TOP." + uuid)) {
            return database.getString("TOP." + uuid + ".Nick");
        }
        return null;
    }

}
