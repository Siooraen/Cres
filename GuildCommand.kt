package me.siooraen.cres.module.guild

import org.bukkit.entity.Player
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.PermissionDefault
import taboolib.common.platform.command.mainCommand

@CommandHeader(name = "GUILD", aliases = ["g"], permissionDefault = PermissionDefault.TRUE)
object GuildCommand {

    @CommandBody
    val main = mainCommand {
        execute<Player> { sender, context, argument ->

        }
    }
}