package me.siooraen.cres.util

import taboolib.module.chat.colored

/**
 * @author Siooraen
 * @since 2022/11/1 10:12
 */
fun createProgressBarWithText(max: Int, now: Int, emptyC: Char, completeC: Char, frameC: Char, text: String): String {
    val a = (now.toDouble() / max.toDouble()) * 10
    val b = text.codePointCount(0, text.length)
    val c = 10 - b
    val c1: Int
    val c2: Int
    if (c % 2 == 0) {
        c1 = c / 2
        c2 = c / 2
    } else {
        c1 = c / 2
        c2 = c / 2 + 1
    }
    val s1 = (1..c1).joinToString { "|" }
    val s2 = (1..c2).joinToString { "|" }
    val progress = "&${completeC}${s1}${text}${s2}".replace(",", "").replace(" ", "")
    val coloredProgress = StringBuffer(progress).insert((a + 2.0).toInt(), "&$emptyC")
    return "&${frameC}[${coloredProgress}&${frameC}]".colored()
}