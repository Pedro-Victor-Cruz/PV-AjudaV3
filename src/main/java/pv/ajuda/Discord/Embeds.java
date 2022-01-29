package pv.ajuda.Discord;

import org.bukkit.entity.Player;
import pv.ajuda.Config.FileYaml;
import pv.ajuda.Main;

import javax.swing.text.PlainDocument;
import java.awt.*;
import java.util.List;

public class Embeds {

    private static FileYaml discord = Main.instance().discord;

    public static DiscordWebhookBuilder.EmbedObject removeRequest(Player staff, Player player) {
        DiscordWebhookBuilder.EmbedObject embed = new DiscordWebhookBuilder.EmbedObject();
        String path = "Embeds.remove-request.";

        embed.setTitle(discord.getString("Embeds.remove-request.title"));
        embed.addField(discord.getString(path + "field1.name"), discord.getString(path + "field1.value").replace("{staff}", staff.getDisplayName()), discord.getBoolean(path + "field1.inline"));
        embed.addField(discord.getString(path + "field2.name"), discord.getString(path + "field2.value").replace("{player}", player.getDisplayName()), discord.getBoolean(path + "field2.inline"));
        embed.setColor(new Color(discord.getInt(path + "color.r"), discord.getInt(path + "color.g"), discord.getInt(path + "color.b")));
        embed.setFooter(discord.getString(path + "footer.name"), discord.getString(path + "footer.url-image"));
        return embed;
    }

    public static DiscordWebhookBuilder.EmbedObject createRequest(Player player, String world){
        DiscordWebhookBuilder.EmbedObject embed = new DiscordWebhookBuilder.EmbedObject();
        String path = "Embeds.create-request.";

        embed.setTitle(discord.getString("Embeds.create-request.title"));
        embed.addField(discord.getString(path + "field1.name"), discord.getString(path + "field1.value").replace("{player}", player.getDisplayName()), discord.getBoolean(path + "field1.inline"));
        embed.addField(discord.getString(path + "field2.name"), discord.getString(path + "field2.value").replace("{world}", world), discord.getBoolean(path + "field2.inline"));
        embed.setColor(new Color(discord.getInt(path + "color.r"), discord.getInt(path + "color.g"), discord.getInt(path + "color.b")));
        embed.setFooter(discord.getString(path + "footer.name"), discord.getString(path + "footer.url-image"));

        return embed;
    }

    public static DiscordWebhookBuilder.EmbedObject acceptRequest(Player staff, Player player, int point){
        DiscordWebhookBuilder.EmbedObject embed = new DiscordWebhookBuilder.EmbedObject();
        String path = "Embeds.accept-request.";

        embed.setTitle(discord.getString("Embeds.accept-request.title"));
        embed.addField(discord.getString(path + "field1.name"), discord.getString(path + "field1.value").replace("{player}", player.getDisplayName()), discord.getBoolean(path + "field1.inline"));
        embed.addField(discord.getString(path + "field2.name"), discord.getString(path + "field2.value").replace("{staff}", staff.getDisplayName()), discord.getBoolean(path + "field2.inline"));
        embed.addField(discord.getString(path + "field3.name"), discord.getString(path + "field3.value").replace("{point}", String.valueOf(point)), discord.getBoolean(path + "field3.inline"));
        embed.setColor(new Color(discord.getInt(path + "color.r"), discord.getInt(path + "color.g"), discord.getInt(path + "color.b")));
        embed.setFooter(discord.getString(path + "footer.name"), discord.getString(path + "footer.url-image"));

        return embed;
    }

    public static DiscordWebhookBuilder.EmbedObject mute(Player staff, Player player, String time){
        DiscordWebhookBuilder.EmbedObject embed = new DiscordWebhookBuilder.EmbedObject();
        String path = "Embeds.mute.";

        embed.setTitle(discord.getString("Embeds.mute.title"));
        embed.addField(discord.getString(path + "field1.name"), discord.getString(path + "field1.value").replace("{staff}", staff.getDisplayName()), discord.getBoolean(path + "field1.inline"));
        embed.addField(discord.getString(path + "field2.name"), discord.getString(path + "field2.value").replace("{player}", player.getDisplayName()), discord.getBoolean(path + "field2.inline"));
        embed.addField(discord.getString(path + "field3.name"), discord.getString(path + "field3.value").replace("{temp}",time + " minutos"), discord.getBoolean(path + "field3.inline"));
        embed.setColor(new Color(discord.getInt(path + "color.r"), discord.getInt(path + "color.g"), discord.getInt(path + "color.b")));
        embed.setFooter(discord.getString(path + "footer.name"), discord.getString(path + "footer.url-image"));

        return embed;
    }

    public static DiscordWebhookBuilder.EmbedObject unmute(Player staff, Player player){
        DiscordWebhookBuilder.EmbedObject embed = new DiscordWebhookBuilder.EmbedObject();
        String path = "Embeds.unmute.";

        embed.setTitle(discord.getString("Embeds.unmute.title"));
        embed.addField(discord.getString(path + "field1.name"), discord.getString(path + "field1.value").replace("{staff}", staff.getDisplayName()), discord.getBoolean(path + "field1.inline"));
        embed.addField(discord.getString(path + "field2.name"), discord.getString(path + "field2.value").replace("{player}", player.getDisplayName()), discord.getBoolean(path + "field2.inline"));
        embed.setColor(new Color(discord.getInt(path + "color.r"), discord.getInt(path + "color.g"), discord.getInt(path + "color.b")));
        embed.setFooter(discord.getString(path + "footer.name"), discord.getString(path + "footer.url-image"));

        return embed;
    }
}
