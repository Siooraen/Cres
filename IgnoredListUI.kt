package me.siooraen.cres.module.chat

import me.siooraen.cres.module.player.CresPlayer.getCresPlayerData
import me.siooraen.cres.util.Utils.inits
import org.bukkit.Sound
import org.bukkit.entity.Player
import taboolib.library.xseries.XMaterial
import taboolib.module.ui.openMenu
import taboolib.module.ui.type.PageableChest
import taboolib.platform.util.buildItem
import java.util.*

fun Player.openIgnoredListUI() {

    playSound(location, Sound.UI_BUTTON_CLICK, 1f, 2f)
    openMenu<PageableChest<UUID>> {
        inits(getCresPlayerData().ignored)
        onGenerate { player, element, index, slot ->
            val playerData = element.getCresPlayerData()
            buildItem(XMaterial.PLAYER_HEAD) {
                name = element.getCresPlayerData().realDisplayName
                customModelData = 1
                skullOwner = element.getCresPlayerData().name
                colored()
            }
        }
    }
}