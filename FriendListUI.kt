package me.siooraen.cres.module.friend

import me.siooraen.cres.module.player.CresPlayer.getCresPlayerData
import me.siooraen.cres.module.profile.openProfileUI
import me.siooraen.cres.util.Utils.flip
import me.siooraen.cres.util.Utils.quit
import me.siooraen.cres.util.buttonNotice
import me.siooraen.cres.util.info
import org.bukkit.Bukkit.getOfflinePlayer
import org.bukkit.Sound
import org.bukkit.entity.Player
import taboolib.library.xseries.XMaterial
import taboolib.module.ui.openMenu
import taboolib.module.ui.type.Chest
import taboolib.module.ui.type.PageableChest
import taboolib.platform.util.buildItem
import java.util.*

private fun <T> PageableChest<T>.friendInits(player: Player) {
    map(
        "X  AASSRR",
        "@@@@@@@@@",
        "@@@@@@@@@",
        "@@@@@@@@@",
        "@@@@@@@@@",
        "<       >"
    )
    slotsBy('@')
    quit(player)
    flip(45, 53)
}

fun Player.openAllFriendUI() {
    openMenu<PageableChest<UUID>>("all") {
        getCresPlayerData().updateFriendList()
        elements { getCresPlayerData().friends }
        friendInits(this@openAllFriendUI)
        set('A', buildItem(XMaterial.PAPER) {
            name = "&8全部好友"
            customModelData = 1
            colored()
        })
        set('S', buildItem(XMaterial.PAPER) {
            name = "&8发送的申请"
            customModelData = 1
            colored()
        }) {
            buttonNotice()
            openSentRequestFriendUI()
        }
        set('R', buildItem(XMaterial.PAPER) {
            name = "&8收到的申请"
            customModelData = 1
            colored()
        }) {
            buttonNotice()
            openReceivedRequestFriendUI()
        }
        onGenerate { _, element, _, _ ->
            if (getOfflinePlayer(element).isOnline) {
                buildItem(XMaterial.PLAYER_HEAD) {
                    name = element.getCresPlayerData().realDisplayName
                    lore += "&a在线"
                    lore += ""
                    lore += " &a→ 点击查看档案"
                    lore += " &c→ SHIFT点击删除好友"
                    customModelData = 2
                    skullOwner = element.getCresPlayerData().name
                    colored()
                }
            } else {
                buildItem(XMaterial.PLAYER_HEAD) {
                    name = element.getCresPlayerData().realDisplayName
                    lore += "&7上次在线: &f${element.getCresPlayerData().lastOnline}"
                    lore += ""
                    lore += " &a→ 点击查看档案"
                    lore += " &c→ SHIFT点击删除好友"
                    customModelData = 2
                    skullOwner = element.getCresPlayerData().name
                    colored()
                }
            }
        }
        onClick { event, element ->
            if (event.clickEvent().isShiftClick) {
                manageFriend(element)
                return@onClick
            }
            openProfileUI(element)
        }
    }
}

fun Player.openReceivedRequestFriendUI() {
    openMenu<PageableChest<UUID>>("received") {
        elements { getCresPlayerData().friendReceivedRequests }
        friendInits(this@openReceivedRequestFriendUI)
        set('A', buildItem(XMaterial.PAPER) {
            name = "&8全部好友"
            customModelData = 1
            colored()
        }) {
            buttonNotice()
            openAllFriendUI()
        }
        set('S', buildItem(XMaterial.PAPER) {
            name = "&8发送的申请"
            customModelData = 1
            colored()
        }) {
            buttonNotice()
            openSentRequestFriendUI()
        }
        set('R', buildItem(XMaterial.PAPER) {
            name = "&8收到的申请"
            customModelData = 1
            colored()
        })
        onGenerate { _, element, _, _ ->
            buildItem(XMaterial.PLAYER_HEAD) {
                name = element.getCresPlayerData().realDisplayName
                lore += ""
                lore += " &a→ 点击管理申请"
                customModelData = 2
                skullOwner = element.getCresPlayerData().name
                colored()
            }
        }
        onClick { _, element ->
            manageReceivedRequest(element)
        }
    }
}

