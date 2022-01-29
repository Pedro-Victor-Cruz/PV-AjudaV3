package pv.ajuda;

import com.samjakob.spigui.SpiGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pv.ajuda.Config.FileYaml;
import pv.ajuda.Config.Messages;
import pv.ajuda.Discord.DiscordWebhook;
import pv.ajuda.Discord.DiscordWebhookBuilder;
import pv.ajuda.Inventory.Event;
import pv.ajuda.Inventory.InventoryRequestList;
import pv.ajuda.Inventory.InventoryTop;
import pv.ajuda.System.Comandos.CommandAjuda;

import java.awt.*;

public class Main extends JavaPlugin {

    public SpiGUI GUI;
    public PluginManager pm;
    public static Main INSTANCE;
    public FileYaml database;
    public FileYaml config;
    public FileYaml discord;

    @Override
    public void onEnable() {

        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + " ============ PV-Ajuda ============");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "PV-Ajuda habilitado com sucesso!");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Criado por: Pedro_Victor");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + Bukkit.getServer().getVersion());
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + " ============ PV-Ajuda ============");

        start();
        reload();
        new InventoryRequestList(InventoryRequestList.invName, 6);
        new InventoryTop(InventoryTop.invName, 6);
        getCommand("ajuda").setExecutor(new CommandAjuda());
        pm.registerEvents(new Event(), this);
        pm.registerEvents(new pv.ajuda.System.Event(), this);
    }

    public void reload() {
        this.database = new FileYaml("db.yml");
        this.config = new FileYaml("settings.yml");
        this.discord = new FileYaml("discord.yml");

        // Mensagens
        Messages.hasOpenRequest = ChatColor.translateAlternateColorCodes('&', config.getString("messages.has-open-request"));
        Messages.requestCancel = ChatColor.translateAlternateColorCodes('&', config.getString("messages.request-cancel"));
        Messages.requestCloseByStaff = ChatColor.translateAlternateColorCodes('&', config.getString("messages.request-close-by-staff"));
        Messages.msgToPlayer = ChatColor.translateAlternateColorCodes('&', config.getString("messages.message-to-player"));
        for(String msg : config.getStringList("messages.message-to-staff"))
            Messages.msgToStaff.add(ChatColor.translateAlternateColorCodes('&', msg));
        Messages.notRequestOpen = ChatColor.translateAlternateColorCodes('&', config.getString("messages.not-request-open"));
        Messages.requestAcceptByStaff = ChatColor.translateAlternateColorCodes('&', config.getString("messages.request-accept-by-staff"));
        Messages.requestAccept = ChatColor.translateAlternateColorCodes('&', config.getString("messages.request-accept"));

        for(String cmds : config.getStringList("commands"))
            Messages.msgCmds.add(ChatColor.translateAlternateColorCodes('&', cmds));

        Messages.notPermission = ChatColor.translateAlternateColorCodes('&', config.getString("messages.not-permission"));
        Messages.mute = ChatColor.translateAlternateColorCodes('&', config.getString("messages.mute"));
        Messages.unmute = ChatColor.translateAlternateColorCodes('&', config.getString("messages.unmute"));
        Messages.receivedMute = ChatColor.translateAlternateColorCodes('&', config.getString("messages.received-mute"));

        // Inventorys
        InventoryRequestList.invName = ChatColor.translateAlternateColorCodes('&', config.getString("inventorys.request-list.inventory-name"));
        InventoryTop.invName = ChatColor.translateAlternateColorCodes('&', config.getString("inventorys.top.inventory-name"));
    }

    public void start() {
        INSTANCE = this;
        GUI = new SpiGUI(this);
        pm = Bukkit.getPluginManager();
    }

    public static Main instance() {
        return INSTANCE;
    }

}
