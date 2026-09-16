package me.siooraen.cres.module.player

import me.siooraen.cres.module.badge.data.BadgePlayerData
import me.siooraen.cres.module.chat.ChatChannel
import org.bukkit.Bukkit.getOfflinePlayer
import org.bukkit.Bukkit.getPlayer
import org.bukkit.Location
import org.bukkit.entity.Player
import taboolib.module.chat.colored
import java.util.*

class CresPlayerData(
    val uuid: UUID,
) {
    var rank: CresPlayerRank = CresPlayerRank.DEFAULT
    var nickName: String? = null
    var realDisplayName: String = "${rank.icon}${rank.color}${player.name}".colored()
    var displayName: String = "${rank.icon}${rank.color}${
        if (nickName == null) {
            player.name
        } else {
            "&o$nickName"
        }
    }".colored()

    var chatChannel: ChatChannel = ChatChannel.GLOBAL
    var msgSender: UUID? = null

    var trackLocation: Location? = null

    var lastOnline: Date? = null

    var inCombat: Boolean = false

    var guild: UUID? = null
    var badges: MutableList<BadgePlayerData> = mutableListOf()
    var fastTravelPoints: MutableList<String> = mutableListOf()

    var ignored: MutableList<UUID> = mutableListOf()
    var friends: MutableList<UUID> = mutableListOf()
    var friendSentRequests: MutableList<UUID> = mutableListOf()
    var friendReceivedRequests: MutableList<UUID> = mutableListOf()

    var level: Int = 1
    var exp: Int = 0
    var soul: Int = 10
    var maxSoul: Int = 10
    var mana: Double = 0.0
    var maxMana: Double = 100.0
    var stamina: Double = 0.0
    var maxStamina: Double = 100.0

    val player: Player
        get() = getPlayer(uuid)!!

    val name: String
        get() = player.name

    fun addSoul(amount: Int) {
        if (soul + amount <= maxSoul) {
            soul += amount
        } else {
            soul = maxSoul
        }
    }

    fun updateIgnoredList() {
        ignored.sortedBy { getOfflinePlayer(it).name }
    }

    fun updateFriendList() {
        friends.sortWith { p1, p2 ->
            if (getOfflinePlayer(p1).isOnline != getOfflinePlayer(p2).isOnline) {
                getOfflinePlayer(p2).isOnline.compareTo(getOfflinePlayer(p1).isOnline)
            } else {
                getOfflinePlayer(p1).name!!.compareTo(getOfflinePlayer(p2).name!!)
            }
        }
    }

    fun updateDisplayName() {
        displayName = "${rank.icon}${rank.color}${
            if (nickName == null) {
                player.name
            } else {
                "&o$nickName"
            }
        }".colored()
        player.setPlayerListName(displayName)
    }
}