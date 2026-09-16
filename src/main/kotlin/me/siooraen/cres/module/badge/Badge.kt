package me.siooraen.cres.module.badge

import me.siooraen.cres.module.Module
import me.siooraen.cres.module.ModuleState
import me.siooraen.cres.module.badge.data.BadgeData
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.module.configuration.createLocal

/**
 * @author Siooraen
 * @since 2022/6/8 21:07
 */
object Badge: Module {

    override val name: String
        get() = "徽章"

    override val id: String
        get() = "Badge"

    override val state: ModuleState
        get() = ModuleState.RUNNING

    val data by lazy { createLocal("module/badge/data.yml") }
    val badges = ArrayList<BadgeData>()

    @Awake(LifeCycle.ACTIVE)
    fun import() {
        badges.clear()
        data.getKeys(false).forEach {
            badges.add(
                BadgeData(
                    it,
                    data.getString("$it.name")!!,
                    data.getStringList("$it.desc").toMutableList(),
                    data.getString("$it.obtained")!!,
                    data.getInt("$it.custom-model-data")
                )
            )
        }
    }

    @Awake(LifeCycle.DISABLE)
    fun export() {
        data.getKeys(false).forEach { data[it] = null }
        badges.forEach { badge ->
            val id = badge.id
            data["$id.name"] = badge.name
            data["$id.desc"] = badge.desc
            data["$id.obtained"] = badge.obtaining
            data["$id.custom-model-data"] = badge.customModelData
        }
    }

    fun getBadgeData(id: String): BadgeData? {
        return badges.firstOrNull { it.isBadgeData(id) }
    }
}