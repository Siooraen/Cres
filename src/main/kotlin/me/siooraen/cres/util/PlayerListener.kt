package me.siooraen.cres.util

import me.siooraen.cres.util.event.PlayerLeaveServerEvent
import org.bukkit.event.player.PlayerKickEvent
import org.bukkit.event.player.PlayerQuitEvent
import taboolib.common.platform.event.SubscribeEvent

/**
 * @author Siooraen
 * @since 2022/9/4 8:56
 */
object PlayerListener {

    @SubscribeEvent
    fun e(e: PlayerQuitEvent) {
        PlayerLeaveServerEvent(e.player).call()
    }

    @SubscribeEvent
    fun e(e: PlayerKickEvent) {
        PlayerLeaveServerEvent(e.player).call()
    }
}