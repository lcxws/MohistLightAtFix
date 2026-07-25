MohistLightAtFix
一个为 Mohist 核心量身定制的轻量级 @ 提及插件
通过 /at <玩家名> [消息] 命令，精准地向指定玩家发送提醒，并触发醒目弹窗与音效，为朋友间的微型服务器提供便捷的沟通方式。

✨ 特性
命令触发 – 使用 /at <玩家名> [消息] 主动 @ 在线玩家，附带自定义消息（可选）

醒目弹窗提醒 – 被 @ 的玩家屏幕中央会弹出主副标题，确保不遗漏

多重通知 – 被 @ 者收到私聊提醒，@ 者收到发送成功反馈

聊天高亮 – 支持在聊天消息中高亮显示 @玩家名（可与命令结合使用）

音效反馈 – 被 @ 时播放自定义音效（可配置开关）

零依赖 – 不依赖 Vault、PlaceholderAPI 或任何第三方插件

手动配置 – 通过 config.yml 自由定制标题文字、颜色、音效等所有内容

专为 Mohist 优化 – 在 Mohist 环境下稳定工作

开源且轻量 – 整个插件仅几 KB，代码清晰，方便二次修改

📦 安装
下载 MohistLightAtFix-1.0.2.jar

将 jar 文件放入服务端的 plugins/ 文件夹

重启服务端（或使用 /reload，但建议重启）

插件会自动生成 plugins/MohistLightAtFix/config.yml 配置文件

按需编辑 config.yml，然后执行 /atplugin reload 重载配置

⚙️ 配置文件 (config.yml)
yaml
# @插件配置文件

# 是否启用插件
enabled: true

# 被@时弹出的标题文字（支持 & 颜色代码）
title:
  # 主标题
  text: "&e&l你被 @ 了！"
  # 副标题格式（%s 会被替换为发送者名字）
  subtitle-format: "&f来自 &b%s &f的消息"

# 聊天相关设置
chat:
  # 是否高亮显示消息中的 @用户名
  highlight: true
  # 高亮颜色
  highlight-color: "&e"
  # 被@者收到的私聊提醒
  notify-message: "&e[!] &b%s &e在聊天中@了你！"
  # @者发送成功提醒
  sender-message: "&a✓ 已成功@ %s"

# 音效设置（被@者会听到声音）
sound:
  enabled: false
  sound: "entity.experience_orb.pickup"
  volume: 1.0
  pitch: 1.0

# 是否在控制台记录@事件
log-to-console: true
字段说明
字段	类型	默认值	说明
enabled	布尔值	true	插件总开关
title.text	字符串	&e&l你被 @ 了！	屏幕中央弹窗的主标题
title.subtitle-format	字符串	&f来自 &b%s &f的消息	副标题格式，%s 替换为发送者名字
chat.highlight	布尔值	true	是否高亮聊天中的 @ 提及
chat.highlight-color	字符串	&e	高亮颜色
chat.notify-message	字符串	&e[!] &b%s &e在聊天中@了你！	被@者收到的私聊提醒，%s 替换为发送者名字
chat.sender-message	字符串	&a✓ 已成功@ %s	@者发送成功提醒，%s 替换为被@玩家名字
sound.enabled	布尔值	false	是否在被@时播放音效
sound.sound	字符串	entity.experience_orb.pickup	音效 ID
sound.volume	浮点数	1.0	音量（0.0 ~ 1.0）
sound.pitch	浮点数	1.0	音调（0.5 ~ 2.0）
log-to-console	布尔值	true	是否在控制台记录 @ 事件
颜色代码：使用 & 符号，例如 &e 黄色，&l 加粗，&r 重置。

🎮 命令与权限
玩家命令
命令	说明	权限	默认
/at <玩家名> [消息]	@ 一位在线玩家，可附带自定义消息（可选）	at.use	所有玩家
示例：

/at Steve – 只 @ 玩家 Steve，不附带额外消息

/at Steve 来一起挖矿！ – @ 玩家 Steve，并附带消息

管理命令
所有管理命令需 OP 权限或拥有对应权限节点。

命令	说明	权限	默认
/atplugin reload	重载 config.yml 配置文件	atplugin.admin	OP
权限节点
权限节点	说明	默认
at.use	允许使用 /at 命令	true（所有玩家可用）
atplugin.admin	允许使用 /atplugin 管理命令	op
⚠️ 已知问题与限制
Forge 模组聊天监听失效
由于插件通过 setCancelled(true) 拦截了原始聊天事件，所有依赖 Forge ServerChatEvent 的模组将无法监听到玩家的聊天内容。这是为了修复 Mohist 环境下 @ 功能可能失效而做出的必要牺牲。

仅支持 Mohist 核心
本插件专为 Mohist 1.20.1 设计，在纯 Bukkit/Spigot 或 Paper 服务端上可能无法正常工作（因为不存在 <user> 问题需要修复）。

玩家名匹配
目前 /at 命令要求精确匹配在线玩家名（不区分大小写），不支持模糊匹配。

🔧 开发者信息
主类：com.mohistmc.atplugin.AtPlugin

API 版本：1.20

构建：无外部依赖，直接编译即可

🧠 开发备注：本插件的核心逻辑由 AI 辅助生成，作者进行了调试、适配和集成，所有代码均在指定环境中经过测试，确保稳定运行。

欢迎 Fork 并提交 Pull Request，但请保留原作者信息。

📄 许可证
本项目采用 MIT License 开源，你可以自由使用、修改、分发，但必须保留版权声明。

🙏 致谢
感谢 MohistMC 团队为混合端做出的努力

感谢所有在 Mohist 环境下奋斗的服务器管理员

🧠 特别感谢 AI 工具在代码生成和调试过程中提供的辅助支持
