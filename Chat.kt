package me.siooraen.cres.module.chat

import me.siooraen.cres.module.Module
import me.siooraen.cres.module.ModuleState
import me.siooraen.cres.module.player.CresPlayer.getCresDisplayName
import me.siooraen.cres.module.player.CresPlayer.getCresPlayerData
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import taboolib.common.platform.function.getProxyPlayer
import taboolib.common5.util.replace
import taboolib.module.chat.Components
import taboolib.module.chat.colored

/**
 * @author Siooraen
 * @since 2023/7/21 21:05
 */
object Chat : Module {

    override val name: String
        get() = "聊天"

    override val id: String
        get() = "Chat"

    override val state: ModuleState
        get() = ModuleState.RUNNING

    fun Player.sendGlobalMsg(msg: String) {
        val level = this.getCresPlayerData().level

        val background = level.getBackgroundCount()
        val backgroundNegativeSpaces = level.getNegativeSpaces()

        val stats = "&f\uF50A\uF50B\uF821${level.toFive()}"
        val statsHover = listOf(
            this.getCresDisplayName(),
            "",
            "&7等级: §f${level}",
            "",
            "&8点击查看该玩家资料"
        )
        val statsCmd = "/profile ${this.name}"

        val displayName = this.getCresDisplayName()
        val displayNameHover = if (this.getCresPlayerData().nickName == null) {
            listOf(
                this.getCresPlayerData().rank.desc,
                "",
                "&8点击发送私信"
            )
        } else {
            listOf(
                this.getCresPlayerData().rank.desc,
                "",
                "&8此玩家真名为 ${this.name}",
                "&8点击发送私信"
            )
        }
        val displayNameSuggest = "/msg ${this.name}"

        Bukkit.getOnlinePlayers().forEach {
            it?.let {
                val data = it.getCresPlayerData()
                if (data.ignored.contains(this.uniqueId)) {
                    return@forEach
                }
                if (data.friends.contains(this.uniqueId)) {
                    val newBackground = background.replace(
                        Pair("\uF130", "\uF133"),
                        Pair("\uF131", "\uF134"),
                        Pair("\uF132", "\uF135")
                    )
                    Components.text("\uF130${newBackground}\uF803\uF132${backgroundNegativeSpaces}${stats}".colored())
                        .hoverText(statsHover.colored())
                        .clickSuggestCommand(statsCmd)
                        .append(" ")
                        .append("${displayName}:")
                        .hoverText(displayNameHover.colored())
                        .clickSuggestCommand(displayNameSuggest)
                        .append(" &f$msg".colored())
                        .sendTo(getProxyPlayer(it.uniqueId)!!)
                } else {
                    Components.text("\uF130${background}\uF801\uF132${backgroundNegativeSpaces}${stats}".colored())
                        .hoverText(statsHover.colored())
                        .clickSuggestCommand(statsCmd)
                        .append(" ")
                        .append("${displayName}:")
                        .hoverText(displayNameHover.colored())
                        .clickSuggestCommand(displayNameSuggest)
                        .append(" &f$msg".colored())
                        .sendTo(getProxyPlayer(it.uniqueId)!!)
                }
            }
        }
    }

    fun Int.toFive(): String {
        return this.toString().replace(
            Pair("0", "\uF500"),
            Pair("1", "\uF501"),
            Pair("2", "\uF502"),
            Pair("3", "\uF503"),
            Pair("4", "\uF504"),
            Pair("5", "\uF505"),
            Pair("6", "\uF506"),
            Pair("7", "\uF507"),
            Pair("8", "\uF508"),
            Pair("9", "\uF509")
        )
    }

    fun Int.getBackgroundCount(): String {
        return "\uF801\uF131".repeat(4 + this.toString().length * 2)
    }

    fun Int.getNegativeSpaces(): String {
        return  "\uF809" + "\uF803".repeat(this.toString().length * 2)
    }
}