package me.siooraen.cres.module.tool

import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.block.BlockFace
import org.bukkit.block.data.Directional
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.module.chat.uncolored


/**
 * @author Siooraen
 * @since 2023/7/23 11:07
 */
object ToolEvent {

    @SubscribeEvent
    fun e(e: PlayerInteractEvent) {

        if (e.action != Action.LEFT_CLICK_BLOCK || e.player.gameMode != GameMode.CREATIVE) {
            return
        }

        if (e.item == null || e.item!!.itemMeta == null) {
            return
        }
        val itemMeta = e.item!!.itemMeta!!
        if (e.item!!.hasItemMeta() && e.item!!.type == Material.STICK && e.clickedBlock != null) {
            if (itemMeta.displayName.uncolored() == "活塞头放置棒") {
                val location = e.clickedBlock!!.location
                location.y += 1
                location.block.type = Material.PISTON_HEAD
                val blockData = location.block.blockData
                if (blockData is Directional) {
                    blockData.facing = BlockFace.UP
                    location.block.blockData = blockData
                }
                e.isCancelled = true
            }
            if (itemMeta.displayName.uncolored() == "TP棒") {
                val location = e.clickedBlock!!.location
                location.y += 1
                e.player.teleport(location)
                e.isCancelled = true
            }
        }
    }
}