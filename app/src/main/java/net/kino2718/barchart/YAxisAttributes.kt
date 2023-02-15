package net.kino2718.barchart

import kotlin.math.max
import kotlin.math.min

data class YAxisAttributes(val minValue: Float, val maxValue: Float, val gridList: List<Float>)

fun <T> makeYAxisAttributes(
    data: List<Datum<T>> = emptyList(),
    attributes: BarChartAttributes<T> = BarChartAttributes(),
    gridCount: Float = 7f // 目標とするgrid線の数。実際にはこれより小さくなる
): YAxisAttributes where T : Number, T : Comparable<T> {
    // チャートのy軸の範囲を決める
    val yMinOrNull = attributes.yMin ?: run { data.minOfOrNull { it.value } }
    val yMin = yMinOrNull?.toFloat() ?: 0f
    val yMaxOrNull = attributes.yMax ?: run { data.maxOfOrNull { it.value } }
    val yMax = yMaxOrNull?.toFloat() ?: 0f

    // 棒グラフのためy軸の範囲には必ず0を含むようにする
    val minValue = min(0f, yMin)
    val maxValue = max(0f, yMax)

    val range = maxValue - minValue
    // 範囲が0だとグラフは書けないので(0,1)の範囲に変更する
    if (range == 0f) return YAxisAttributes(0f, 1f, emptyList())

    // grid間隔を計算する。1, 2, 5, 10, 20, 50, 100, ... 等から選ぶ。
    // grid間隔はgrid線がgridCount個程度になるように調整する
    var gridIntervalOrder = 1f
    // まずはgrid線がgridCount個以上になるようにする
    while (true) {
        if (gridCount <= range / gridIntervalOrder + 1) break
        // grid幅の桁を一つ下げる
        gridIntervalOrder /= 10f
    }
    // grid間隔を少しずつ大きくしてgrid線がgridCount個以下になるように調整する
    val factors = listOf(1f, 2f, 5f)
    val gridInterval: Float
    loop@ while (true) {
        for (factor in factors) {
            val interval = factor * gridIntervalOrder
            if (range / interval + 1 <= gridCount) {
                gridInterval = interval
                break@loop
            }
        }
        // grid幅の桁を一つ上げる
        gridIntervalOrder *= 10f
    }

    // grid位置を求め配列に追加していく
    val gridList = mutableListOf<Float>()
    val start = (minValue / gridInterval).toInt()
    val end = (maxValue / gridInterval).toInt() + 1
    for (i in start until end) {
        gridList.add(i * gridInterval)
    }
    return YAxisAttributes(minValue, maxValue, gridList)
}