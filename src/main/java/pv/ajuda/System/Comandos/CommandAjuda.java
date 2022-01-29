package pv.ajuda.System.Comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pv.ajuda.Config.Messages;
import pv.ajuda.Discord.DiscordWebhook;
import pv.ajuda.Discord.Embeds;
import pv.ajuda.Inventory.InventoryRequestList;
import pv.ajuda.Inventory.InventoryTop;
import pv.ajuda.System.SystemHelp;
import pv.ajuda.System.SystemMute;

public class CommandAjuda implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length > 0) {
                if (args[0].equalsIgnoreCase("aceitar")) {
                    if(!player.hasPermission("pv.ajuda")) {
                        player.sendMessage(Messages.notPermission);
                        return true;
                    }
                    if(args.length >= 1) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if(target != null) {
                            SystemHelp.acceptRequest(player, target);
                        }
                    }
                } else if (args[0].equalsIgnoreCase("top")) {
                    if(!player.hasPermission("pv.ajuda.top")) {
                        player.sendMessage(Messages.notPermission);
                        return true;
                    }
                    InventoryTop.open(player);
                } else if (args[0].equalsIgnoreCase("lista")) {
                    if(!player.hasPermission("pv.ajuda.list")) {
                        player.sendMessage(Messages.notPermission);
                        return true;
                    }
                    InventoryRequestList.open(player);
                } else if (args[0].equalsIgnoreCase("help")) {
                    if(!player.hasPermission("pv.ajuda")) {
                        player.sendMessage(Messages.notPermission);
                        return true;
                    }
                    for(String cmds : Messages.msgCmds)
                        player.sendMessage(cmds);
                } else if (args[0].equalsIgnoreCase("mute")) {
                    if(!player.hasPermission("pv.ajuda.mute")) {
                        player.sendMessage(Messages.notPermission);
                        return true;
                    }
                    if(args.length == 3){
                        String time = args[2];
                        Player target = Bukkit.getPlayer(args[1]);
                        if(target != null) {
                            if(!SystemMute.isMute(target.getUniqueId())){
                                SystemMute.mute(target.getUniqueId(), Integer.parseInt(time));
                                player.sendMessage("§bVocê mutou §a" + target.getDisplayName() + " §bpor §a" + time + " §bminutos !");
                                target.sendMessage(Messages.receivedMute.replace("{staff}", player.getDisplayName()).replace("{temp}", time + " minutos"));
                                DiscordWebhook.sendDiscordEmbed(Embeds.mute(player, target, time));
                            } else {
                                player.sendMessage("§4{!} §cO player já está mutado !");
                            }
                        } else {
                            player.sendMessage("§4{!} §cJogador está offline ou não existe !");
                        }
                    }
                } else if (args[0].equalsIgnoreCase("unmute")) {
                    if(!player.hasPermission("pv.ajuda.unmute")) {
                        player.sendMessage(Messages.notPermission);
                        return true;
                    }
                    if(args.length == 2){
                        Player target = Bukkit.getPlayer(args[1]);
                        if(target != null) {
                            if(SystemMute.isMute(target.getUniqueId())){
                                player.sendMessage("§bPlayer §a" + target.getDisplayName() + " §bfoi desmutado !");
                                target.sendMessage(Messages.unmute.replace("{staff}", player.getDisplayName()));
                                SystemMute.unmute(target.getUniqueId());
                                DiscordWebhook.sendDiscordEmbed(Embeds.unmute(player, target));
                            } else {
                                player.sendMessage("§4{!} §cO player não está mutado !");
                            }
                        } else {
                            player.sendMessage("§4{!} §cJogador está offline ou não existe !");
                        }
                    }
                }
                if(args[0].equalsIgnoreCase("cancelar")) {
                    SystemHelp.cancelRequest(player);
                }
            } else {
                if(SystemMute.isMute(player.getUniqueId())){
                    long temp = SystemMute.getTempLeft(player.getUniqueId());
                    if(temp > 60) {
                        long minutos = temp / 60;
                        long segundos = temp - (minutos * 60);
                        player.sendMessage(Messages.mute.replace("{temp}", minutos + " minuto e " + segundos + " segundos"));
                    } else {
                        player.sendMessage(Messages.mute.replace("{temp}", temp + " segundos"));
                    }
                } else {
                    SystemHelp.createRequest(player);
                }
            }
        }
        return true;
    }

}
