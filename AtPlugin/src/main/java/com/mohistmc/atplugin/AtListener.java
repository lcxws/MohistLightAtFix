package com.mohistmc.atplugin;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AtListener implements Listener {

    private final AtPlugin plugin;

    public AtListener(AtPlugin plugin) {
        this.plugin = plugin;
    }

    // 聊天 @ 功能已移除，仅保留指令 /at 方式
    // 防止其他插件冲突，保留空 Listener
}
