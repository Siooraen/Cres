package me.siooraen.cres.module.player

import taboolib.module.chat.colored

/**
 * @author Siooraen
 * @since 2022/9/25 8:21
 */
enum class CresPlayerRank(val display: String, val color: String, val icon: String, val desc: String) {

    DEFAULT("默认", "&r&7".colored(), "", "&7默认头衔"),
    ELITE(
        "精英",
        "&r&{#43a832}".colored(),
        "&f\uF123 &r".colored(),
        "&f\uF123\n&7在 store.cresworld.com 中\n购买以获得此头衔"
    ),
    MASTER(
        "大师",
        "&r&{#8897ea}".colored(),
        "&f\uF124 &r".colored(),
        "&f\uF124\n&7在 store.cresworld.com 中\n购买以获得此头衔"
    ),
    HERO(
        "英雄",
        "&r&{#a043a7}".colored(),
        "&f\uF125 &r".colored(),
        "&f\uF125\n&7在 store.cresworld.com 中\n购买以获得此头衔"
    ),
    MODERATOR("客服", "&r&{#00AAAA}".colored(), "&f\uF126 &r".colored(), "&f\uF126\n&7CresWorld 客服专属头衔"),
    CONTENT_TEAM("制作组", "&r&{#EE9A00}".colored(), "&f\uF127 &r".colored(), "&f\uF127\n&7CresWorld 制作组专属头衔"),
    ADMIN("管理员", "&r&{#CF1010}".colored(), "&f\uF128 &r".colored(), "&f\uF128\n&7CresWorld 管理员专属头衔"),
    OWNER("服主", "&r&{#CF1010}".colored(), "&f\uF129 &r".colored(), "&f\uF129\n&7CresWorld 服主专属头衔")
}