package pv.ajuda.System;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pv.ajuda.Inventory.InventoryRequestList;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Event implements Listener {

    private List<UUID> closeRequests = new ArrayList<>();

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(closeRequests.contains(player.getUniqueId())){
            player.sendMessage("§eSeu pedido de ajuda foi fechado pois você desconectou !\n§eCaso queira abrir novamente utilize /ajuda.");
        }
        return;
    }

    @EventHandler
    public void PlayerQuit(PlayerQuitEvent event) {
        OfflinePlayer playerOff = event.getPlayer();
        Player player = playerOff.getPlayer();
        if(SystemHelp.openRequests.contains(player)){
            SystemHelp.removeRequest(player);
            closeRequests.add(player.getUniqueId());
        }
        return;
    }
}
