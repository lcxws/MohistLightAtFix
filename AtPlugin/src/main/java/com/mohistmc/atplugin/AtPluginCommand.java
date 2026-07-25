package com.mohistmc.atplugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class AtPluginCommand implements CommandExecutor {

    private final AtPlugin plugin;

    public AtPluginCommand(AtPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("atplugin.admin")) {
            sender.sendMessage(ChatColor.RED + "你没有权限使用此命令");
            return true;
        }

        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "reload":
                plugin.reloadPluginConfig();
                sender.sendMessage(ChatColor.GREEN + "@插件配置已重载！");
                break;
            case "status":
                sender.sendMessage(ChatColor.AQUA + "=== @插件状态 ===");
                sender.sendMessage(ChatColor.GRAY + "启用: " +
                        (plugin.isFeatureEnabled() ? ChatColor.GREEN + "是" : ChatColor.RED + "否"));
                sender.sendMessage(ChatColor.GRAY + "在线玩家: " +
                        ChatColor.YELLOW + String.valueOf(plugin.getServer().getOnlinePlayers().size()));
                sender.sendMessage(ChatColor.GRAY + "版本: " +
                        ChatColor.YELLOW + plugin.getDescription().getVersion());
                break;
            case "help":
            default:
                sendHelp(sender);
                break;
        }
        return true;
    }

    private void sendHelp(CommandSender sender) {
        List<String> help = List.of(
                ChatColor.AQUA + "=== @插件 帮助 ===",
                ChatColor.GRAY + "/atplugin reload " + ChatColor.YELLOW + "- 重载配置",
                ChatColor.GRAY + "/atplugin status " + ChatColor.YELLOW + "- 查看状态",
                ChatColor.GRAY + "/atplugin help " + ChatColor.YELLOW + "- 显示帮助",
                "",
                ChatColor.GRAY + "使用方式: " + ChatColor.YELLOW + "在聊天中输入 @玩家名"
        );
        help.forEach(sender::sendMessage);
    }
}
