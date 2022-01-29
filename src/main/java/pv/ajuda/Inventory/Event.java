package pv.ajuda.Inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class Event implements Listener {

    @EventHandler
    public void InventoryOpen(InventoryOpenEvent event) {
        Player player = (Player) event.getPlayer();
        if(event.getInventory().getHolder() == null) return;
        if(event.getInventory().getHolder().equals(InventoryRequestList.getInventory().getInventory().getHolder())) {
            if(!InventoryRequestList.getPlayersOpenInv().contains(player)) {
                InventoryRequestList.getPlayersOpenInv().add(player);
            }
        } else if(event.getInventory().getHolder().equals(InventoryTop.getInventory().getInventory().getHolder())) {
            if(InventoryTop.getPlayersOpenInv().contains(player)){
                InventoryTop.getPlayersOpenInv().add(player);
            }
        }
    }

    @EventHandler
    public void InventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        if(event.getInventory().getHolder() == null) return;
        if(event.getInventory().getHolder().equals(InventoryRequestList.getInventory().getInventory().getHolder())) {
            if(InventoryRequestList.getPlayersOpenInv().contains(player)) {
                InventoryRequestList.getPlayersOpenInv().remove(player);
            }
        } else if(event.getInventory().getHolder().equals(InventoryTop.getInventory().getInventory().getHolder())) {
            if(InventoryTop.getPlayersOpenInv().contains(player)){
                InventoryTop.getPlayersOpenInv().remove(player);
            }
        }
    }
}
