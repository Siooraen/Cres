package me.siooraen.cres.module.guild

import me.siooraen.cres.module.guild.data.GuildData
import me.siooraen.cres.util.Utils.quit
import org.bukkit.entity.Player
import taboolib.module.ui.openMenu
import taboolib.module.ui.type.Chest

fun Player.openGuildUI(guild: GuildData) {
    openMenu<Chest> {
        map(
            "X  HHWWBB",
            "III      ",
            "III AAAA ",
            "III AAAA ",
            "III    SD"
            /*
            A是公告区域
            X是退出按钮
            I区域是公会旗帜
            D是捐献按钮
            S是商店按钮
            HH是主页面按钮
            WW是公会战信息按钮
            BB是公会银行按钮
             */
        )
        quit(this@openGuildUI)
    }
}

/*
公会银行：
    低等级银行
    高等级银行
公会战：
    领地信息
    公会战排名
成员列表
公告栏
公会等级
 */