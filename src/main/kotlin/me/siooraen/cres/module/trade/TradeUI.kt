package me.siooraen.cres.module.trade

import me.siooraen.cres.module.player.CresPlayer.getCresDisplayName
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import taboolib.common.platform.function.submit
import taboolib.common.platform.service.PlatformExecutor
import taboolib.library.xseries.XMaterial
import taboolib.module.ui.openMenu
import taboolib.module.ui.type.Chest
import taboolib.platform.util.buildItem
import taboolib.platform.util.giveItem

/**
 * @author Siooraen
 * @since 2023/8/7 17:49
 */
fun trade(
    player1: Player,
    content1: MutableList<ItemStack>,
    progress1: TradeProgress,
    player2: Player,
    content2: MutableList<ItemStack>,
    progress2: TradeProgress,
) {

    var cancelCountdown1: PlatformExecutor.PlatformTask? = null
    var cancelCountdown2: PlatformExecutor.PlatformTask? = null

    player1.closeInventory()
    player1.openMenu<Chest>("${progress1.edge} ${progress1.button} ${progress2.edge}") {
        if (progress1 == TradeProgress.CANCEL && progress2 == TradeProgress.CANCEL) {
            player1.setCooldown(Material.BARRIER, 60)
            cancelCountdown1 = submit(delay = 60) {
                player1.giveItem(content2)
                player1.closeInventory()
            }
        }
        rows(6)
        onClick {
            it.isCancelled = true
            it.currentItem?.let { item ->
                if (it.rawSlot >= 54 && item.type != Material.AIR && content1.size < 15) {
                    content1.add(item)
                    player1.inventory.setItem(it.rawSlot, ItemStack(Material.AIR, 1))
                    trade(player1, content1, TradeProgress.CONFIRM, player2, content2, TradeProgress.CONFIRM)
                }
            }
        }
        content1.forEachIndexed { index, itemStack ->
            set(
                when (index) {
                    in 0..2 -> index + 19
                    in 3..6 -> index + 24
                    in 7..10 -> index + 29
                    else -> index + 34
                }, itemStack
            ) {
                player1.giveItem(itemStack)
                content1.removeAt(index)
                trade(player1, content1, TradeProgress.CONFIRM, player2, content2, TradeProgress.CONFIRM)
                cancelCountdown1?.cancel()
                cancelCountdown2?.cancel()
                player1.setCooldown(Material.BARRIER, 0)
                player2.setCooldown(Material.BARRIER, 0)
            }
        }
        content2.forEachIndexed { index, itemStack ->
            set(
                when (index) {
                    in 0..2 -> index + 24
                    in 3..6 -> index + 29
                    in 7..10 -> index + 34
                    else -> index + 39
                }, itemStack
            )
        }
        set(1, XMaterial.PLAYER_HEAD) {
            name = player1.getCresDisplayName()
            customModelData = 2
            skullOwner = player1.name
            colored()
        }
        set(7, XMaterial.PLAYER_HEAD) {
            name = player2.getCresDisplayName()
            customModelData = 2
            skullOwner = player2.name
            colored()
        }
        set(18, XMaterial.PAPER) {
            customModelData = 1
            name = "&7提供的金币数量: "
            lore += listOf(
                "", "&a点击更改金币数量"
            )
            colored()
        }
        set(23, XMaterial.PAPER) {
            customModelData = 1
            name = "&7提供的金币数量: "
            colored()
        }
        listOf(3, 4, 5).forEach { slot ->
            set(slot, buildItem(XMaterial.BARRIER) {
                customModelData = 1
                name = progress1.display
                colored()
            }) {
                when (progress1) {
                    TradeProgress.CONFIRM -> when (progress2) {
                        TradeProgress.CONFIRM -> trade(
                            player1, content1, TradeProgress.CANCEL, player2, content2, TradeProgress.CONFIRM
                        )

                        TradeProgress.CANCEL -> trade(
                            player1, content1, TradeProgress.CANCEL, player2, content2, TradeProgress.CANCEL
                        )
                    }

                    TradeProgress.CANCEL -> {
                        cancelCountdown1?.cancel()
                        cancelCountdown2?.cancel()
                        player1.setCooldown(Material.BARRIER, 0)
                        player2.setCooldown(Material.BARRIER, 0)
                        trade(
                            player1, content1, TradeProgress.CONFIRM, player2, content2, TradeProgress.CONFIRM
                        )
                    }
                }
            }
        }
    }

    player2.closeInventory()
    player2.openMenu<Chest>("${progress2.edge} ${progress2.button} ${progress1.edge}") {
        if (progress1 == TradeProgress.CANCEL && progress2 == TradeProgress.CANCEL) {
            player2.setCooldown(Material.BARRIER, 60)
            cancelCountdown2 = submit(delay = 60) {
                player2.giveItem(content1)
                player2.closeInventory()
            }
        }
        rows(6)
        onClick {
            it.isCancelled = true
            it.currentItem?.let { item ->
                if (it.rawSlot >= 54 && item.type != Material.AIR && content2.size < 15) {
                    content2.add(item)
                    player2.inventory.setItem(it.rawSlot, ItemStack(Material.AIR, 1))
                    trade(player1, content1, TradeProgress.CONFIRM, player2, content2, TradeProgress.CONFIRM)
                }
            }
        }
        content2.forEachIndexed { index, itemStack ->
            set(
                when (index) {
                    in 0..2 -> index + 19
                    in 3..6 -> index + 24
                    in 7..10 -> index + 29
                    else -> index + 34
                }, itemStack
            ) {
                player2.giveItem(itemStack)
                content2.removeAt(index)
                trade(player1, content1, TradeProgress.CONFIRM, player2, content2, TradeProgress.CONFIRM)
                cancelCountdown1?.cancel()
                cancelCountdown2?.cancel()
                player1.setCooldown(Material.BARRIER, 0)
                player2.setCooldown(Material.BARRIER, 0)
            }
        }
        content1.forEachIndexed { index, itemStack ->
            set(
                when (index) {
                    in 0..2 -> index + 24
                    in 3..6 -> index + 29
                    in 7..10 -> index + 34
                    else -> index + 39
                }, itemStack
            )
        }
        set(1, XMaterial.PLAYER_HEAD) {
            name = player2.getCresDisplayName()
            customModelData = 2
            skullOwner = player2.name
            colored()
        }
        set(7, XMaterial.PLAYER_HEAD) {
            name = player1.getCresDisplayName()
            customModelData = 2
            skullOwner = player1.name
            colored()
        }
        set(18, XMaterial.PAPER) {
            customModelData = 1
            name = "&7提供的金币数量: "
            lore += listOf(
                "", "&a点击更改金币数量"
            )
            colored()
        }
        set(23, XMaterial.PAPER) {
            customModelData = 1
            name = "&7提供的金币数量: "
            colored()
        }
        listOf(3, 4, 5).forEach { slot ->
            set(slot, buildItem(XMaterial.BARRIER) {
                customModelData = 1
                name = progress2.display
                colored()
            }) {
                when (progress2) {
                    TradeProgress.CONFIRM -> when (progress1) {
                        TradeProgress.CONFIRM -> trade(
                            player1, content1, TradeProgress.CONFIRM, player2, content2, TradeProgress.CANCEL
                        )

                        TradeProgress.CANCEL -> trade(
                            player1, content1, TradeProgress.CANCEL, player2, content2, TradeProgress.CANCEL
                        )
                    }

                    TradeProgress.CANCEL -> {
                        cancelCountdown1?.cancel()
                        cancelCountdown2?.cancel()
                        player1.setCooldown(Material.BARRIER, 0)
                        player2.setCooldown(Material.BARRIER, 0)
                        trade(
                            player1, content1, TradeProgress.CONFIRM, player2, content2, TradeProgress.CONFIRM
                        )
                    }
                }
            }
        }
    }
}

