package me.siooraen.cres.module.basic.patch

import me.siooraen.cres.module.basic.Basic
import me.siooraen.cres.util.Utils.bypass
import org.bukkit.event.block.SignChangeEvent
import org.bukkit.event.entity.PlayerDeathEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.module.chat.colored

/**
 * @author Siooraen
 * @since 2022/2/5 21:08
 */
object PatchEssential {

    /**
     * 牌子颜色
     */
    @SubscribeEvent
    fun e(e: SignChangeEvent) {
        if (e.player.bypass())
            (0..3).forEach { e.setLine(it, e.getLine(it)?.colored() ?: "") }
    }

    /**
     * 死亡提示
     */
    @SubscribeEvent
    fun e(e: PlayerDeathEvent) {
        val message = Basic.basicConf.getString("death-message")
        if (message.isNullOrEmpty())
            e.deathMessage = null
        else
            e.deathMessage = message.colored().replace("@p", e.entity.player!!.name)
    }
}