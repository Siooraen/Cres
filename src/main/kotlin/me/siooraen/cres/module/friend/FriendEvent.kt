package me.siooraen.cres.module.friend

import me.siooraen.cres.module.player.CresPlayer.getCresPlayerData
import me.siooraen.cres.util.event.PlayerLeaveServerEvent
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.player.PlayerJoinEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.module.nms.sendToast
import taboolib.module.nms.type.ToastFrame

object FriendEvent {

    @SubscribeEvent
    fun e(e: PlayerJoinEvent) {
        e.player.getCresPlayerData().friends.forEach {
            val player = Bukkit.getPlayer(it) ?: return@forEach
            if (!player.isOnline) {
                return@forEach
            }
            player.sendToast(Material.PLAYER_HEAD, "${player.name} 上线", ToastFrame.GOAL)
        }
    }

    @SubscribeEvent
    fun e(e: PlayerLeaveServerEvent) {
        e.player.getCresPlayerData().friends.forEach {
            val player = Bukkit.getPlayer(it) ?: return@forEach
            if (!player.isOnline) {
                return@forEach
            }
            player.sendToast(Material.PLAYER_HEAD, "${player.name} 下线", ToastFrame.GOAL)
        }
    }
}