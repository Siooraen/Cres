package me.siooraen.cres.module.badge

import me.siooraen.cres.module.badge.Badge.badges
import me.siooraen.cres.module.badge.Badge.export
import me.siooraen.cres.module.badge.Badge.getBadgeData
import me.siooraen.cres.module.badge.data.BadgeData
import me.siooraen.cres.module.badge.data.BadgePlayerData
import me.siooraen.cres.module.badge.data.openEditor
import me.siooraen.cres.module.player.CresPlayer.getCresPlayerData
import me.siooraen.cres.util.info
import org.bukkit.Bukkit.getPlayer
import org.bukkit.entity.Player
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.mainCommand
import taboolib.common.platform.command.subCommand
import taboolib.common.platform.function.onlinePlayers
import taboolib.expansion.createHelper
import java.lang.System.currentTimeMillis
import java.util.*

/**
 * @author Siooraen
 * @since 2022/7/6 15:14
 */
@CommandHeader(name = "Badge", permission = "*")
object BadgeCommand {

    @CommandBody
    val main = mainCommand {
        createHelper()
    }

    @CommandBody
    val list = subCommand {
        dynamic(comment = "player", optional = true) {
            suggestion<Player> { _, _ -> onlinePlayers().map { it.name } }
            execute<Player> { _, _, argument ->
                val target = getPlayer(argument)!!
                target.openBadgeUI()
            }
        }
        execute<Player> { sender, _, _ ->
            sender.openBadgeUI()
        }
    }

    @CommandBody
    val create = subCommand {
        dynamic(comment = "id") {
            execute<Player> { sender, _, argument ->
                sender.info("徽章已创建.")
                badges.add(BadgeData(argument).run {
                    openEditor(sender)
                    this
                })
                export()
            }
        }
    }

    @CommandBody
    val remove = subCommand {
        dynamic(comment = "id") {
            execute<Player> { sender, _, argument ->
                val badgeData = getBadgeData(argument)
                sender.info("徽章已移除.")
                badges.remove(badgeData)
                export()
            }
            suggestion<Player> { _, _ -> badges.map { it.id } }
        }
    }

    @CommandBody
    val edit = subCommand {
        dynamic(comment = "id") {
            execute<Player> { sender, _, argument ->
                val badgeData = getBadgeData(argument)!!
                badgeData.openEditor(sender)
            }
            suggestion<Player> { _, _ -> badges.map { it.id } }
        }
    }

    @CommandBody
    val give = subCommand {
        dynamic(comment = "player") {
            dynamic(comment = "id") {
                suggestion<Player> { _, _ -> badges.map { it.id } }
                execute<Player> { sender, context, argument ->
                    val target = getPlayer(context["player"])!!
                    val badgeData = getBadgeData(argument)!!
                    val badgePlayerData = BadgePlayerData(badgeData, Date(currentTimeMillis()))
                    target.getCresPlayerData().badges.add(badgePlayerData)
                    sender.info("已给予${target.name}徽章${badgeData.id}")
                    target.info("你收到了徽章${badgeData.id}")
                }
            }
            suggestion<Player> { _, _ -> onlinePlayers().map { it.name } }
        }
    }

    @CommandBody
    val take = subCommand {
        dynamic(comment = "player") {
            dynamic(comment = "id") {
                suggestion<Player> { _, _ -> badges.map { it.id } }
                execute<Player> { sender, context, argument ->
                    val target = getPlayer(context["player"])!!
                    val badgeData = getBadgeData(argument)!!
                    val targetBadgePlayerData = target.getCresPlayerData().badges.firstOrNull { it.badgeData == badgeData }
                    if (targetBadgePlayerData != null) {
                        target.getCresPlayerData().badges.remove(targetBadgePlayerData)
                        sender.info("已取走${target.name}的徽章${targetBadgePlayerData.badgeData.id}")
                        target.info("你的徽章${targetBadgePlayerData.badgeData.id}已被取走")
                    } else {
                        sender.info("${target.name}不拥有徽章${badgeData.id}")
                    }
                }
            }
            suggestion<Player> { _, _ -> onlinePlayers().map { it.name } }
        }
    }
}