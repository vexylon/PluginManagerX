package org.legacity.pluginmanagerx;

import javafx.scene.media.AudioEqualizer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Main extends JavaPlugin {

    private final Map<String, Boolean> pluginStates = new HashMap<>();

    @Override
    public void onEnable() {
        getLogger().info("PluginManagerX has been enabled!");
        getCommand("pmx").setExecutor(this);
        }


    @Override
    public void onDisable() {
        getLogger().info("PluginManagerX has been disabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("pmx")) {
            if (args.length >= 1) {
                String subCommand = args[0];
                if (subCommand.equalsIgnoreCase("list")) {
                    getServer().getScheduler().runTaskAsynchronously(this, () -> listPlugins(sender));
                } else if (args.length >= 2) {
                    String pluginName = args[1];
                    getServer().getScheduler().runTaskAsynchronously(this, () -> processCommand(sender, subCommand, pluginName));
                } else {
                    sender.sendMessage(ChatColor.YELLOW + "Usage: /pmx <list|enable|disable|reload|unreload|info> <plugin>");
                }
            } else {
                getServer().getScheduler().runTaskAsynchronously(this, () -> sendHelpMessage(sender));
            }
            return true;
        }
        return false;
    }

    private void listPlugins(CommandSender sender) {
        File pluginFolder = new File(getServer().getPluginManager().getPlugins()[0].getDataFolder().getParent());
        File[] files = pluginFolder.listFiles();

        if (files != null) {
            sender.sendMessage(ChatColor.GOLD + "Plugins in Folder:");
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".jar")) {
                    sender.sendMessage(ChatColor.AQUA + file.getName());
                }
            }
        }
    }

    private void processCommand(CommandSender sender, String subCommand, String pluginName) {
        Plugin targetPlugin = getServer().getPluginManager().getPlugin(pluginName);

        if (targetPlugin == null) {
            sender.sendMessage(ChatColor.RED + "Plugin not found: " + pluginName);
            return;
        }

        switch (subCommand.toLowerCase()) {
            case "enable":
                enablePlugin(sender, targetPlugin);
                break;
            case "disable":
                disablePlugin(sender, targetPlugin);
                break;
            case "reload":
                reloadPlugin(sender, targetPlugin);
                break;
            case "unreload":
                unreloadPlugin(sender, targetPlugin, pluginName);
                break;
            case "info":
                sendPluginInfo(sender, targetPlugin);
                break;
            default:
                sender.sendMessage(ChatColor.RED + "Unknown sub-command: " + subCommand);
                break;
        }
    }

    private void enablePlugin(CommandSender sender, Plugin plugin) {
        if (!plugin.isEnabled()) {
            getServer().getPluginManager().enablePlugin(plugin);
            if (plugin.isEnabled()) {
                sender.sendMessage(ChatColor.GREEN + plugin.getName() + " has been enabled.");
            } else {
                sender.sendMessage(ChatColor.RED + "Failed to enable " + plugin.getName() + ". Please check the server console for errors.");
            }
        } else {
            sender.sendMessage(ChatColor.YELLOW + plugin.getName() + " is already enabled.");
        }
    }


    private void disablePlugin(CommandSender sender, Plugin plugin) {
        if (plugin.isEnabled()) {
            getServer().getPluginManager().disablePlugin(plugin);
            sender.sendMessage(ChatColor.RED + plugin.getName() + " has been disabled.");
        } else {
            sender.sendMessage(ChatColor.YELLOW + plugin.getName() + " is already disabled.");
        }
    }

    private void reloadPlugin(CommandSender sender, Plugin plugin) {
        if (plugin.isEnabled()) {
            getServer().getPluginManager().disablePlugin(plugin);
            getServer().getPluginManager().enablePlugin(plugin);
            sender.sendMessage(ChatColor.GREEN + plugin.getName() + " has been reloaded.");
        } else {
            sender.sendMessage(ChatColor.RED + plugin.getName() + " is disabled. Enable it before reloading.");
        }
    }

    private void unreloadPlugin(CommandSender sender, Plugin plugin, String pluginName) {
        if (plugin.isEnabled()) {
            sender.sendMessage(ChatColor.RED + "Plugin " + pluginName + " is still enabled. Disable it before unreloading.");
        } else if (pluginStates.containsKey(pluginName) && pluginStates.get(pluginName)) {
            sender.sendMessage(ChatColor.RED + "Plugin " + pluginName + " has been modified after enabling. Unload it instead.");
        } else {
            getServer().getPluginManager().disablePlugin(plugin);
            getServer().getPluginManager().enablePlugin(plugin);
            sender.sendMessage(ChatColor.GREEN + pluginName + " has been unloaded and unreloaded.");
        }
    }

    private void sendPluginInfo(CommandSender sender, Plugin plugin) {
        sender.sendMessage(ChatColor.GOLD + "Plugin Information for " + plugin.getName() + ":");
        sender.sendMessage(ChatColor.AQUA + "Version: " + plugin.getDescription().getVersion());
        sender.sendMessage(ChatColor.AQUA + "Authors: " + String.join(", ", plugin.getDescription().getAuthors()));
        sender.sendMessage(ChatColor.AQUA + "Description: " + plugin.getDescription().getDescription());
    }

    private void sendHelpMessage(CommandSender sender) {
        sender.sendMessage(ChatColor.GOLD + "PluginManagerX Commands:");
        sender.sendMessage(ChatColor.AQUA + "/pmx help - Show this help message.");
        sender.sendMessage(ChatColor.AQUA + "/pmx list - List all plugins in the folder.");
        sender.sendMessage(ChatColor.AQUA + "/pmx enable <plugin> - Enable a plugin.");
        sender.sendMessage(ChatColor.AQUA + "/pmx disable <plugin> - Disable a plugin.");
        sender.sendMessage(ChatColor.AQUA + "/pmx reload <plugin> - Reload a plugin.");
        sender.sendMessage(ChatColor.AQUA + "/pmx unreload <plugin> - Unload and reload a plugin.");
        sender.sendMessage(ChatColor.AQUA + "/pmx info <plugin> - Show information about a plugin.");
    }
}
