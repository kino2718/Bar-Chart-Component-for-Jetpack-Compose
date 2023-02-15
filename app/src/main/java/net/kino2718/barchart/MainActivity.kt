package net.kino2718.barchart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.kino2718.barchart.ui.theme.BarChartTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BarChartTheme {
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
        }
    }
}