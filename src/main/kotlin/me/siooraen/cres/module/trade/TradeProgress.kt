package me.siooraen.cres.module.trade

/**
 * @author Siooraen
 * @since 2023/8/7 19:56
 */
enum class TradeProgress(
    val button: String,
    val edge: String,
    val display: String
) {

    CONFIRM("1", "CONFIRM", "&a确认此交易"),
    CANCEL("2", "CANCEL", "&4取消此交易"),
}