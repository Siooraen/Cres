package me.siooraen.cres.module.chat

import me.siooraen.cres.module.player.CresPlayer.getCresPlayerData
import me.siooraen.cres.util.Utils.getOnlinePlayers
import me.siooraen.cres.util.error
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.entity.Player
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.PermissionDefault
import taboolib.common.platform.command.mainCommand
import taboolib.module.chat.colored
import taboolib.module.chat.uncolored

/**
 * @author Siooraen
 * @since 2023/7/21 21:49
 */
@CommandHeader(name = "MSG", permissionDefault = PermissionDefault.TRUE)
object ChatMsgCommand {

    @CommandBody
    val main = mainCommand {
        dynamic(comment = "name") {
            suggestion<Player>(uncheck = true) { sender, _ -> getOnlinePlayers(sender) }
            dynamic(comment = "message") {
                execute<Player> { sender, context, argument ->
                    val target = Bukkit.getPlayer(context["name"])
                    if (target == null) {
                        sender.error("${context["name"]}不存在或离线.")
                        return@execute
                    }
                    if (target.getCresPlayerData().ignored.contains(sender.uniqueId)) {
                        sender.error("你已被${target.name}屏蔽.")
                        return@execute
                    }
                    sender.sendMessage("\uF120 &7我 &8→ &7${target.name}&8: &f${argument.uncolored()}".colored())
                    sender.playSound(sender.location, Sound.BLOCK_LEVER_CLICK, 2f, 1f)
                    sender.getCresPlayerData().msgSender = target.uniqueId
                    target.sendMessage("\uF120 &7${sender.name} &8→ &7我&8: &f${argument.uncolored()}".colored())
                    target.playSound(target.location, Sound.BLOCK_LEVER_CLICK, 2f, 1f)
                    target.getCresPlayerData().msgSender = sender.uniqueId
                }
            }
            execute<Player> { sender, _, _ ->
                sender.error("错误用法. 用法: /msg [昵称] [消息]")
            }
        }
        execute<Player> { sender, _, _ ->
            sender.error("错误用法. 用法: /msg [昵称] [消息]")
        }
    }
}