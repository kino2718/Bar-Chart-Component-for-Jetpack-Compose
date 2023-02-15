package net.kino2718.barchart

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class BarChartAttributes<T>(
    val yMin: T? = null,
    val yMax: T? = null,
    val barInterval: Dp = 64.dp, // distance between each bar
    val barWidth: Dp = 48.dp,
    val barColor: Color = Color(0xFF3F_51B5),
    val axisLineColor: Color = Color(0x6000_0000),
    val dataLabelTextColor: Color = Color(0xdd00_0000),
    val dataLabelTextSize: TextUnit = 12.sp
) where T : Number, T : Comparable<T>