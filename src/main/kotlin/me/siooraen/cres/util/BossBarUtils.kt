package me.siooraen.cres.util

import org.bukkit.Bukkit
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.entity.Player
import taboolib.common.platform.function.submit

/**
 * @author Siooraen
 * @since 2023/7/19 20:56
 */
fun Player.createTransparentBossBar(iconText: String, animateText: List<String>, period: Long, layers: Int, time: Int) {
    for (i in 1 .. layers) {
        val bossBar = Bukkit.createBossBar("", BarColor.WHITE, BarStyle.SEGMENTED_20)
        bossBar.addPlayer(this)
        bossBar.progress = 1.0
        submit(async = true, delay = (time * 20).toLong()) {
            bossBar.removePlayer(this@createTransparentBossBar)
            return@submit
        }
    }

    val iconBossBar = Bukkit.createBossBar(iconText, BarColor.WHITE, BarStyle.SEGMENTED_20)
    iconBossBar.addPlayer(this)
    iconBossBar.progress = 1.0
    submit(async = true, delay = (time * 20).toLong()) {
        iconBossBar.removePlayer(this@createTransparentBossBar)
        return@submit
    }

    val animateBossBar = Bukkit.createBossBar(animateText[0], BarColor.WHITE, BarStyle.SEGMENTED_20)
    animateBossBar.addPlayer(this)
    animateBossBar.progress = 1.0
    for (i in 0 until (animateText.size)) {
        submit(async = true, delay = period * i) {
            animateBossBar.setTitle(animateText[i])
            return@submit
        }
    }
    submit(async = true, delay = (time * 20).toLong()) {
        animateBossBar.removePlayer(this@createTransparentBossBar)
        return@submit
    }
}