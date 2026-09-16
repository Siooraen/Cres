package me.siooraen.cres

import me.siooraen.cres.Cres.conf
import me.siooraen.cres.module.Module.Companion.modules
import me.siooraen.cres.module.basic.Basic.basicConf
import me.siooraen.cres.module.basic.command.*
import me.siooraen.cres.module.motd.Motd.motdConf
import me.siooraen.cres.module.openModuleUI
import me.siooraen.cres.module.player.CresPlayer.getCresPlayerData
import me.siooraen.cres.module.player.CresPlayerRank
import me.siooraen.cres.module.tool.ToolCommand
import me.siooraen.cres.util.info
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.mainCommand
import taboolib.common.platform.command.subCommand
import taboolib.expansion.createHelper

@CommandHeader(name = "Cres", aliases = ["cs", "core"], permission = "*")
object CresCommand {

    @CommandBody
    val main = mainCommand { createHelper() }

    @CommandBody
    val module = subCommand {
        execute<CommandSender> { sender, _, _ ->
            if (sender is Player) {
                sender.openModuleUI()
            } else {
                sender.info("核心插件 Cres")
                sender.info("当前共加载 ${modules.size} 个模块:")
                modules.forEachIndexed { index, module -> sender.info(" &7- &8(${index + 1})&7${module.name}") }
            }
        }
    }

    @CommandBody
    val help = CommandHelp

    @CommandBody
    val gm = CommandGameMode

    @CommandBody
    val heal = CommandHeal

    @CommandBody
    val flyspeed = CommandFlySpeed

    @CommandBody
    val walkspeed = CommandWalkSpeed

    @CommandBody
    val lore = CommandLore

    @CommandBody
    val tool = ToolCommand

    @CommandBody
    val reload = subCommand {
        execute<CommandSender> { sender, _, _ ->
            conf.reload()
            basicConf.reload()
            motdConf.reload()
            sender.info("重载成功.")
        }
    }

    @CommandBody
    val rank = subCommand {
        dynamic {
            suggestion<Player> { _, _ -> CresPlayerRank.values().map { it.name } }
            execute<Player> { sender, _, argument ->
                val data = sender.getCresPlayerData()
                data.rank = CresPlayerRank.valueOf(argument)
                data.updateDisplayName()
            }
        }
    }

}