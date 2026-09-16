package me.siooraen.cres.module.inventory

import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.module.chat.uncolored

object InventoryEvent {

    // 44 日记
    // 43 角色信息

    @SubscribeEvent
    fun e(e: InventoryDragEvent) {
        if (e.inventory.type == InventoryType.PLAYER || e.inventory.type == InventoryType.CREATIVE) {
            if (e.rawSlots.contains(44)
                || e.rawSlots.contains(43)) {
                e.isCancelled = true
            }
        }
    }

    @SubscribeEvent
    fun e(e: InventoryClickEvent) {
        val inventory = e.clickedInventory ?: return
        if (inventory.type == InventoryType.PLAYER || inventory.type == InventoryType.CREATIVE) {
            when(e.rawSlot) {
                44 -> {
                    e.isCancelled = true
                }
                43 -> {
                    e.isCancelled = true
                }
            }
        }
    }

    @SubscribeEvent
    fun e(e: PlayerDropItemEvent) {
        if (e.itemDrop.itemStack.itemMeta == null) {
            return
        }
        when(e.itemDrop.itemStack.itemMeta!!.displayName.uncolored()) {
            "日记" -> {
                e.isCancelled = true
            }
            "角色信息" -> {
                e.isCancelled = true
            }
        }
    }

    @SubscribeEvent
    fun e(e: PlayerInteractEvent) {
        if (e.item == null || e.item!!.itemMeta == null) {
            return
        }
        when(e.item!!.itemMeta!!.displayName.uncolored()) {
            "日记" -> {
                e.isCancelled = true
            }
            "角色信息" -> {
                e.isCancelled = true
            }
        }
    }
}