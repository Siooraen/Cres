package me.siooraen.cres.module.chat

import me.siooraen.cres.module.player.CresPlayer.getCresPlayerData
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

@CommandHeader(name = "REPLY", aliases = ["r"], permissionDefault = PermissionDefault.TRUE)
object ChatReplyCommand {

    @CommandBody
    val main = mainCommand {
        dynamic(comment = "message") {
            execute<Player> { sender, _, argument ->
                if (sender.getCresPlayerData().msgSender == null) {
                    sender.error("你没有私信过任何人! 使用 /msg [昵称] [消息] 来私信玩家.")
                    return@execute
                }
                val target = Bukkit.getPlayer(sender.getCresPlayerData().msgSender!!)
                if (sender.getCresPlayerData().ignored.contains(sender.uniqueId)) {
                    sender.error("你已被${target!!.name}屏蔽.")
                    return@execute
                }
                sender.sendMessage("\uF120 &7我 &8→ &7${target!!.name}&8: &f${argument.uncolored()}".colored())
                sender.playSound(sender.location, Sound.BLOCK_LEVER_CLICK, 2f, 1f)
                sender.getCresPlayerData().msgSender = target.uniqueId
                target.sendMessage("\uF120 &7${sender.name} &8→ &7我&8: &f${argument.uncolored()}".colored())
                target.playSound(target.location, Sound.BLOCK_LEVER_CLICK, 2f, 1f)
                target.getCresPlayerData().msgSender = sender.uniqueId
            }
        }
        execute<Player> { sender, _, _ ->
            sender.error("错误用法. 用法: /r [消息]")
        }
    }
}