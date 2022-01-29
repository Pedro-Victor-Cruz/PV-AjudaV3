package pv.ajuda.System;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pv.ajuda.Config.Messages;
import pv.ajuda.Discord.DiscordWebhook;
import pv.ajuda.Discord.DiscordWebhookBuilder;
import pv.ajuda.Discord.Embeds;
import pv.ajuda.Inventory.InventoryRequestList;
import pv.ajuda.Inventory.InventoryTop;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class SystemHelp {

    private static int maxRequest = 36;

    public static ArrayList<Player> openRequests = new ArrayList<>();

    public static void createRequest(Player player) {
        if(!openRequests.contains(player)){
            if(openRequests.size() < maxRequest) {
                openRequests.add(player);
                InventoryRequestList.reload();
                for (Player players : Bukkit.getOnlinePlayers()) {
                    if (players.hasPermission("pv.ajuda")) {
                        Messages.msgToStaff.forEach(msg -> {
                            if (msg.contains("[AQUI]")) {
                                TextComponent text = new TextComponent(msg);
                                text.setClickEvent(new ClickEvent(
                                        ClickEvent.Action.RUN_COMMAND,
                                        "/ajuda aceitar " + player.getDisplayName()
                                ));
                                text.setHoverEvent(new HoverEvent(
                                        HoverEvent.Action.SHOW_TEXT,
                                        new ComponentBuilder("§aClique para aceitar o pedido de ajuda !").create()
                                ));
                                players.spigot().sendMessage(text);
                            } else {
                                players.sendMessage(msg.replace("{world}", player.getWorld().getName()).replace("{player}", player.getDisplayName()));
                            }
                        });
                    }
                }
                player.sendMessage(Messages.msgToPlayer);
                DiscordWebhook.sendDiscordEmbed(Embeds.createRequest(player, player.getWorld().getName()));
            }
        } else {
            player.sendMessage(Messages.hasOpenRequest);
        }
    }

    public static void cancelRequest(Player player) {
        if(openRequests.contains(player)) {
            removeRequest(player);
            player.sendMessage(Messages.requestCancel);
        }
    }

    public static void removeRequest(Player player) {
        if(openRequests.contains(player)) {
            openRequests.remove(player);
            InventoryRequestList.reload();
        }
    }

    public static void removeRequest(Player staff, Player player) {
        removeRequest(player);
        for(Player staffs : Bukkit.getOnlinePlayers())
            if(staffs.hasPermission("pv.ajuda"))
                staffs.sendMessage(Messages.requestCloseByStaff.replace("{player}", player.getDisplayName()).replace("{staff}", staff.getDisplayName()));
        DiscordWebhook.sendDiscordEmbed(Embeds.removeRequest(staff, player));
    }

    public static void removeAllRequest(){
        if(openRequests.isEmpty()) return;
        openRequests.clear();
        InventoryRequestList.reload();
    }

    public static void acceptRequest(Player staff, Player player) {
        if(staff.hasPermission("pv.ajuda")) {
            if(openRequests.contains(player)) {
                if(!player.equals(staff) || player.hasPermission("pv.ajuda.owner")) {
                    staff.teleport(player);
                    for (Player players : Bukkit.getOnlinePlayers())
                        if (players.hasPermission("pv.ajuda"))
                            players.sendMessage(Messages.requestAcceptByStaff.replace("{player}", player.getDisplayName()).replace("{staff}", staff.getDisplayName()));
                    player.sendMessage(Messages.requestAccept.replace("{staff}", staff.getDisplayName()));
                    removeRequest(player);
                    SystemTop.addPoint(staff, 1);
                    InventoryTop.reload();
                    DiscordWebhook.sendDiscordEmbed(Embeds.acceptRequest(staff, player, SystemTop.getPoint(staff)));
                } else {
                    staff.sendMessage("§cVocê não pode aceita o seu pedido de ajdua !");
                }
            } else {
                staff.sendMessage(Messages.notRequestOpen);
            }
        }
    }
}
