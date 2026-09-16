package me.siooraen.cres.module.friend

import me.siooraen.cres.module.player.CresPlayer.getCresPlayerData
import me.siooraen.cres.util.Utils.getOnlinePlayers
import me.siooraen.cres.util.error
import me.siooraen.cres.util.info
import org.bukkit.Bukkit
import org.bukkit.Bukkit.getOfflinePlayer
import org.bukkit.entity.Player
import taboolib.common.platform.command.*

@CommandHeader(name = "FRIEND", aliases = ["f"], permissionDefault = PermissionDefault.TRUE)
object FriendCommand {

    @CommandBody
    val main = mainCommand {
        execute<Player> { sender, _, _ ->
            sender.openAllFriendUI()
        }
    }

    @CommandBody
    val add = subCommand {
        dynamic(comment = "name") {
            suggestion<Player> { sender, _ -> getOnlinePlayers(sender) }
            execute<Player> { sender, _, argument ->
                val target = Bukkit.getPlayer(argument)
                if (target == null) {
                    sender.error("${argument}不存在或离线.")
                    return@execute
                }
                if (target.getCresPlayerData().ignored.contains(sender.uniqueId)) {
                    sender.error("你已被${target.name}屏蔽.")
                    return@execute
                }
                sender.getCresPlayerData().friendSentRequests.add(target.uniqueId)
                target.getCresPlayerData().friendReceivedRequests.add(sender.uniqueId)
                sender.info("已向${argument}发送了好友申请.")
                target.info("接收到${sender.name}的好友申请.")
            }
        }
    }

    @CommandBody
    val remove = subCommand {
        dynamic(comment = "name") {
            suggestion<Player>(uncheck = false) { sender, _ -> sender.getCresPlayerData().friends.map { it.getCresPlayerData().name } }
            execute<Player> { sender, _, argument ->
                val target = getOfflinePlayer(argument)
                sender.getCresPlayerData().friends.remove(target.uniqueId)
                target.getCresPlayerData().friends.remove(sender.uniqueId)
                sender.info("已删除好友$argument.")
            }
        }
    }
}