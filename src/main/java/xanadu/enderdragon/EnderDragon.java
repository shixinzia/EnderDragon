package xanadu.enderdragon;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import xanadu.enderdragon.commands.MainCommand;
import xanadu.enderdragon.commands.TabCompleter;
import xanadu.enderdragon.events.*;
import xanadu.enderdragon.listeners.InventoryClick;
import xanadu.enderdragon.metrics.Metrics;

import java.io.File;

public final class EnderDragon extends JavaPlugin {

    public static String prefix = "";
    public static Plugin plugin;
    public static Server server;
    public static PluginManager pm;
    public static File data0;
    public static File language0;
    public static FileConfiguration data;
    public static FileConfiguration language;

    @Override
    public void onEnable() {
        plugin = this;
        server = plugin.getServer();
        pm = Bukkit.getPluginManager();
        if (!new File(getDataFolder(), "config.yml").exists()) {
            saveDefaultConfig();
            Bukkit.getConsoleSender().sendMessage("§a[EnderDragon] 未检测到config.yml文件，正在生成新的配置文件");
        }
        if (!new File(getDataFolder(), "data.yml").exists()) {
            this.saveResource("data.yml",false);
            Bukkit.getConsoleSender().sendMessage("§a[EnderDragon] 未检测到data.yml文件，正在生成新的配置文件");
        }
        if (!new File(getDataFolder(), "language.yml").exists()) {
            this.saveResource("language.yml",false);
            Bukkit.getConsoleSender().sendMessage("§a[EnderDragon] 未检测到language.yml文件，正在生成新的配置文件");
        }
        data0 = new File(plugin.getDataFolder(),"data.yml");
        language0 = new File(plugin.getDataFolder(),"language.yml");
        data = YamlConfiguration.loadConfiguration(data0);
        language = YamlConfiguration.loadConfiguration(language0);
        prefix = language.getString("prefix");
        if(!getConfig().getString("version").equals("1.8.2") ){
            Bukkit.getConsoleSender().sendMessage("§c[EnderDragon] config.yml版本与插件不对应，请更新配置文件");
        }
        if(!data.getString("version").equals("1.8.1") ){
            Bukkit.getConsoleSender().sendMessage("§c[EnderDragon] data.yml版本与插件不对应，请更新配置文件");
        }
        if(!language.getString("version").equals("1.8") ){
            Bukkit.getConsoleSender().sendMessage("§c[EnderDragon] language.yml版本与插件不对应，请更新配置文件");
        }
        Bukkit.getConsoleSender().sendMessage("§a[EnderDragon] 插件已加载");
        Bukkit.getConsoleSender().sendMessage("§a[EnderDragon] 作者：Xanadu13");
        pm.registerEvents(new CreatureHurt(),this);
        pm.registerEvents(new DragonAttack(),this);
        pm.registerEvents(new DragonDeath(),this);
        pm.registerEvents(new DragonHeal(),this);
        pm.registerEvents(new DragonSpawn(),this);
        pm.registerEvents(new InventoryClick(),this);
        getCommand("enderdragon").setExecutor(new MainCommand());
        getCommand("enderdragon").setTabCompleter(new TabCompleter());
        Metrics metrics = new Metrics(this,14850);
    }

    @Override
    public void onDisable()
    {
        Bukkit.getConsoleSender().sendMessage("§e[EnderDragon] 插件已卸载");
    }


    public static String itemStackToString(ItemStack itemStack) {
        YamlConfiguration yml = new YamlConfiguration();
        yml.set("item", itemStack);
        return yml.saveToString();
    }
    public static ItemStack StringToItemStack(String str) {
        YamlConfiguration yml = new YamlConfiguration();
        ItemStack item;
        try {
            yml.loadFromString(str);
            item = yml.getItemStack("item");
        } catch (InvalidConfigurationException ex) {
            item = new ItemStack(Material.AIR, 1);
        }
        return item;
    }

}


