package me.siooraen.cres.module.basic.command

import me.siooraen.cres.module.basic.Basic.basicConf
import org.bukkit.entity.Player
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.PermissionDefault
import taboolib.common.platform.command.mainCommand
import taboolib.module.chat.colored

@CommandHeader(name = "Help", permissionDefault = PermissionDefault.TRUE)
object CommandHelp {

    @CommandBody
    val main = mainCommand {
        execute<Player> { sender, _, _ ->
            basicConf.getStringList("custom-help").forEach { sender.sendMessage(it.colored()) }
            return@execute
        }
    }

}