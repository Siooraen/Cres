package me.siooraen.cres.module.actionbar

import me.siooraen.cres.module.Module
import me.siooraen.cres.module.ModuleState
import me.siooraen.cres.module.player.CresPlayer.getCresPlayerData
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.entity.Player
import taboolib.common.platform.Schedule
import taboolib.module.chat.colored
import taboolib.platform.util.sendActionBar

/**
 * @author Siooraen
 * @since 2023/7/12 19:43
 */
object ActionBar : Module {

    override val name: String
        get() = "ActionBar"

    override val id: String
        get() = "ActionBar"

    override val state: ModuleState
        get() = ModuleState.RUNNING

    @Schedule(async = true, period = 1L)
    fun e() {
        Bukkit.getOnlinePlayers().forEach {
            if (it.gameMode == GameMode.CREATIVE) {
                return@forEach
            }
            it.sendActionBar(it.getActionBarText())
        }
    }

    fun Player.getActionBarText(): String {
//        this.getComboData().checkExpired()
//        val comboText = this.getComboData().comboText
        val healthText = "&c${this.health.toInt()} ❤ ${this.maxHealth}&r"
        val manaText = "&3${this.getCresPlayerData().mana.toInt()} ✸ ${this.getCresPlayerData().maxMana.toInt()}&r"
        return "$healthText $manaText".colored()
    }
}