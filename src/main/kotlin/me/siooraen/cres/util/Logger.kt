package me.siooraen.cres.util

import org.bukkit.Sound
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import taboolib.common.platform.function.console
import taboolib.module.chat.colored

/**
 * @author Siooraen
 * @since 2022/9/4 8:34
 */
fun CommandSender.info(info: String, ifPlaySound: Boolean = true) {
    sendMessage("&7${info}".colored())
    if (this is Player && ifPlaySound) {
        playSound(this.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 2f)
    }
}

fun CommandSender.error(info: String, ifPlaySound: Boolean = true) {
    sendMessage("&7${info}".colored())
    if (this is Player && ifPlaySound) {
        playSound(this.location, Sound.ENTITY_VILLAGER_NO, 1f, 1f)
    }
}

fun Player.title(info: String, sub: String) {
    this.sendTitle(info.colored(), sub.colored(), 10, 25, 10)
}

fun Player.buttonNotice() {
    playSound(this.location, Sound.BLOCK_LEVER_CLICK, 1f, 1f)
}

fun console(info: String) {
    console().sendMessage(info.colored())
}

fun consoles(info: List<String>) {
    info.forEach { console().sendMessage(it.colored()) }
}