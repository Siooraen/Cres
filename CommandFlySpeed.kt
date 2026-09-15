package me.siooraen.cres.module.basic.command

import me.siooraen.cres.util.error
import me.siooraen.cres.util.info
import org.bukkit.Bukkit.getPlayer
import org.bukkit.entity.Player
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.mainCommand
import taboolib.common.platform.function.onlinePlayers
import taboolib.expansion.createHelper


@CommandHeader(name = "FlySpeed", aliases = ["fs"], permission = "*")
object CommandFlySpeed {

    @CommandBody
    val main = mainCommand {
        dynamic(comment = "speed") {
            dynamic(optional = true, comment = "player") {
                suggestion<Player>(uncheck = true) { _, _ -> onlinePlayers().map { it.name } }
                execute<Player> { sender, context, argument ->
                    val target = getPlayer(argument)
                    if (target == null) {
                        sender.error("无效的玩家.")
                        return@execute
                    }
                    if (context["speed"] == "reset") {
                        target.flySpeed = 0.1f
                        sender.info("${argument}的飞行速度已被重置.")
                        target.info("你的飞行速度已被重置.")
                        return@execute
                    }
                    val speed = context["speed"].toFloatOrNull()
                    if (speed == null || speed > 10) {
                        sender.error("数值只能为-10~10之间的数字.")
                        return@execute
                    }
                    target.flySpeed = speed / 10
                    sender.info("${argument}的飞行速度已被设置为$speed.")
                    target.info("你的飞行速度已被设置为$speed.")
                }
            }
            suggestion<Player>(uncheck = true) { _, _ -> listOf("reset") }
            execute<Player> { sender, _, argument ->
                if (argument == "reset") {
                    sender.flySpeed = 0.1f
                    sender.info("你的飞行速度已被重置.")
                    return@execute
                }
                val speed = argument.toFloatOrNull()
                if (speed == null || speed > 10) {
                    sender.error("数值只能为-10~10之间的数字.")
                    return@execute
                }
                sender.flySpeed = speed / 10
                sender.info("你的飞行速度已被设置为$speed.")
            }
        }
        createHelper()
    }

}