fun Player.openSentRequestFriendUI() {
    openMenu<PageableChest<UUID>>("sent") {
        elements { getCresPlayerData().friendSentRequests }
        friendInits(this@openSentRequestFriendUI)
        set('A', buildItem(XMaterial.PAPER) {
            name = "&8全部好友"
            customModelData = 1
            colored()
        }) {
            buttonNotice()
            openAllFriendUI()
        }
        set('S', buildItem(XMaterial.PAPER) {
            name = "&8发送的申请"
            customModelData = 1
            colored()
        })
        set('R', buildItem(XMaterial.PAPER) {
            name = "&8收到的申请"
            customModelData = 1
            colored()
        }) {
            buttonNotice()
            openReceivedRequestFriendUI()
        }
        onGenerate { _, element, _, _ ->
            buildItem(XMaterial.PLAYER_HEAD) {
                name = element.getCresPlayerData().realDisplayName
                lore += ""
                lore += " &a→ 点击查看档案"
                lore += " &c→ SHIFT点击取消申请"
                customModelData = 2
                skullOwner = element.getCresPlayerData().name
                colored()
            }
        }
        onClick { event, element ->
            if (event.clickEvent().isShiftClick) {
                playSound(this@openSentRequestFriendUI.location, Sound.ENTITY_VILLAGER_HURT, 1f, 1f)
                getCresPlayerData().friendSentRequests.remove(element)
                element.getCresPlayerData().friendSentRequests.remove(uniqueId)
                info("已删除向${element.getCresPlayerData().name}的好友申请.", false)
                openSentRequestFriendUI()
                return@onClick
            }
            openProfileUI(element)
        }
    }
}

fun Player.manageFriend(target: UUID) {
    val data = target.getCresPlayerData()
    buttonNotice()
    openMenu<Chest>("manageFriend") {
        map(
            "    P    ",
            " AA   BB "
        )
        set('P', buildItem(XMaterial.PLAYER_HEAD) {
            name = data.realDisplayName
            customModelData = 2
            skullOwner = data.name
            colored()
        })
        set('A', buildItem(XMaterial.PAPER) {
            name = "&a确认"
            customModelData = 1
            colored()
        }) {
            playSound(this@manageFriend.location, Sound.ENTITY_VILLAGER_HURT, 1f, 1f)
            getCresPlayerData().friends.remove(target)
            getCresPlayerData().updateFriendList()
            data.friends.remove(uniqueId)
            data.updateFriendList()
            info("已删除${data.name}的好友.", false)
            openAllFriendUI()
        }
        set('B', buildItem(XMaterial.PAPER) {
            name = "&c取消"
            customModelData = 1
            colored()
        }) {
            buttonNotice()
            openAllFriendUI()
        }
    }
}

fun Player.manageReceivedRequest(target: UUID) {
    val data = target.getCresPlayerData()
    buttonNotice()
    openMenu<Chest>("manageReceivedRequest") {
        map(
            "    P    ",
            " AA   RR "
        )
        set('P', buildItem(XMaterial.PLAYER_HEAD) {
            name = data.realDisplayName
            customModelData = 2
            skullOwner = data.name
            colored()
        })
        set('A', buildItem(XMaterial.PAPER) {
            name = "&a同意"
            customModelData = 1
            colored()
        }) {
            buttonNotice()
            getCresPlayerData().friendReceivedRequests.remove(target)
            getCresPlayerData().friends.add(target)
            getCresPlayerData().updateFriendList()
            data.friendSentRequests.remove(uniqueId)
            data.friends.add(uniqueId)
            data.updateFriendList()
            if (getOfflinePlayer(target).isOnline) {
                data.player.info("${name}已同意你的好友申请.")
            }
            info("已同意${data.name}的好友申请.")
            openReceivedRequestFriendUI()
        }
        set('R', buildItem(XMaterial.PAPER) {
            name = "&c拒绝"
            customModelData = 1
            colored()
        }) {
            buttonNotice()
            getCresPlayerData().friendReceivedRequests.remove(target)
            getCresPlayerData().updateFriendList()
            data.friendSentRequests.remove(uniqueId)
            data.updateFriendList()
            if (getOfflinePlayer(target).isOnline) {
                data.player.info("${name}已拒绝你的好友申请.")
            }
            info("已拒绝${data.name}的好友申请.")
            openReceivedRequestFriendUI()
        }
    }
}