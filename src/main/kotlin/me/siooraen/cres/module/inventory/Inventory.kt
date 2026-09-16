package me.siooraen.cres.module.inventory

import me.siooraen.cres.module.Module
import me.siooraen.cres.module.ModuleState
import me.siooraen.cres.module.player.CresPlayer.getCresPlayerData
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.inventory.meta.CompassMeta
import taboolib.common.platform.Schedule
import taboolib.library.xseries.XMaterial
import taboolib.platform.util.buildItem
import taboolib.platform.util.modifyMeta

/**
 * @author Siooraen
 * @since 2023/7/12 22:22
 */
object Inventory : Module {

    override val name: String
        get() = "物品栏"

    override val id: String
        get() = "Inventory"

    override val state: ModuleState
        get() = ModuleState.RUNNING

    @Schedule(async = true, period = 20L)
    fun e() {
        Bukkit.getOnlinePlayers().forEach {
            if (it.gameMode != GameMode.ADVENTURE) {
                return@forEach
            }
            it.inventory.setItem(8, buildItem(XMaterial.BOOK) {
                name = "&5&l日记"
                colored()
            })
            it.inventory.setItem(7, buildItem(XMaterial.COMPASS) {
                name = "&6&l角色信息"
                colored()
            }.modifyMeta<CompassMeta> { lodestone = it.getCresPlayerData().trackLocation ?: return@modifyMeta })
        }
    }
}