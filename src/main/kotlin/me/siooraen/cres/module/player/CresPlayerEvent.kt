package me.siooraen.cres.module.player

import me.siooraen.cres.module.player.CresPlayer.getCresDisplayName
import me.siooraen.cres.module.player.CresPlayer.getCresPlayerData
import me.siooraen.cres.util.event.PlayerLeaveServerEvent
import org.bukkit.Bukkit
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerKickEvent
import org.bukkit.event.player.PlayerQuitEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.module.chat.colored
import taboolib.module.nms.setPrefix
import java.util.*

/**
 * @author Siooraen
 * @since 2022/10/28 8:19
 */
object CresPlayerEvent {

    @SubscribeEvent
    fun e(e: PlayerLeaveServerEvent) {
        val data = e.player.getCresPlayerData()
        data.stamina = 0.0
        data.lastOnline = Date(System.currentTimeMillis())
        data.mana = 0.0
    }

    @SubscribeEvent
    fun e(e: PlayerJoinEvent) {
        e.joinMessage = "&7[&a+&7] ${e.player.getCresDisplayName()}".colored()

        val data = e.player.getCresPlayerData()
        val souls = ((System.currentTimeMillis() - data.lastOnline!!.time) / 1800000).toInt()
        data.updateDisplayName()
        Bukkit.getOnlinePlayers().forEach {
            it?.let {
                if (it.getCresPlayerData().friends.contains(e.player.uniqueId)) {
                    e.player.setPrefix("§a", it)
                } else {
                    e.player.setPrefix("§f", it)
                }
            }
        }

        data.addSoul(souls)
        if (souls > 0) {
            e.player.sendMessage("&7在你离线的时间内，你获得了${souls}点灵魂.".colored())
        }
    }

    @SubscribeEvent
    fun e(e: PlayerQuitEvent) {
        e.quitMessage = "&7[&c-&7] ${e.player.getCresDisplayName()}".colored()
    }

    @SubscribeEvent
    fun e(e: PlayerKickEvent) {
        e.leaveMessage = "&7[&c-&7] ${e.player.getCresDisplayName()}".colored()
    }
}