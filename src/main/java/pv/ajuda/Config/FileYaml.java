package pv.ajuda.Config;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import pv.ajuda.Main;

import java.io.File;
import java.util.List;

public class FileYaml {

    private FileConfiguration config;
    private File file;

    public FileYaml(String name) {
        this.file = new File(Main.instance().getDataFolder(), name);
        if(!Main.instance().getDataFolder().exists())
            Main.instance().getDataFolder().mkdir();
        if(!file.exists())
            try {
                Main.instance().saveResource(name, false);
            } catch (Exception e) {
                System.out.println(e);
            }
        this.config = (FileConfiguration) YamlConfiguration.loadConfiguration(file);
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "O arquivo " + name + " foi carregado com sucesso !");
    }

    public int getInt(String path) {
        return this.config.getInt(path);
    }

    public String getString(String path) {
        return this.config.getString(path);
    }

    public List<String> getStringList(String path) {
        return this.config.getStringList(path);
    }

    public double getDouble(String path) {
        return this.config.getDouble(path);
    }

    public boolean getBoolean(String path) {
        return this.config.getBoolean(path);
    }

    public void set(String path, Object valor) {
        this.config.set(path, valor);
        saveConfig();
        reloadConfig();
    }

    public void setDefault(String path, Object valor) {
        if (!this.config.contains(path))
            set(path, valor);
    }

    public boolean contains(String path) {
        return this.config.contains(path);
    }

    public List<?> getList(String path) {
        return this.config.getList(path);
    }

    private FileConfiguration getFileConfiguration() {
        return this.config;
    }

    public ConfigurationSection getConfigurationSection(String path) {
        return this.config.getConfigurationSection(path);
    }

    public void saveConfig() {
        try {
            this.config.save(this.file);
        } catch (Exception exception) {}
    }

    public void saveDefault() {
        try {
            this.config.options().copyDefaults(true);
            this.config.save(this.file);
        } catch (Exception exception) {}
    }

    public void saveDefaultConfig(boolean replace) {
        Main.instance().saveResource(this.file.getPath(), replace);
    }

    public void reloadConfig() {
        this.config = (FileConfiguration)YamlConfiguration.loadConfiguration(this.file);
    }
}
