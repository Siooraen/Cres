package me.siooraen.cres.module.player

import me.siooraen.cres.module.Module
import me.siooraen.cres.module.ModuleState
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.ConcurrentHashMap

object CresPlayer : Module {

    override val name: String
        get() = "玩家"

    override val id: String
        get() = "Player"

    override val state: ModuleState
        get() = ModuleState.RUNNING

    val playerData = ConcurrentHashMap<UUID, CresPlayerData>()

    fun Player.getCresPlayerData(): CresPlayerData {
        return playerData.getOrPut(this.uniqueId) { CresPlayerData(this.uniqueId) }
    }

    fun OfflinePlayer.getCresPlayerData(): CresPlayerData {
        return playerData.getOrPut(this.uniqueId) { CresPlayerData(this.uniqueId) }
    }

    fun UUID.getCresPlayerData(): CresPlayerData {
        return playerData.getOrPut(this) { CresPlayerData(this) }
    }

    fun Player.getCresDisplayName(): String {
        return this.getCresPlayerData().displayName
    }
}