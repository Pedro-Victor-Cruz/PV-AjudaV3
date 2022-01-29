package pv.ajuda.Inventory;

import com.samjakob.spigui.SGMenu;
import com.samjakob.spigui.buttons.SGButton;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import pv.ajuda.Comparator.Decrescente;
import pv.ajuda.Config.Messages;
import pv.ajuda.Main;
import pv.ajuda.System.SystemTop;
import pv.ajuda.System.TopBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class InventoryTop {

    public static String invName;

    private static SGMenu inventory;
    private static List<Player> playersOpenInv = new ArrayList<>();

    public InventoryTop(String inventoryName, int size) {
        this.inventory = Main.instance().GUI.create(inventoryName, size);
        inventory.setAutomaticPaginationEnabled(false);
    }

    public static void reload() {
        inventory.clearAllButStickiedSlots();

        if(!inventory.getName().equals(invName))
            inventory.setName(invName);

        ConfigurationSection keys = Main.instance().database.getConfigurationSection("TOP");
        if(keys.getKeys(false).size() > 0) {
            List<TopBuilder> tops = new ArrayList<>();
            for (String top : keys.getKeys(false))
                tops.add(new TopBuilder(top, SystemTop.getName(UUID.fromString(top)), SystemTop.getPoint(UUID.fromString(top))));

            int posicao = 1;
            int slot = 0;
            Collections.sort(tops, new Decrescente());
            for (TopBuilder staffs : tops) {
                List<String> lore = new ArrayList<>();
                lore.add(" ");
                lore.add(" §e§lInformações:");
                lore.add(" §bPosição: §a" + posicao + "º lugar");
                lore.add(" §bPontos: §a" + staffs.getPoints());
                lore.add("");
                lore.add(" §7> Clique para abrir o menu de opções !");
                ItemStack item = ItemBuilder.getSkull(staffs.getName(), "§7" + posicao + "# §b" + staffs.getName(), posicao, lore);
                inventory.setButton(slot, new SGButton(item)
                        .withListener((InventoryClickEvent event) -> {
                            Player player = (Player) event.getWhoClicked();
                            if(event.getClick().isLeftClick()){
                                if(player.hasPermission("pv.ajuda.top.options")){
                                    SGMenu inv = Main.instance().GUI.create("§6" + staffs.getName() + " §e(Pontos: §6"+SystemTop.getPoint(staffs.getUuid())+"§e)", 3);
                                    inv.setAutomaticPaginationEnabled(false);

                                    inv.setButton(10, new SGButton(ItemBuilder.getItem(Material.STONE_BUTTON, "§4> [Remover]", 1,
                                            "", "§cRemover staff da lista de pontuação !")).withListener((InventoryClickEvent e) -> {
                                                Player player1 = (Player) e.getWhoClicked();
                                                if(event.isLeftClick()) {
                                                    if(player1.hasPermission("pv.ajuda.top.remove")) {
                                                        SystemTop.removeTop(staffs.getUuid());
                                                        player1.sendMessage("§eStaff" + staffs.getName() + " removido do TOP !");
                                                        player1.closeInventory();
                                                        open(player1);
                                                    } else {
                                                        player1.sendMessage(Messages.notPermission);
                                                    }
                                                }
                                    }));

                                    inv.setButton(12, new SGButton(ItemBuilder.getItem(Material.WOOD_BUTTON, "§c> [Resetar]", 1,
                                            "", "§cResetar pontuação do staff !")).withListener((InventoryClickEvent e) -> {
                                                Player player1 = (Player) e.getWhoClicked();
                                                if(event.isLeftClick()) {
                                                    if(player1.hasPermission("pv.ajuda.top.reset.point")) {
                                                        SystemTop.setPoint(staffs.getUuid(), staffs.getName(), 0);
                                                        player1.sendMessage("§aAs pontuações do staff" + staffs.getName() + " foi resetada !");
                                                        player1.closeInventory();
                                                        inv.setName("§6" + staffs.getName() + " §e(Pontos: §6" + SystemTop.getPoint(staffs.getUuid()) + "§e)");
                                                        player1.openInventory(inv.getInventory());
                                                    } else {
                                                        player1.sendMessage(Messages.notPermission);
                                                    }
                                                }
                                    }));

                                    inv.setButton(14, new SGButton(ItemBuilder.getItem(Material.STONE_BUTTON, "§a> [Adicionar]", 1,
                                            "", "§aAdiciona 1 ponto para o staff !")).withListener((InventoryClickEvent e) -> {
                                                Player player1 = (Player) e.getWhoClicked();
                                                if(event.isLeftClick()) {
                                                    if(player1.hasPermission("pv.ajuda.top.add.point")) {
                                                        SystemTop.addPoint(staffs.getUuid(), staffs.getName(), 1);
                                                        player1.sendMessage("§aFoi adicionado 1 ponto para o staff " + staffs.getName());
                                                        player1.closeInventory();
                                                        inv.setName("§6" + staffs.getName() + " §e(Pontos: §6" + SystemTop.getPoint(staffs.getUuid()) + "§e)");
                                                        player1.openInventory(inv.getInventory());
                                                    } else {
                                                        player1.sendMessage(Messages.notPermission);
                                                    }
                                                }
                                    }));

                                    inv.setButton(16, new SGButton(ItemBuilder.getItem(Material.WOOD_BUTTON, "§e> [Retirar]", 1,
                                            "", "§eRetira 1 ponto do staff !")).withListener((InventoryClickEvent e) -> {
                                                Player player1 = (Player) e.getWhoClicked();
                                                if(event.isLeftClick()) {
                                                    if(player1.hasPermission("pv.ajuda.top.remove.point")) {
                                                        if (SystemTop.getPoint(staffs.getUuid()) > 0) {
                                                            SystemTop.removePoint(staffs.getUuid(), staffs.getName(), 1);
                                                            player1.sendMessage("§eFoi removido 1 ponto do staff " + staffs.getName());
                                                            player1.closeInventory();
                                                            inv.setName("§6" + staffs.getName() + " §e(Pontos: §6" + SystemTop.getPoint(staffs.getUuid()) + "§e)");
                                                            player1.openInventory(inv.getInventory());
                                                        } else {
                                                            player1.sendMessage("§cA pontuação do staff " + staffs.getName() + " já está em 0.");
                                                        }
                                                    } else {
                                                        player1.sendMessage(Messages.notPermission);
                                                    }
                                                }
                                    }));

                                    inv.setButton(18, new SGButton(ItemBuilder.getItem(Material.ARROW, "§c<- [Voltar]", 1,
                                            "", "§cClique para voltar para a página dos TOP")).withListener((InventoryClickEvent e) -> {
                                                Player player1 = (Player) e.getWhoClicked();
                                                if(event.isLeftClick()) {
                                                    player1.closeInventory();
                                                    open(player1);
                                                }
                                    }));

                                    player.closeInventory();
                                    player.openInventory(inv.getInventory());
                                } else {
                                    player.sendMessage(Messages.notPermission);
                                }
                            }
                        }));
                posicao++;
                slot++;
            }
        } else {
            inventory.setName("§cNenhum staff no TOP !");
            int[] slots = {10,12,14,16,19,21,23,25,28,30,32,34,37,39,41,43};
            for(int slot : slots)
                inventory.setButton(slot, new SGButton(ItemBuilder.getItem(Material.REDSTONE_BLOCK, "§cN/A", 1)));
        }

        for(Player player : playersOpenInv) {
            player.closeInventory();
            open(player);
        }
    }

    public static void open(Player player) {
        reload();
        player.openInventory(inventory.getInventory());
    }

    public static List<Player> getPlayersOpenInv() {
        return playersOpenInv;
    }

    public static SGMenu getInventory() {
        return inventory;
    }
}
