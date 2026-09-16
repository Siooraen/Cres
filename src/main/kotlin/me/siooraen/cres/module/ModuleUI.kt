package me.siooraen.cres.module

import me.siooraen.cres.module.Module.Companion.modules
import me.siooraen.cres.util.Utils.inits
import org.bukkit.Sound
import org.bukkit.entity.Player
import taboolib.library.xseries.XMaterial
import taboolib.module.ui.openMenu
import taboolib.module.ui.type.PageableChest
import taboolib.platform.util.buildItem

/**
 * @author Siooraen
 * @since 2022/2/2 21:09
 */
fun Player.openModuleUI() {

    playSound(location, Sound.UI_BUTTON_CLICK, 1f, 2f)
    openMenu<PageableChest<Module>>("Cres (${modules.size}个模块)") {
        inits(modules)
        onGenerate { _, element, _, _ ->
            buildItem(XMaterial.PAPER) {
                name = "&f${element.name}"
                lore.add("&7ID: &f${element.id}")
                lore.add("&7状态: &f${element.state}")
                lore.add("&7优先级: &f${element.priority}")
                colored()
            }
        }
    }
}