package me.siooraen.cres.module.chat

import me.siooraen.cres.module.chat.Chat.sendGlobalMsg
import me.siooraen.cres.module.player.CresPlayer.getCresPlayerData
import me.siooraen.cres.util.event.PlayerLeaveServerEvent
import org.bukkit.event.player.AsyncPlayerChatEvent
import taboolib.common.platform.event.EventPriority
import taboolib.common.platform.event.SubscribeEvent

object ChatEvent {

    @SubscribeEvent(EventPriority.MONITOR)
    fun e(e: AsyncPlayerChatEvent) {

        e.isCancelled = true

        when(e.player.getCresPlayerData().chatChannel) {
            ChatChannel.GLOBAL -> e.player.sendGlobalMsg(e.message)
            else -> e.player.sendGlobalMsg(e.message)
        }
    }

    @SubscribeEvent
    fun e(e: PlayerLeaveServerEvent) {
        e.player.getCresPlayerData().msgSender = null
    }
}