# PluginManagerX

PluginManagerX is a Bukkit plugin for managing plugins on your Minecraft server. It provides commands to enable, disable, reload, and get information about plugins. Additionally, it allows you to list all plugins in the server's plugin folder.

## Commands

- **/pmx help**: Show help message.
- **/pmx list**: List all plugins in the folder.
- **/pmx enable \<plugin\>**: Enable a plugin.
- **/pmx disable \<plugin\>**: Disable a plugin.
- **/pmx reload \<plugin\>**: Reload a plugin.
- **/pmx unreload \<plugin\>**: Unload and reload a plugin.
- **/pmx info \<plugin\>**: Show information about a plugin.

## How to Use

1. **List Plugins**: Use the `/pmx list` command to see all the plugins in the server's plugin folder.

2. **Enable a Plugin**: To enable a plugin, type `/pmx enable <plugin>` in the game. Replace `<plugin>` with the name of the plugin you want to enable.

3. **Disable a Plugin**: To disable a plugin, type `/pmx disable <plugin>` in the game. Replace `<plugin>` with the name of the plugin you want to disable.

4. **Reload a Plugin**: To reload a plugin, type `/pmx reload <plugin>` in the game. Replace `<plugin>` with the name of the plugin you want to reload.

5. **Unload and Reload a Plugin**: To unload and reload a plugin, type `/pmx unreload <plugin>` in the game. Replace `<plugin>` with the name of the plugin you want to unload and reload.

6. **Get Plugin Information**: To get information about a plugin, type `/pmx info <plugin>` in the game. Replace `<plugin>` with the name of the plugin you want to get information about.

## Examples

- **List Plugins**: `/pmx list`
- **Enable a Plugin**: `/pmx enable MyPlugin`
- **Disable a Plugin**: `/pmx disable MyPlugin`
- **Reload a Plugin**: `/pmx reload MyPlugin`
- **Unload and Reload a Plugin**: `/pmx unreload MyPlugin`
- **Get Plugin Information**: `/pmx info MyPlugin`

## Notes

- You cannot unload a plugin if it's still enabled or if it has been modified after enabling.
- Please check the server console for errors if enabling a plugin fails.

Feel free to use PluginManagerX to efficiently manage plugins on your Minecraft server!
