package pv.ajuda.Discord;

import pv.ajuda.Main;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;

public class DiscordWebhook {

    private static String url = Main.instance().discord.getString("Discord.webhook-url");
    private static String webhookName = Main.instance().discord.getString("Discord.name");
    private static String avatarURL = Main.instance().discord.getString("Discord.avatar-url");
    private static boolean active = Main.instance().discord.getBoolean("Discord.active");

    public static void sendDiscordMessage(String message) {
        if(active == false) return;
        DiscordWebhookBuilder webhook = new DiscordWebhookBuilder(url);
        webhook.setUsername(webhookName);
        webhook.setAvatarUrl(avatarURL);
        webhook.setContent(message);
        try {
            webhook.execute();
        } catch (MalformedURLException e) {
            System.out.println("[PV-Ajuda] Invalid webhook URL");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendDiscordEmbed(DiscordWebhookBuilder.EmbedObject embed) {
        if(active == false) return;
        DiscordWebhookBuilder webhook = new DiscordWebhookBuilder(url);
        webhook.setUsername(webhookName);
        webhook.setAvatarUrl(avatarURL);
        webhook.addEmbed(embed);
        try {
            webhook.execute();
        } catch (MalformedURLException e) {
            System.out.println("[PV-Ajuda] Invalid webhook URL");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
