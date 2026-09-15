package me.siooraen.cres.module.basic.command

import me.siooraen.cres.util.error
import me.siooraen.cres.util.info
import org.bukkit.entity.Player
import org.bukkit.inventory.meta.ItemMeta
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.mainCommand
import taboolib.common.platform.command.subCommand
import taboolib.expansion.createHelper
import taboolib.module.chat.colored
import taboolib.module.nms.getName
import taboolib.platform.util.isAir
import taboolib.platform.util.modifyLore
import taboolib.platform.util.modifyMeta


@CommandHeader(name = "lore", permission = "*")
object CommandLore {

    @CommandBody
    val main = mainCommand {
        createHelper()
    }

    @CommandBody
    val add = subCommand {
        dynamic(comment = "lore") {
            execute<Player> { sender, _, argument ->
                val item = sender.inventory.itemInMainHand
                if (item.isAir()) {
                    sender.error("无效的物品.")
                    return@execute
                }
                val lore = argument.colored()
                item.modifyLore { add(lore) }
                item.itemMeta!!.lore!!.add(lore)
                sender.info("已为${item.getName()}增加一条描述.")
            }
        }
    }

    @CommandBody
    val insert = subCommand {
        dynamic(comment = "index") {
            dynamic(comment = "lore") {
                execute<Player> { sender, context, argument ->
                    val item = sender.inventory.itemInMainHand
                    if (item.isAir) {
                        sender.error("无效的物品.")
                        return@execute
                    }
                    val index = context["index"].toIntOrNull()!!
                    val lore = argument.colored()
                    item.modifyLore { add(index, lore) }
                    sender.info("已为${item.getName()}的第${index}行后添加一条描述")
                }
            }
        }
    }

    @CommandBody
    val remove = subCommand {
        dynamic(comment = "index", optional = true) {
            execute<Player> { sender, _, argument ->
                val item = sender.inventory.itemInMainHand
                if (item.isAir) {
                    sender.error("无效的物品.")
                    return@execute
                }
                val index = argument.toIntOrNull()!!
                item.modifyLore { removeAt(index) }
                sender.info("已删除${item.getName()}的第${index}条描述")
            }
        }
        execute<Player> { sender, _, _ ->
            val item = sender.inventory.itemInMainHand
            if (item.isAir) {
                sender.error("无效的物品.")
                return@execute
            }
            item.modifyLore { removeAt(-1) }
            sender.info("已删除${item.getName()}的最后一条描述")
        }
    }

    @CommandBody
    val name = subCommand {
        dynamic(comment = "name") {
            execute<Player> { sender, _, argument ->
                val item = sender.inventory.itemInMainHand
                if (item.isAir) {
                    sender.error("无效的物品.")
                    return@execute
                }
                val oldName = item.getName()
                val name = argument.colored()
                item.modifyMeta<ItemMeta> { setDisplayName(name) }
                sender.info("已将${oldName}的名字改为$name")
            }
        }
    }

}