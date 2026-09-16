package me.siooraen.cres.module.basic.patch

import me.siooraen.cres.module.basic.Basic.allowCraftDisplay
import org.bukkit.event.player.PlayerKickEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.CraftingInventory
import taboolib.common.platform.event.SubscribeEvent
import taboolib.library.reflex.Reflex.Companion.invokeMethod
import taboolib.module.nms.PacketReceiveEvent
import taboolib.module.nms.PacketSendEvent
import java.util.concurrent.ConcurrentHashMap

/**
 * @author Siooraen
 * @since 2022/2/5 21:09
 */
object PatchPacket {

    private val bite = ConcurrentHashMap<String, Int>()
    private val entityPackets = arrayOf(
        "PacketPlayOutEntityVelocity",
        "PacketPlayOutEntityMetadata",
        "PacketPlayOutEntityStatus",
        "PacketPlayOutEntityEffect"
    )

    @SubscribeEvent
    fun e(e: PlayerQuitEvent) {
        bite.remove(e.player.name)
    }

    @SubscribeEvent
    fun e(e: PlayerKickEvent) {
        bite.remove(e.player.name)
    }

    @SubscribeEvent
    fun e(e: PacketSendEvent) {
        if (e.packet.name == "PacketPlayOutChat" && e.packet.read<String>("a").toString()
                .contains("chat.type.advancement")
        ) {
            e.isCancelled = true
        }
//        if (e.packet.name == "PacketPlayOutTabComplete" && !e.player.isOp) {
//            e.isCancelled = true
//        }
        if (e.packet.name == "PacketPlayOutWorldParticles" && e.packet.read<Any>("j")!!
                .invokeMethod<String>("a")!! == "minecraft:damage_indicator"
        ) {
            e.isCancelled = true
        }
        if (e.packet.name in entityPackets && e.packet.read<Int>("a") == bite[e.player.name]) {
            e.isCancelled = true
        }
    }

    @SubscribeEvent
    fun e(e: PacketReceiveEvent) {
        if (e.packet.name == "PacketPlayInAutoRecipe" || e.packet.name == "PacketPlayInRecipeDisplayed") {
            if (!allowCraftDisplay) {
                e.isCancelled = true
            }
        }
        if (e.packet.name == "PacketPlayInUseItem" || e.packet.name == "PacketPlayInUseEntity" || e.packet.name == "PacketPlayInArmAnimation") {
            if (e.player.openInventory.topInventory !is CraftingInventory) {
                e.isCancelled = true
            }
        }
    }
}