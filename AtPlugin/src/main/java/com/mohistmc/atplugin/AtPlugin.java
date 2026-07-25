package com.mohistmc.atplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class AtPlugin extends JavaPlugin {

    private static AtPlugin instance;

    private boolean enabled;
    private String titleText;
    private String subtitleFormat;
    private boolean soundEnabled;
    private String soundName;
    private float soundVolume;
    private float soundPitch;
    private boolean logToConsole;

    // 冷却: UUID -> 上次使用时间戳
    private final Map<UUID, Long> cooldowns = new HashMap<>();
    private int cooldownSeconds;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        reloadPluginConfig();

        Objects.requireNonNull(getCommand("at")).setExecutor(new AtCommand(this));
        getCommand("atplugin").setExecutor(new AtPluginCommand(this));
        getServer().getPluginManager().registerEvents(new AtListener(this), this);

        getLogger().info("@插件已启用 (/at <玩家>)");
    }

    @Override
    public void onDisable() {
        getLogger().info("@插件已卸载");
    }

    public void reloadPluginConfig() {
        reloadConfig();
        enabled = getConfig().getBoolean("enabled", true);
        titleText = ChatColor.translateAlternateColorCodes('&',
                getConfig().getString("title.text", "&e&l你被 @ 了！"));
        subtitleFormat = ChatColor.translateAlternateColorCodes('&',
                getConfig().getString("title.subtitle-format", "&f来自 &b%s &f的消息"));
        soundEnabled = getConfig().getBoolean("sound.enabled", true);
        soundName = getConfig().getString("sound.sound", "entity.experience_orb.pickup");
        soundVolume = (float) getConfig().getDouble("sound.volume", 1.0);
        soundPitch = (float) getConfig().getDouble("sound.pitch", 1.0);
        logToConsole = getConfig().getBoolean("log-to-console", true);
        cooldownSeconds = getConfig().getInt("cooldown-seconds", 3);
    }

    /**
     * 执行 @ 操作
     * @param message 可选的消息内容
     * @return null=成功, String=错误消息
     */
    String doAt(Player sender, String targetName, String message) {
        if (!enabled) return "§c@插件已禁用";

        // 冷却检查
        UUID uid = sender.getUniqueId();
        long now = System.currentTimeMillis() / 1000;
        Long last = cooldowns.get(uid);
        if (last != null && (now - last) < cooldownSeconds) {
            long remain = cooldownSeconds - (now - last);
            return "§c冷静一下！" + remain + " 秒后才能再 @";
        }

        // 查找目标
        Player target = Bukkit.getPlayer(targetName);
        if (target == null) {
            // 尝试部分匹配
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.getName().toLowerCase().startsWith(targetName.toLowerCase())) {
                    target = p;
                    break;
                }
            }
        }
        if (target == null || !target.isOnline()) {
            return "§c玩家 " + targetName + " 不在线";
        }
        if (target.equals(sender)) {
            return "§c不能 @ 自己";
        }

        // 执行 @
        cooldowns.put(uid, now);
        sendAtNotification(sender, target, message);
        return null;
    }

    private void sendAtNotification(Player sender, Player target, String message) {
        // 弹窗
        target.sendTitle(titleText, String.format(subtitleFormat, sender.getName()), 10, 70, 20);

        // 被 @ 者消息 - 如果有消息则显示消息内容
        if (message != null && !message.isEmpty()) {
            // 显示 @ 通知 + 消息内容
            String atMsg = getConfig().getString("chat.at-message",
                    "&e[@] &b%s&7: &f%s");
            target.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    atMsg.replace("%s", sender.getName()).replace("%m", message)));
        } else {
            // 仅 @ 通知（无消息）
            String notifyMsg = getConfig().getString("chat.notify-message",
                    "&e[!] &b%s &e@了你！");
            target.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    notifyMsg.replace("%s", sender.getName())));
        }

        // 发送者消息
        String senderMsg = getConfig().getString("chat.sender-message",
                "&a✓ 已 @ %s");
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                senderMsg.replace("%s", target.getName())));

        // 音效
        if (soundEnabled) {
            try {
                Sound sound = Sound.valueOf(soundName.toUpperCase().replace('.', '_'));
                target.playSound(target.getLocation(), sound, soundVolume, soundPitch);
            } catch (Exception ignored) {}
        }

        if (logToConsole) {
            if (message != null && !message.isEmpty()) {
                getLogger().info(sender.getName() + " @了 " + target.getName() + " 消息: " + message);
            } else {
                getLogger().info(sender.getName() + " @了 " + target.getName());
            }
        }
    }

    public static AtPlugin inst() { return instance; }
    public boolean isFeatureEnabled() { return enabled; }
}
