package me.siooraen.cres.module.badge.data

import me.siooraen.cres.util.error
import org.bukkit.Material
import org.bukkit.entity.Player
import taboolib.common.platform.function.submit
import taboolib.library.xseries.XMaterial
import taboolib.module.nms.inputSign
import taboolib.module.ui.openMenu
import taboolib.module.ui.type.Chest
import taboolib.platform.util.buildBook
import taboolib.platform.util.buildItem
import taboolib.platform.util.giveItem

/**
 * @author Siooraen
 * @since 2022/6/11 21:00
 */
fun BadgeData.openEditor(player: Player) {
    player.openMenu<Chest>("编辑徽章 ${this.name}") {
        map(
            "         ",
            "    B    ",
            "         ",
            "  N D O  ",
            "    C    ",
            "         "
        )
        set('B', buildItem(XMaterial.PAPER) {
            name = "&f${this@openEditor.name}"
            this@openEditor.desc.forEach { lore.add("&7$it") }
            lore.add("")
            lore.add("&f${this@openEditor.obtaining}")
            lore.add("&8${this@openEditor.id}")
            customModelData = this@openEditor.customModelData
            colored()
        })
        set('N', buildItem(XMaterial.NAME_TAG) {
            name = "&f编辑名字"
            colored()
        }) {
            player.closeInventory()
            player.inputSign(arrayOf("", "", "请在第一行输入名字", "")) { lens ->
                val new = lens[0]
                if (new.isEmpty()) {
                    player.error("无效的名字.")
                    return@inputSign
                }
                name = new
                submit(delay = 1) { openEditor(player) }
            }
        }
        set('D', buildItem(XMaterial.OAK_SIGN) {
            name = "&f编辑描述"
            colored()
        }) {
            player.closeInventory()
            player.giveItem(buildBook {
                material = Material.WRITABLE_BOOK
                this@openEditor.desc.forEach { write(it) }
                name = "&7编辑描述"
                lore += listOf("&7Badge", "&7${this@openEditor.id}")
                colored()
            })
        }
        set('O', buildItem(XMaterial.TRIPWIRE_HOOK) {
            name = "&f编辑获取方式"
            colored()
        }) {
            player.closeInventory()
            player.inputSign(arrayOf("", "", "请在第一行输入获取方式", "")) { lens ->
                val new = lens[0]
                if (new.isEmpty()) {
                    player.error("无效的获取方式.")
                    return@inputSign
                }
                obtaining = new
                submit(delay = 1) { openEditor(player) }
            }
        }
        set('C', buildItem(XMaterial.NAME_TAG) {
            name = "&f编辑CustomModelData"
            colored()
        }) {
            player.closeInventory()
            player.inputSign(arrayOf("", "", "请在第一行输入CustomModelData", "")) { lens ->
                val new = lens[0].toIntOrNull()
                if (new == null) {
                    player.error("无效的CustomModelData.")
                    return@inputSign
                }
                customModelData = new
                submit(delay = 1) { openEditor(player) }
            }
        }
    }
}