//    val content1 = mutableListOf<ItemStack>()
//    val content2 = mutableListOf<ItemStack>()
//    var progress1 = TradeProgress.CONFIRM
//    var progress2 = TradeProgress.CONFIRM
//    var cancelCountdown1: PlatformExecutor.PlatformTask? = null
//    var cancelCountdown2: PlatformExecutor.PlatformTask? = null
//    player1.closeInventory()
//    player1.openMenu<Basic>("${TradeProgress.CONFIRM.edge} ${TradeProgress.CONFIRM.button} ${TradeProgress.CONFIRM.edge}") {
//        rows(6)
//        onClick {
//            it.isCancelled = true
//            if (it.rawSlot >= 54 && content1.size < 15 && it.currentItem != null && it.currentItem!!.type != Material.AIR) {
//                content1.add(it.currentItem!!)
//                content1.forEachIndexed { index, item ->
//                    set(
//                        when (index) {
//                            in 0..2 -> index + 19
//                            in 3..6 -> index + 24
//                            in 7..10 -> index + 29
//                            else -> index + 34
//                        }, item
//                    ) {
//                        player1.giveItem(item)
//                        it.inventory.setItem(it.rawSlot, ItemStack(Material.AIR, 1))
//                        content1.removeAt(index)
//                    }
//                }
//            }
//        }
//        content1.forEachIndexed { index, item ->
//            set(
//                when (index) {
//                    in 0..2 -> index + 19
//                    in 3..6 -> index + 24
//                    in 7..10 -> index + 29
//                    else -> index + 34
//                }, item
//            ) {
//                player1.giveItem(item)
//                content1.removeAt(index)
//            }
//        }
//        set(1, XMaterial.PLAYER_HEAD) {
//            name = player1.getCresDisplayName()
//            customModelData = 2
//            skullOwner = player1.name
//            colored()
//        }
//        set(7, XMaterial.PLAYER_HEAD) {
//            name = player2.getCresDisplayName()
//            customModelData = 2
//            skullOwner = player2.name
//            colored()
//        }
//        set(18, XMaterial.PAPER) {
//            customModelData = 1
//            name = "&7提供的金币数量: "
//            lore += listOf(
//                "", "&a点击更改金币数量"
//            )
//            colored()
//        }
//        set(23, XMaterial.PAPER) {
//            customModelData = 1
//            name = "&7提供的金币数量: "
//            colored()
//        }
//        submit(async = true, period = 10) {
//            this@openMenu.title = "${progress1.edge} ${progress1.button} ${progress2.edge}"
//            listOf(3, 4, 5).forEach { slot ->
//                set(slot, buildItem(XMaterial.BARRIER) {
//                    customModelData = 1
//                    name = progress1.display
//                    colored()
//                }) {
//                    when (progress1) {
//                        TradeProgress.CONFIRM -> when (progress2) {
//                            TradeProgress.CONFIRM -> progress1 = TradeProgress.CANCEL
//                            TradeProgress.CANCEL -> {
//                                player1.setCooldown(Material.BARRIER, 80)
//                                player2.setCooldown(Material.BARRIER, 80)
//                                cancelCountdown1 = submit(period = 80) {
//                                    player1.giveItem(content2)
//                                    player1.closeInventory()
//                                }
//                                cancelCountdown2 = submit(period = 80) {
//                                    player2.giveItem(content1)
//                                    player2.closeInventory()
//                                }
//                            }
//                        }
//
//                        TradeProgress.CANCEL -> when (progress2) {
//                            TradeProgress.CONFIRM -> progress1 = TradeProgress.CONFIRM
//                            TradeProgress.CANCEL -> {
//                                cancelCountdown1?.cancel()
//                                cancelCountdown2?.cancel()
//                                player1.setCooldown(Material.BARRIER, 0)
//                                player2.setCooldown(Material.BARRIER, 0)
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    player2.closeInventory()
//    player2.openMenu<Basic>("${TradeProgress.CONFIRM.edge} ${TradeProgress.CONFIRM.button} ${TradeProgress.CONFIRM.edge}") {
//        rows(6)
//        onClick {
//            it.isCancelled = true
//            if (it.rawSlot >= 54 && content1.size < 15 && it.currentItem != null && it.currentItem!!.type != Material.AIR) {
//                content1.add(it.currentItem!!)
//                content1.forEachIndexed { index, item ->
//                    set(
//                        when (index) {
//                            in 0..2 -> index + 19
//                            in 3..6 -> index + 24
//                            in 7..10 -> index + 29
//                            else -> index + 34
//                        }, item
//                    ) {
//                        player1.giveItem(item)
//                        content1.removeAt(index)
//                    }
//                }
//            }
//        }
//        content2.forEachIndexed { index, item ->
//            set(
//                when (index) {
//                    in 0..2 -> index + 19
//                    in 3..6 -> index + 24
//                    in 7..10 -> index + 29
//                    else -> index + 34
//                }, item
//            ) {
//                player1.giveItem(item)
//                content1.removeAt(index)
//            }
//        }
//        set(1, XMaterial.PLAYER_HEAD) {
//            name = player2.getCresDisplayName()
//            customModelData = 2
//            skullOwner = player2.name
//            colored()
//        }
//        set(7, XMaterial.PLAYER_HEAD) {
//            name = player1.getCresDisplayName()
//            customModelData = 2
//            skullOwner = player1.name
//            colored()
//        }
//        set(18, XMaterial.PAPER) {
//            customModelData = 1
//            name = "&7提供的金币数量: "
//            colored()
//        }
//        set(23, XMaterial.PAPER) {
//            customModelData = 1
//            name = "&7提供的金币数量: "
//            lore += listOf(
//                "", "&a点击更改金币数量"
//            )
//            colored()
//        }
//        submit(async = true, period = 10) {
//            this@openMenu.title = "${progress2.edge} ${progress2.button} ${progress1.edge}"
//            listOf(3, 4, 5).forEach { slot ->
//                set(slot, buildItem(XMaterial.BARRIER) {
//                    customModelData = 1
//                    name = progress2.display
//                    colored()
//                }) {
//                    when (progress2) {
//                        TradeProgress.CONFIRM -> when (progress1) {
//                            TradeProgress.CONFIRM -> progress2 = TradeProgress.CANCEL
//                            TradeProgress.CANCEL -> {
//                                player1.setCooldown(Material.BARRIER, 80)
//                                player2.setCooldown(Material.BARRIER, 80)
//                                cancelCountdown1 = submit(period = 80) {
//                                    player1.giveItem(content2)
//                                    player1.closeInventory()
//                                }
//                                cancelCountdown2 = submit(period = 80) {
//                                    player2.giveItem(content1)
//                                    player2.closeInventory()
//                                }
//                            }
//                        }
//
//                        TradeProgress.CANCEL -> when (progress1) {
//                            TradeProgress.CONFIRM -> progress2 = TradeProgress.CONFIRM
//                            TradeProgress.CANCEL -> {
//                                cancelCountdown1?.cancel()
//                                cancelCountdown2?.cancel()
//                                player1.setCooldown(Material.BARRIER, 0)
//                                player2.setCooldown(Material.BARRIER, 0)
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }