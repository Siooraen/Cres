package me.siooraen.cres.module.guild.data

import java.util.*

class GuildData(
    val uuid: UUID,
    var name: String,
    var abbreviation: String,
    var owner: UUID
) {

    var description: String? = null
    var members: MutableList<UUID> = mutableListOf(owner)
    var level: Int = 1
    var exp: Int = 0
}