package me.siooraen.cres.module.basic.patch

import me.siooraen.cres.module.basic.Basic
import me.siooraen.cres.util.Utils.bypass
import org.bukkit.entity.AbstractArrow
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Arrow
import org.bukkit.entity.FishHook
import org.bukkit.event.block.Action
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.event.inventory.*
import org.bukkit.event.player.PlayerInteractEvent
import taboolib.common.platform.event.EventPriority
import taboolib.common.platform.event.SubscribeEvent

/**
 * @author Siooraen
 * @since 2022/2/5 21:09
 */
object PatchPlayer {

    /**
     * 禁止合成物品
     */
    @SubscribeEvent(priority = EventPriority.LOWEST, ignoreCancelled = true)
    fun e(e: CraftItemEvent) {
        if (!Basic.allowCraft) {
            e.isCancelled = true
        }
    }

    /**
     * 禁止合成
     */
    @SubscribeEvent(priority = EventPriority.LOWEST, ignoreCancelled = true)
    fun e(e: InventoryClickEvent) {
        if (!Basic.allowCraft) {
            e.isCancelled = e.clickedInventory?.type == InventoryType.CRAFTING
        }
    }

    /**
     * 禁止合成
     */
    @SubscribeEvent(priority = EventPriority.LOWEST, ignoreCancelled = true)
    fun e(e: InventoryDragEvent) {
        if (!Basic.allowCraft) {
            e.isCancelled = e.inventory.type == InventoryType.CRAFTING && e.rawSlots.any { it < 5 }
        }
    }

    /**
     * 禁止打开界面
     */
    @SubscribeEvent(priority = EventPriority.LOWEST, ignoreCancelled = true)
    fun e(e: InventoryOpenEvent) {
        e.isCancelled = e.inventory.type.name in Basic.blockInventory
    }

    /**
     * 禁止方块交互
     */
    @SubscribeEvent(priority = EventPriority.LOWEST, ignoreCancelled = true)
    fun e(e: PlayerInteractEvent) {
        if ((e.action == Action.RIGHT_CLICK_BLOCK || e.action == Action.LEFT_CLICK_BLOCK) && !e.player.bypass()) {
            val type = e.clickedBlock!!.type.name
            if (Basic.blockInteract.any {
                    if (it.endsWith("?")) it.substring(
                        0,
                        it.length - 1
                    ) in type else it == type
                }) {
                e.isCancelled = true
            }
        }
    }

    @SubscribeEvent
    fun e(e: ProjectileHitEvent) {
        if (e.entity is FishHook && (e.hitEntity is ArmorStand || e.hitEntity?.hasMetadata("NPC") == true)) {
            e.entity.remove()
        }
        if (e.entity is Arrow && (e.entity as Arrow).pickupStatus != AbstractArrow.PickupStatus.ALLOWED) {
            e.entity.remove()
        }
    }
}