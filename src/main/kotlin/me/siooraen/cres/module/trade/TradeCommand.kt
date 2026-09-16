package me.siooraen.cres.module.trade

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.mainCommand
import taboolib.common.platform.function.onlinePlayers

/**
 * @author Siooraen
 * @since 2023/8/8 22:39
 */
@CommandHeader(name = "Trade")
object TradeCommand {

    @CommandBody
    val main = mainCommand {
        dynamic(comment = "player") {
            suggestion<Player> { _, _ -> onlinePlayers().map { it.name } }
            execute<Player> { sender, _, argument ->
                val target = Bukkit.getPlayerExact(argument)!!
                trade(sender, mutableListOf(), TradeProgress.CONFIRM, target, mutableListOf(), TradeProgress.CONFIRM)
            }
        }
    }
}