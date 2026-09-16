package me.siooraen.cres.module.badge

import me.siooraen.cres.module.badge.Badge.badges
import me.siooraen.cres.module.badge.data.BadgeData
import me.siooraen.cres.module.badge.data.openEditor
import me.siooraen.cres.util.Utils.inits
import org.bukkit.Sound
import org.bukkit.entity.Player
import taboolib.library.xseries.XMaterial
import taboolib.module.ui.openMenu
import taboolib.module.ui.type.PageableChest
import taboolib.platform.util.buildItem

/**
 * @author Siooraen
 * @since 2022/6/11 20:51
 */
fun Player.openBadgeUI() {

    playSound(location, Sound.UI_BUTTON_CLICK, 1f, 2f)
    openMenu<PageableChest<BadgeData>>("徽章列表") {
        inits(badges)
        onGenerate { _, element, _, _ ->
            buildItem(XMaterial.PAPER) {
                name = "&f${element.name}"
                element.desc.forEach { lore.add("&7$it") }
                lore.add("")
                lore.add("&f${element.obtaining}")
                lore.add("&8${element.id}")
                customModelData = element.customModelData
                colored()
            }
        }
        onClick { _, element ->
            element.openEditor(this@openBadgeUI)
        }
    }
}