package me.siooraen.cres.module.basic.patch

import me.siooraen.cres.module.basic.Basic.basicConf
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.bukkit.event.player.PlayerCommandSendEvent
import org.spigotmc.SpigotConfig
import taboolib.common.platform.event.SubscribeEvent
import java.util.*

/**
 * @author Siooraen
 * @since 2022/2/5 21:07
 */
object PatchCommand {

    @SubscribeEvent
    fun e(e: PlayerCommandSendEvent) {
        if (!e.player.isOp) {
            e.commands.removeIf { !basicConf.getStringList("unblock-command-name").contains(it) }
        }
    }

    @SubscribeEvent
    fun e(e: PlayerCommandPreprocessEvent) {
        if (e.player.isOp) {
            return
        }
        val v = e.message.split(" ")[0].lowercase(Locale.getDefault()).substring(1)
        if (v.contains(":") || v !in basicConf.getStringList("unblock-command-name")) {
            e.isCancelled = true
            e.player.sendMessage(SpigotConfig.unknownCommandMessage)
        }
    }
}