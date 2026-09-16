package me.siooraen.cres.util.weight

import java.util.*

object WeightUtil {

    fun <T> getWeightRandom(categories: List<WeightCategory<T>>): T? {
        var weightSum = 0
        categories.forEach { wc ->
            weightSum += wc.weight
        }
        if (weightSum <= 0) {
            return null
        }
        val n = Random().nextInt(weightSum)
        var m = 0
        categories.forEach { wc ->
            if (m <= n && n < m + wc.weight) {
                return wc.category
            }
            m += wc.weight
        }
        return null
    }

}