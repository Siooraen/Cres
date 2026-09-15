package me.siooraen.cres.util

import org.bukkit.*
import org.bukkit.block.Block
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import taboolib.library.xseries.XMaterial
import taboolib.module.chat.colored
import taboolib.module.ui.type.Chest
import taboolib.module.ui.type.PageableChest
import taboolib.platform.util.Slots
import taboolib.platform.util.buildItem
import taboolib.platform.util.onlinePlayers

object Utils {

    fun getOnlinePlayers(player: Player): List<String> {
        val players = onlinePlayers.map { it.name }.toMutableList()
        players.remove(player.name)
        return players
    }

    fun createLoad(max: Double, now: Double, maxS: String, nowS: String): String {
        val a = (now / max)
        if (now == max) {
            return "${maxS}${maxS}${maxS}${maxS}${maxS}${maxS}${maxS}${maxS}${maxS}${maxS}"
        }
        val string = StringBuilder()
        (1..10).forEach {
            if (a > it / 10.0) {
                string.append(maxS.reversed())
            } else {
                string.append(nowS.reversed())
            }
        }
        return string.reversed().toString().colored()
    }

    fun <T> PageableChest<T>.inits(elements: List<T>) {
        rows(6)
        slots(Slots.CENTER)
        elements { elements }
        setPreviousPage(47) { _, hasPreviousPage ->
            if (hasPreviousPage) {
                buildItem(XMaterial.PAPER) {
                    name = "&7上一页".colored()
                    customModelData = 1
                }
            } else {
                ItemStack(Material.AIR)
            }
        }
        setNextPage(51) { _, hasNextPage ->
            if (hasNextPage) {
                buildItem(XMaterial.PAPER) {
                    name = "&7下一页".colored()
                    customModelData = 1
                }
            } else {
                ItemStack(Material.AIR)
            }
        }
    }

    fun <T> PageableChest<T>.flip(previous: Int, next: Int) {
        setPreviousPage(previous) { _, _ ->
            buildItem(XMaterial.ARROW) { name = "&8上一页".colored() }
        }
        setNextPage(next) { _, _ ->
            buildItem(XMaterial.ARROW) { name = "&8下一页".colored() }
        }
    }

    fun <T> PageableChest<T>.quit(player: Player) {
        set('X', buildItem(XMaterial.BARRIER) {
            name = "&c退出"
            customModelData = 1
            colored()
        }) {
            player.buttonNotice()
            player.closeInventory()
        }
    }

    fun Chest.quit(player: Player) {
        set('X', buildItem(XMaterial.BARRIER) {
            name = "&c退出"
            customModelData = 1
            colored()
        }) {
            player.buttonNotice()
            player.closeInventory()
        }
    }

    fun Block.display() {
        world.playEffect(location, Effect.STEP_SOUND, type)
    }

    fun Entity.bypass(hard: Boolean = false): Boolean {
        return (this !is Player) || (isOp && (gameMode == GameMode.CREATIVE) && (!hard || isSneaking))
    }

    fun Player.getTargetBlockExact(): Block? {
        return getTargetBlockExact(10, FluidCollisionMode.NEVER)
    }

    fun Player.bypass(): Boolean {
        return isOp || (gameMode == GameMode.CREATIVE)
    }

    fun fromLocation(location: Location): String {
        return "${location.world?.name},${location.x},${location.y},${location.z}".replace(".", "__")
    }

    fun toLocation(source: String): Location {
        return source.replace("__", ".").split(",").run {
            Location(
                Bukkit.getWorld(get(0)),
                getOrElse(1) { "0" }.toDouble(),
                getOrElse(2) { "0" }.toDouble(),
                getOrElse(3) { "0" }.toDouble(),
                0F,
                0F
            )
        }
    }
}