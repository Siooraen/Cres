package me.siooraen.cres.module.basic.command

import me.siooraen.cres.util.error
import me.siooraen.cres.util.info
import org.bukkit.Bukkit.getPlayer
import org.bukkit.GameMode
import org.bukkit.entity.Player
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.mainCommand
import taboolib.common.platform.function.onlinePlayers
import taboolib.expansion.createHelper

@CommandHeader(name = "GM", permission = "*")
object CommandGameMode {

    @CommandBody
    val main = mainCommand {
        dynamic(comment = "mode") {
            dynamic(optional = true, comment = "player") {
                suggestion<Player> { _, _ -> onlinePlayers().map { it.name } }
                execute<Player> { sender, context, argument ->
                    val target = getPlayer(argument)
                    if (target == null) {
                        sender.error("无效的玩家.")
                        return@execute
                    }
                    val mode = getMode(context["mode"])
                    if (mode == null) {
                        sender.error("数值只能为0~3之间的整数.")
                        return@execute
                    }
                    target.gameMode = GameMode.valueOf(mode)
                    sender.info("${argument}的模式已被更改为$mode.")
                    target.info("你的模式已被更改为$mode.")
                }
            }
            suggestion<Player> { _, _ -> listOf("0", "1", "2", "3") }
            execute<Player> { sender, _, argument ->
                val mode = getMode(argument)
                if (mode == null) {
                    sender.error("数值只能为0~3之间的整数.")
                    return@execute
                }
                sender.gameMode = GameMode.valueOf(mode)
                sender.info("你的模式已被更改为$mode.")
            }
        }
        createHelper()
    }

    private fun getMode(argument: String): String? {
        return when (argument) {
            "0" -> "SURVIVAL"
            "1" -> "CREATIVE"
            "2" -> "ADVENTURE"
            "3" -> "SPECTATOR"
            else -> null
        }
    }

}