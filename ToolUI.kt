package me.siooraen.cres.module.tool

import org.bukkit.Sound
import org.bukkit.entity.Player
import taboolib.library.xseries.XMaterial
import taboolib.module.ui.openMenu
import taboolib.module.ui.type.Chest
import taboolib.platform.util.buildItem
import taboolib.platform.util.giveItem

/**
 * @author Siooraen
 * @since 2023/7/23 10:57
 */
fun Player.openToolUI() {

    playSound(location, Sound.UI_BUTTON_CLICK, 1f, 2f)
    openMenu<Chest>("工具列表") {
        map(
            "         ",
            " 12      ",
            "         ",
            "         ",
            "         ",
            "         "
        )
        set('1', buildItem(XMaterial.STICK) {
            name = "&f活塞头放置棒"
            lore += "&8点击获取"
            colored()
        }) {
            this@openToolUI.giveItem(buildItem(XMaterial.STICK) {
                name = "&f活塞头放置棒"
                colored()
            })
        }
        set('2', buildItem(XMaterial.STICK) {
            name = "&fTP棒"
            lore += "&8点击获取"
            colored()
        }) {
            this@openToolUI.giveItem(buildItem(XMaterial.STICK) {
                name = "&fTP棒"
                colored()
            })
        }
    }
}