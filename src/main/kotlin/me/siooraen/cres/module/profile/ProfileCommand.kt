package me.siooraen.cres.module.profile

import me.siooraen.cres.module.player.CresPlayer
import org.bukkit.entity.Player
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.mainCommand
import taboolib.common.platform.function.onlinePlayers

/**
 * @author Siooraen
 * @since 2022/4/30 19:33
 */
@CommandHeader(name = "Profile")
object ProfileCommand {

    @CommandBody
    val main = mainCommand {
        dynamic(comment = "player") {
            suggestion<Player>(uncheck = true) { _, _ -> onlinePlayers().map { it.name } }
            execute<Player> { sender, _, argument ->
                CresPlayer.playerData.forEach {
                    if (it.value.player.name == argument) {
                        sender.openProfileUI(it.value.uuid)
                        return@forEach
                    }
                }
            }
        }
        execute<Player> { sender, _, _ ->
            sender.openProfileUI(sender.uniqueId)
        }
    }
}