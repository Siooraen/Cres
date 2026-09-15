package me.siooraen.cres.module.tool

import org.bukkit.entity.Player
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.mainCommand
import taboolib.common.platform.command.subCommand
import taboolib.expansion.createHelper

/**
 * @author Siooraen
 * @since 2023/7/23 10:55
 */
@CommandHeader(name = "Tool", aliases = ["tl"], permission = "*")
object ToolCommand {

    @CommandBody
    val main = mainCommand {
        createHelper()
    }

    @CommandBody
    val list = subCommand {
        execute<Player> { sender, _, _ ->
            sender.openToolUI()
        }
    }
}