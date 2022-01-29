package pv.ajuda.System;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class SystemMute {

    private static HashMap<UUID, Long> mutes = new HashMap<>();

    public static void mute(UUID player, int time) {
        if(!mutes.containsKey(player)){
            long ms = time * 60000;
            mutes.put(player, (System.currentTimeMillis() + ms));
        }
    }

    public static void unmute(UUID player) {
        if(mutes.containsKey(player)){
            mutes.remove(player);
        }
    }

    public static boolean isMute(UUID player) {
        if(mutes.containsKey(player)){
            if(mutes.get(player) > System.currentTimeMillis()){
                return true;
            } else {
                mutes.remove(player);
                return false;
            }
        }
        return false;
    }

    public static long getTempLeft(UUID player){
        if(mutes.containsKey(player)){
            long temp = ((mutes.get(player) / 1000) - (System.currentTimeMillis() / 1000));
            return temp;
        }
        return 0;
    }
}
