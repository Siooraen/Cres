package me.siooraen.cres.module.chat

import me.siooraen.cres.module.player.CresPlayer.getCresPlayerData
import me.siooraen.cres.module.player.CresPlayerRank
import me.siooraen.cres.util.error
import me.siooraen.cres.util.info
import org.bukkit.entity.Player
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.PermissionDefault
import taboolib.common.platform.command.mainCommand
import taboolib.module.chat.uncolored

@CommandHeader(name = "NICKNAME", permissionDefault = PermissionDefault.TRUE)
object ChatNickNameCommand {

    @CommandBody
    val main = mainCommand {
        dynamic(comment = "nickname") {
            execute<Player> { sender, _, argument ->
                if (sender.getCresPlayerData().rank == CresPlayerRank.DEFAULT
                    || sender.getCresPlayerData().rank == CresPlayerRank.ELITE
                ) {
                    sender.error("此功能仅限大师和英雄使用! 在 store.cresworld.com 购买来使用此功能.")
                } else {
                    if (argument.length > 12) {
                        sender.error("昵称过长!")
                        return@execute
                    }
                    sender.getCresPlayerData().nickName = argument.uncolored()
                    sender.getCresPlayerData().updateDisplayName()
                    sender.info("已更改昵称为 ${sender.getCresPlayerData().rank.color}${argument}&7.")
                }
            }
        }
        execute<Player> { sender, _, _ ->
            if (sender.getCresPlayerData().rank == CresPlayerRank.DEFAULT
                || sender.getCresPlayerData().rank == CresPlayerRank.ELITE
            ) {
                sender.error("此功能仅限大师和英雄使用! 在 store.cresworld.com 购买来使用此功能.")
            } else {
                sender.error("错误用法. 用法 /nickname [昵称]")
            }
        }
    }
}