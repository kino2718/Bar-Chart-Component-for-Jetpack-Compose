package net.kino2718.barchart

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

@OptIn(ExperimentalTextApi::class)
@Composable
fun <T> BarChart(
    data: List<Datum<T>>,
    modifier: Modifier = Modifier,
    attributes: BarChartAttributes<T> = BarChartAttributes()
) where T : Number, T : Comparable<T> {
    BoxWithConstraints(modifier = modifier) {
        // 文字列のサイズの計測器を準備する
        val textMeasurer = rememberTextMeasurer()

        // データラベルを計測する
        val dataLabelLayoutResults =
            measureDataLabel(data = data, attributes = attributes, textMeasurer = textMeasurer)
        // データラベルの最大の高さを取得する
        val maxDataLabelHeight = dataLabelLayoutResults.maxOfOrNull {
            it.size.height
        } ?: 0

        // x,y軸の描画エリアを設定する
        val width = with(LocalDensity.current) { maxWidth.toPx() }
        val height = with(LocalDensity.current) { maxHeight.toPx() }
        val axisArea = Rect(
            left = 0f, top = 0f, right = width,
            bottom = height - maxDataLabelHeight // データラベル分、軸の位置をずらす
        )

        // データの描画エリアを設定する
        val plotArea = Rect(
            left = axisArea.left, top = axisArea.top, right = axisArea.right,
            bottom = axisArea.bottom
        )

        // データラベルの描画エリアを設定する
        val dataLabelArea = Rect(
            left = plotArea.left, top = plotArea.bottom, right = plotArea.right,
            bottom = plotArea.bottom + maxDataLabelHeight
        )

        // y軸の範囲を求める
        val yAxisAttributes = makeYAxisAttributes(data = data, attributes = attributes)

        Canvas(Modifier.fillMaxSize()) {
            // x,y軸を描画する
            drawAxis(area = axisArea, attributes = attributes)

            // データを描画する
            drawData(
                data = data,
                dataLabelLayoutResults = dataLabelLayoutResults,
                yAxisAttributes = yAxisAttributes,
                plotArea = plotArea,
                dataLabelArea = dataLabelArea,
                attributes = attributes
            )
        }
    }
}

// データラベルを計測する
@OptIn(ExperimentalTextApi::class)
private fun <T> measureDataLabel(
    data: List<Datum<T>>,
    attributes: BarChartAttributes<T>,
    textMeasurer: TextMeasurer
): List<TextLayoutResult> where T : Number, T : Comparable<T> {
    val textLayoutResults = mutableListOf<TextLayoutResult>()
    data.forEach {
        val label = it.label
        textLayoutResults.add(
            textMeasurer.measure(
                text = AnnotatedString(label),
                style = TextStyle(
                    color = attributes.dataLabelTextColor,
                    fontSize = attributes.dataLabelTextSize
                )
            )
        )
    }
    return textLayoutResults
}

// x,y軸を描画する
private fun <T> DrawScope.drawAxis(
    area: Rect,
    attributes: BarChartAttributes<T>
) where T : Number, T : Comparable<T> {
    drawLine(
        color = attributes.axisLineColor,
        start = Offset(area.left, area.top),
        end = Offset(area.left, area.bottom)
    )
    drawLine(
        color = attributes.axisLineColor,
        start = Offset(area.left, area.bottom),
        end = Offset(area.right, area.bottom)
    )
}

private fun Rect.union(other: Rect): Rect {
    return Rect(
        min(left, other.left),
        min(top, other.top),
        max(right, other.right),
        max(bottom, other.bottom)
    )
}

// データを描画する
@OptIn(ExperimentalTextApi::class)
private fun <T> DrawScope.drawData(
    data: List<Datum<T>>,
    dataLabelLayoutResults: List<TextLayoutResult>,
    yAxisAttributes: YAxisAttributes,
    plotArea: Rect,
    dataLabelArea: Rect,
    attributes: BarChartAttributes<T>
) where T : Number, T : Comparable<T> {
    val yMin = yAxisAttributes.minValue
    val yMax = yAxisAttributes.maxValue

    // データ値を座標に変換する係数を計算 y = ax + b
    val yRange = yMax - yMin
    val a = (plotArea.top - plotArea.bottom) / yRange
    val b = (plotArea.bottom * yMax - plotArea.top * yMin) / yRange
    val barInterval = attributes.barInterval.toPx()
    val barWidth = attributes.barWidth.toPx()
    val xStart = plotArea.left + (barInterval - barWidth) / 2f
    // clipの範囲をプロット領域とラベル領域の両方を含むようにする
    val unionArea = plotArea.union(dataLabelArea)
    clipRect(
        left = unionArea.left,
        top = unionArea.top,
        right = unionArea.right,
        bottom = unionArea.bottom
    ) {
        data.forEachIndexed { index, datum ->
            // 描画するbarの座標を計算
            val t = a * datum.value.toFloat()
            val y = t + b
            val barTop = min(y, b)
            val barHeight = abs(t)
            val barLeft = xStart + barInterval * index
            drawRect(
                color = attributes.barColor,
                topLeft = Offset(x = barLeft, y = barTop),
                size = Size(width = barWidth, height = barHeight)
            )

            // ラベルの座標を計算
            val labelLayoutResult = dataLabelLayoutResults[index]
            val labelTop = dataLabelArea.top
            val labelLeft = barLeft + barWidth / 2f - labelLayoutResult.size.width / 2f
            // ラベルを描画
            drawText(
                textLayoutResult = labelLayoutResult,
                topLeft = Offset(labelLeft, labelTop)
            )

        }
    }
}

@Preview(widthDp = 400, heightDp = 400)
@Composable
private fun BarChartPreview1() {
    BarChart(
        data = emptyList<Datum<Int>>(),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    )
}

@Preview(widthDp = 400, heightDp = 400)
@Composable
private fun BarChartPreview2() {
    BarChart(
        data = listOf(
            Datum(1, "d1"),
            Datum(2, "d2"),
            Datum(3, "d3"),
            Datum(4, "d4"),
            Datum(5, "d5"),
            Datum(6, "d6")
        ),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    )
}

@Preview(widthDp = 400, heightDp = 400)
@Composable
private fun BarChartPreview3() {
    BarChart(
        data = listOf(
            Datum(1, "d1"),
            Datum(-2, "d2"),
            Datum(3, "d3"),
            Datum(-4, "d4"),
            Datum(5, "d5"),
            Datum(-6, "d6")
        ),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    )
}