package me.siooraen.cres.module.profile

import me.siooraen.cres.module.badge.data.BadgePlayerData
import me.siooraen.cres.module.player.CresPlayer.getCresPlayerData
import me.siooraen.cres.module.profile.Profile.getStatus
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import taboolib.library.xseries.XMaterial
import taboolib.module.chat.colored
import taboolib.module.ui.openMenu
import taboolib.module.ui.type.PageableChest
import taboolib.platform.util.buildItem
import java.util.*

/**
 * @author Siooraen
 * @since 2022/4/30 19:43
 */
fun Player.openProfileUI(target: UUID) {

    playSound(location, Sound.UI_BUTTON_CLICK, 1f, 2f)
    openMenu<PageableChest<BadgePlayerData>>("&f\uF808\uF001".colored()) {
        map(
            "000   SPP",
            "000    MM",
            "000 HWTFF",
            "<@@@@@>TT"
        )
        slotsBy('@')
        elements { this@openProfileUI.getCresPlayerData().badges }
        onGenerate { _, element, _, _ ->
            val badgeData = element.badgeData
            buildItem(XMaterial.PAPER) {
                name = badgeData.name
                badgeData.desc.forEach { lore += it }
                lore += listOf("", badgeData.obtaining)
            }
        }
        setNextPage(27) { _, hasNextPage ->
            if (!hasNextPage && target.getCresPlayerData().badges.size <= 6) {
                buildItem(Material.AIR)
            } else {
                if (hasNextPage) {
                    buildItem(XMaterial.ARROW) {
                        name = "&7下一页"
                        customModelData = 4
                        colored()
                    }
                } else {
                    buildItem(Material.AIR)
                }
            }
        }
        setPreviousPage(33) { _, hasPreviousPage ->
            if (!hasPreviousPage && target.getCresPlayerData().badges.size <= 6) {
                buildItem(XMaterial.AIR)
            } else {
                if (hasPreviousPage) {
                    buildItem(XMaterial.ARROW) {
                        name = "&7上一页"
                        customModelData = 5
                        colored()
                    }
                } else {
                    buildItem(XMaterial.ARROW) {
                        name = "&8上一页"
                        customModelData = 6
                        colored()
                    }
                }
            }
        }
        set('0', buildItem(XMaterial.PAPER) {
            name = "&f${target.getCresPlayerData().realDisplayName}"
            customModelData = 1
            colored()
        })
        set('H', buildItem(XMaterial.PLAYER_HEAD) {
            name = "&7玩家等级: "
            customModelData = 1
            skullOwner = target.getCresPlayerData().name
            colored()
        }) {
            closeInventory()
        }
        set('W', buildItem(XMaterial.PAPER) {
            name = "&7财富信息"
            customModelData = 1
            colored()
        })
        set('T', buildItem(XMaterial.PAPER) {
            name = "&7时间信息"
            customModelData = 1
            colored()
        })
        set('S', buildItem(XMaterial.PAPER) {
            name = getStatus().display
            customModelData = getStatus().customModelData
            colored()
            hideAll()
        })
        set('P', buildItem(XMaterial.PAPER) {
            name = "&7请求组队"
            customModelData = 1
            colored()
        }) {
            closeInventory()
        }
        set('M', buildItem(XMaterial.PAPER) {
            name = "&7发送邮件"
            customModelData = 1
            colored()
        }) {
            closeInventory()
        }
        set('F', buildItem(XMaterial.PAPER) {
            name = "&7添加好友"
            customModelData = 1
            colored()
        }) {
            closeInventory()
        }
        set('T', buildItem(XMaterial.PAPER) {
            name = "&7请求交易"
            customModelData = 1
            colored()
        }) {
            closeInventory()
        }
    }
}