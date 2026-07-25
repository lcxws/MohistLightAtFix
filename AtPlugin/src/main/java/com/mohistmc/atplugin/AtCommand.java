package com.mohistmc.atplugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AtCommand implements CommandExecutor {

    private final AtPlugin plugin;

    public AtCommand(AtPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "只有玩家可以使用此命令");
            return true;
        }
        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "用法: /at <玩家名> [消息]");
            return true;
        }

        Player player = (Player) sender;
        String targetName = args[0];
        
        // 拼接消息（如果有的话）
        String message = null;
        if (args.length >= 2) {
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                if (i > 1) sb.append(" ");
                sb.append(args[i]);
            }
            message = sb.toString();
        }
        
        String err = plugin.doAt(player, targetName, message);
        if (err != null) {
            player.sendMessage(err);
        }
        return true;
    }
}
