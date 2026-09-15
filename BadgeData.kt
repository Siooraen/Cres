package me.siooraen.cres.module.badge.data

/**
 * @author Siooraen
 * @since 2022/6/8 21:08
 */
class BadgeData(
    val id: String,
    var name: String = "NAME",
    var desc: MutableList<String> = mutableListOf("DESCRIPTION"),
    var obtaining: String = "OBTAINING",
    var customModelData: Int = 0,
) {

    fun isBadgeData(id: String): Boolean {
        return this.id == id
    }
}