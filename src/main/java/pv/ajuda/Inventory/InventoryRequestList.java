package pv.ajuda.Inventory;

import com.samjakob.spigui.SGMenu;
import com.samjakob.spigui.buttons.SGButton;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import pv.ajuda.Config.Messages;
import pv.ajuda.Main;
import pv.ajuda.System.SystemHelp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InventoryRequestList {

    public static String invName;

    private static SGMenu inventory;
    private static HashMap<Player, Integer> pedidos = new HashMap<>();
    private static List<Player> playersOpenInv = new ArrayList<>();

    public InventoryRequestList(String inventoryName, int size) {
        this.inventory = Main.instance().GUI.create(inventoryName, size);
        inventory.setAutomaticPaginationEnabled(false);
    }

    private static HashMap<Player, Long> cooldowns = new HashMap<Player, Long>();

    public static void reload() {
        int slot = 0;
        inventory.clearAllButStickiedSlots();
        pedidos.clear();
        List<String> lore = new ArrayList<>();
        lore.add("§bClique com o botão §aESQUERDO §bpara aceitar");
        lore.add("§bClique com o botão §cDIREITO §bpara cancelar");
        for(Player player : SystemHelp.openRequests) {
            inventory.setButton(slot, new SGButton(ItemBuilder.getSkull(player.getDisplayName(), player.getDisplayName(), 1, lore))
                    .withListener((InventoryClickEvent event) -> {
                        if(event.getWhoClicked() instanceof Player) {
                            Player staff = (Player) event.getWhoClicked();
                            if(event.isLeftClick()) {
                                SystemHelp.acceptRequest(staff, player);
                            } else if(event.isRightClick()){
                                if(staff.hasPermission("pv.ajuda.cancel")){
                                    int cooldownTime = 1;
                                    if(cooldowns.containsKey(staff)) {
                                        long secondsLeft = ((cooldowns.get(staff) / 1000) + cooldownTime) - (java.lang.System.currentTimeMillis() / 1000);
                                        if (secondsLeft > 0) {
                                            staff.sendMessage("§aAguarde " + secondsLeft + " para remover o player !");
                                            return;
                                        }
                                    }
                                    cooldowns.put(staff, java.lang.System.currentTimeMillis());
                                    SystemHelp.removeRequest(staff, player);

                                } else {
                                    staff.sendMessage(Messages.notPermission);
                                }
                            }
                        }
                    }));
            pedidos.put(player, slot);
            slot++;
        }
        for(Player player : playersOpenInv) {
            player.closeInventory();
            open(player);
        }

        // Decoração 36-44
        for(int i = 36; i < 45; i++)
            inventory.setButton(i, new SGButton(ItemBuilder.getItem(Material.STAINED_GLASS_PANE, "§8-/-", 1)));

        inventory.setButton(49, new SGButton(ItemBuilder.getItem(Material.REDSTONE_BLOCK, "§cFechar Tudo", 1))
                .withListener((InventoryClickEvent event) -> {
                    if(event.isLeftClick()) {
                        Player player = (Player) event.getWhoClicked();
                        if(!player.hasPermission("pv.ajuda.cancel.all")){
                            player.sendMessage(Messages.notPermission);
                            return;
                        }
                        SystemHelp.removeAllRequest();
                    }
                }));
    }

    public static void open(Player player) {
        reload();
        player.openInventory(inventory.getInventory());
    }

    public static SGMenu getInventory() {
        return inventory;
    }

    public static List<Player> getPlayersOpenInv() {
        return playersOpenInv;
    }
}
