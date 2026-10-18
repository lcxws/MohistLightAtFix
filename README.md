# MohistLightAtFix（AtPlugin）

一个为 **Mohist 核心**量身定制的轻量级 @ 提及插件。
`/at <玩家名> [消息]` —— 被 @ 的玩家屏幕中央弹出标题提醒（可选音效），适合朋友服里"喊人"。

## ✨ 特性

- **/at 命令** —— @ 任意在线玩家，可附带一条消息
- **弹窗提醒** —— 被 @ 者屏幕中央弹出主标题 + "来自 xxx"副标题
- **消息直达** —— 带消息时被 @ 者收到"发送者: 消息"，不带消息时收到纯提醒
- **发送反馈** —— @ 发起者收到"已 @ xxx"确认
- **防刷屏冷却** —— 默认 3 秒，`cooldown-seconds` 可调
- **部分匹配** —— 输入名字前缀即可匹配在线玩家（不区分大小写）
- **不能 @ 自己 / 不在线不可 @**
- **音效反馈** —— 可开关，音效/音量/音调均可配置
- **控制台日志** —— 可开关，方便查记录
- **管理命令** —— `/atplugin reload` / `status` / `help`
- **零依赖** —— 仅需一个 `config.yml`

## 📦 安装

1. 从 [Releases](https://github.com/lcxws/MohistLightAtFix/releases) 下载 `AtPlugin-1.0.2.jar`
2. 放入服务端 `plugins/` 文件夹
3. 重启服务端（`/reload` 不推荐）
4. 按需编辑 `plugins/AtPlugin/config.yml`
5. 执行 `/atplugin reload` 即时生效

环境要求：**Bukkit 1.20+**（在 Mohist 1.20.1 上验证；Spigot/Paper 同 API 理论可用）、Java 17。

## ⚙️ 配置文件 `config.yml`

```yaml
# 是否启用插件
enabled: true

# 冷却时间（秒）：同一玩家两次 @ 之间的最短间隔
cooldown-seconds: 3

# 被@时弹出的标题文字（支持 & 颜色代码）
title:
  text: "&e&l你被 @ 了！"
  subtitle-format: "&f来自 &b%s &f的消息"

# 聊天相关设置
chat:
  notify-message: "&e[!] &b%s &e@了你！"   # 无消息时，%s = 发送者名字
  at-message: "&e[@] &b%s&7: &f%m"         # 带消息时，%s = 发送者名字，%m = 消息内容
  sender-message: "&a✓ 已 @ %s"            # %s = 被@玩家名字

# 音效设置（被@者会听到声音）
sound:
  enabled: false
  sound: "entity.experience_orb.pickup"    # 点号/下划线写法均可
  volume: 1.0
  pitch: 1.0

# 是否在控制台记录@事件
log-to-console: true
```

| 字段 | 默认值 | 说明 |
|------|--------|------|
| `enabled` | `true` | 插件总开关 |
| `cooldown-seconds` | `3` | 同一玩家两次 @ 的最短间隔（秒） |
| `title.text` | `&e&l你被 @ 了！` | 弹窗主标题 |
| `title.subtitle-format` | `&f来自 &b%s &f的消息` | 副标题格式，`%s` = 发送者名字 |
| `chat.notify-message` | `&e[!] &b%s &e@了你！` | 无消息时被 @ 者收到的提醒 |
| `chat.at-message` | `&e[@] &b%s&7: &f%m` | 带消息时的私聊格式（`%s` 发送者，`%m` 消息） |
| `chat.sender-message` | `&a✓ 已 @ %s` | @ 发起者的成功反馈 |
| `sound.enabled` | `false` | 是否播放音效 |
| `sound.sound` | `entity.experience_orb.pickup` | 音效名（Bukkit Sound） |
| `sound.volume` / `pitch` | `1.0` / `1.0` | 音量（0.0~1.0）/ 音调（0.5~2.0） |
| `log-to-console` | `true` | 是否在控制台记录 @ 事件 |

颜色代码使用 `&`（如 `&e` 黄色、`&l` 加粗、`&r` 重置）。

## 🎮 命令与权限

| 命令 | 说明 | 权限 | 默认 |
|------|------|------|------|
| `/at <玩家名> [消息]` | @ 一位在线玩家，可附带消息 | `at.use` | 所有玩家 |
| `/atplugin reload` | 重载 `config.yml` | `atplugin.admin` | OP |
| `/atplugin status` | 查看运行状态与版本 | `atplugin.admin` | OP |
| `/atplugin help` | 显示帮助 | `atplugin.admin` | OP |

示例：

```
/at Steve                     ← 只提醒 Steve
/at Steve 来一起挖矿！          ← 提醒并附带消息
```

## ⚠️ 说明

- **聊天内 @ 高亮已移除**（v1.0.2 起）：为避免与其他聊天类插件冲突，@ 功能只通过 `/at` 命令提供，不再拦截玩家聊天。
- **名字匹配**：精确或前缀匹配在线玩家（不区分大小写）。
- **音效名**：使用 Bukkit `Sound` 名，点号（`entity.experience_orb.pickup`）或下划线（`ENTITY_EXPERIENCE_ORB_PICKUP`）写法均可。

## 🔧 构建

```bash
./gradlew build
# 产物: build/libs/AtPlugin-1.0.2.jar
```

Mohist 开发包（`com.mohistmc:mohistdev:1.20.1`）由 Gradle 自动下载。JDK 17。

## 📄 许可证

[MIT](LICENSE) © 2026 Lcxws

> 开发备注（作者原话）：核心逻辑由 AI 辅助生成，作者进行了调试、适配与集成，并在实际服务器中验证。

## 🙏 致谢

- [MohistMC](https://mohistmc.com/) 团队为混合端做出的努力
- 所有在 Mohist 环境下奋斗的服务器管理员
