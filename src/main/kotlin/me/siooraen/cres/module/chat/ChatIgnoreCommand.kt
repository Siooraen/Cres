package me.siooraen.cres.module.chat

import me.siooraen.cres.module.player.CresPlayer.getCresPlayerData
import me.siooraen.cres.util.error
import me.siooraen.cres.util.info
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import taboolib.common.platform.command.*

@CommandHeader(name = "IGNORE", permissionDefault = PermissionDefault.TRUE)
object ChatIgnoreCommand {

    @CommandBody
    val main = mainCommand {
        execute<Player> { sender, _, _ ->
            sender.openIgnoredListUI()
        }
    }

    @CommandBody
    val add = subCommand {
        dynamic(comment = "name") {
            execute<Player> { sender, _, argument ->
                val player = Bukkit.getPlayer(argument)
                if (player == null) {
                    sender.error("${argument}不存在或离线.")
                    return@execute
                }
                sender.getCresPlayerData().ignored.add(player.uniqueId)
                sender.getCresPlayerData().updateIgnoredList()
                sender.info("已屏蔽${argument}.")
            }
            execute<Player> { sender, _, _ ->
                sender.error("错误用法. 用法 /ignore add [昵称]")
            }
        }
    }

    @CommandBody
    val remove = subCommand {
        dynamic(comment = "name") {
            execute<Player> { sender, _, argument ->
                val player = Bukkit.getPlayer(argument)
                if (player == null) {
                    sender.error("${argument}不存在或离线.")
                    return@execute
                }
                if (!sender.getCresPlayerData().ignored.contains(player.uniqueId)) {
                    sender.error("你没有屏蔽${argument}.")
                }
                sender.getCresPlayerData().ignored.remove(player.uniqueId)
                sender.getCresPlayerData().updateIgnoredList()
                sender.info("已取消屏蔽${argument}.")
            }
            execute<Player> { sender, _, _ ->
                sender.error("错误用法. 用法 /ignore remove [昵称]")
            }
        }
    }
}