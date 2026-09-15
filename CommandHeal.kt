package me.siooraen.cres.module.basic.command

import me.siooraen.cres.util.error
import me.siooraen.cres.util.info
import org.bukkit.Bukkit.getPlayer
import org.bukkit.Particle
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.mainCommand
import taboolib.common.platform.function.onlinePlayers


@CommandHeader(name = "heal", permission = "*")
object CommandHeal {

    @CommandBody
    val main = mainCommand {
        dynamic(optional = true, comment = "player") {
            suggestion<Player> { _, _ -> onlinePlayers().map { it.name } }
            execute<Player> { sender, _, argument ->
                val target = getPlayer(argument)
                if (target == null) {
                    sender.error("无效的玩家.")
                    return@execute
                }
                target.health = target.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.value
                target.spawnParticle(Particle.HEART, target.eyeLocation, 4, 0.5, 0.5, 0.5)
                sender.info("${argument}已被治疗.")
                target.info("你已被治疗.")
            }
        }
        execute<Player> { sender, _, _ ->
            sender.health = sender.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.value
            sender.spawnParticle(Particle.HEART, sender.eyeLocation, 4, 0.5, 0.5, 0.5)
            sender.info("你已被治疗.")
        }
    }

}