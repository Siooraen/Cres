package me.siooraen.cres.module.guild

import me.siooraen.cres.module.Module
import me.siooraen.cres.module.ModuleState
import me.siooraen.cres.module.guild.data.GuildData
import java.util.*
import java.util.concurrent.ConcurrentHashMap

object Guild : Module {

    override val name: String
        get() = "公会"

    override val id: String
        get() = "Guild"

    override val state: ModuleState
        get() = ModuleState.RUNNING

    val guildData = ConcurrentHashMap<UUID, GuildData>()

    fun String.getGuildFromName(): GuildData? {
        guildData.values.forEach {
            if (this == it.name) {
                return it
            }
        }
        return null
    }

    fun String.getGuildFromAbbr(): GuildData? {
        guildData.values.forEach {
            if (this == it.abbreviation) {
                return it
            }
        }
        return null
    }

    fun UUID.getGuildFromUUID(): GuildData? {
        return guildData.getOrDefault(this, null)
    }
}