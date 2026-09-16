package me.siooraen.cres.module.profile

/**
 * @author Siooraen
 * @since 2022/4/30 10:38
 */
enum class ProfileStatusType(
    val display: String,
    val customModelData: Int,
) {

    ONLINE("&a在线", 2),
    OFFLINE("&c离线", 3),
}