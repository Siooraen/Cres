package me.siooraen.cres.module.badge

import me.siooraen.cres.module.badge.Badge.getBadgeData
import me.siooraen.cres.util.error
import org.bukkit.event.player.PlayerEditBookEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.module.chat.uncolored

/**
 * @author Siooraen
 * @since 2022/8/29 19:43
 */
object BadgeEvent {

    @SubscribeEvent
    fun e(e: PlayerEditBookEvent) {
        if (!e.player.isOp) {
            return
        }
        if (e.previousBookMeta.displayName.contains("编辑描述") && e.previousBookMeta.lore!![0].uncolored() == "Badge") {
            val badgeData = getBadgeData(e.previousBookMeta.lore!![1].uncolored())
            if (badgeData == null) {
                e.player.error("该徽章已失效")
            } else {
                badgeData.desc.clear()
                val lines = e.newBookMeta.pages.joinToString(separator = "").split("\n")
                if (lines[0].uncolored() != "clear") {
                    badgeData.desc.addAll(lines)
                }
            }
            e.isSigning = false
        }
    }
}