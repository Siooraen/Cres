package me.siooraen.cres.module.bossbar

import me.siooraen.cres.module.Module
import me.siooraen.cres.module.ModuleState
import org.bukkit.Bukkit
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.entity.Player
import taboolib.common.platform.Schedule

object BossBar : Module {

    override val name: String
        get() = "BossBar"

    override val id: String
        get() = "BossBar"

    override val state: ModuleState
        get() = ModuleState.DEVELOPING

    @Schedule(async = true, period = 1L)
    fun e() {
        Bukkit.getOnlinePlayers().forEach {
            val bossbar = Bukkit.createBossBar(it.getNavigationText(), BarColor.WHITE, BarStyle.SEGMENTED_20)
        }
    }

    // \uF821
    fun Player.getNavigationText(): String {
        val yaw = this.location.yaw.toInt()
        if (yaw in 0..89) {
            return "START" + "\uF821".repeat(90 - yaw) + "S" + "\uF821".repeat(90) + "W" + "\uF821".repeat(yaw) + "END"
        }
        if (yaw in 90..179) {
            return "START" + "\uF821".repeat(180 - yaw) + "W" + "\uF821".repeat(90) + "N" + "\uF821".repeat(yaw - 90) + "END"
        }
        if (yaw in -90..-1) {
            return "START" + "\uF821".repeat(90 - yaw) + "S" + "\uF821".repeat(90) + "W" + "\uF821".repeat(yaw) + "END"
        }
        if (yaw in -180..-91) {
            return "START" + "\uF821".repeat(90 - yaw) + "S" + "\uF821".repeat(90) + "E" + "\uF821".repeat(180 + yaw) + "END"
        }
        if (yaw in 0..89) {
            return "START" + "\uF821".repeat(yaw) + "E" + "\uF821".repeat(90) + "S" + "\uF821".repeat(90 - yaw) + "END"
        }
        return ""
    }
}