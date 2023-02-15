package net.kino2718.barchart

import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Test

class YAxisAttributesKtTest {

    @Test
    fun makeYAxisAttributes_Test1() {
        val data = listOf(Datum(0f, "d1"), Datum(10f, "d2"))
        val yAxisAttributes = makeYAxisAttributes(data = data)
        println("yAxisAttributes = $yAxisAttributes")
        assertEquals(0f, yAxisAttributes.minValue)
        assertEquals(10f, yAxisAttributes.maxValue)
        assertArrayEquals(arrayOf(0f, 2f, 4f, 6f, 8f, 10f), yAxisAttributes.gridList.toTypedArray())
    }

    @Test
    fun makeYAxisAttributes_Test2() {
        val yAxisAttributes =
            makeYAxisAttributes(attributes = BarChartAttributes(yMin = 0f, yMax = 12f))
        println("yAxisAttributes = $yAxisAttributes")
        assertEquals(0f, yAxisAttributes.minValue)
        assertEquals(12f, yAxisAttributes.maxValue)
        assertArrayEquals(
            arrayOf(0f, 2f, 4f, 6f, 8f, 10f, 12f),
            yAxisAttributes.gridList.toTypedArray()
        )
    }

    @Test
    fun makeYAxisAttributes_Test3() {
        val data = listOf(Datum(0f, "d1"), Datum(14f, "d2"))
        val yAxisAttributes = makeYAxisAttributes(data = data)
        println("yAxisAttributes = $yAxisAttributes")
        assertEquals(0f, yAxisAttributes.minValue)
        assertEquals(14f, yAxisAttributes.maxValue)
        assertArrayEquals(arrayOf(0f, 5f, 10f), yAxisAttributes.gridList.toTypedArray())
    }

    @Test
    fun makeYAxisAttributes_Test4() {
        val data = listOf(Datum(-1f, "d1"), Datum(9f, "d2"))
        val yAxisAttributes = makeYAxisAttributes(data = data)
        println("yAxisAttributes = $yAxisAttributes")
        assertEquals(-1f, yAxisAttributes.minValue)
        assertEquals(9f, yAxisAttributes.maxValue)
        assertArrayEquals(arrayOf(0f, 2f, 4f, 6f, 8f), yAxisAttributes.gridList.toTypedArray())
    }

    @Test
    fun makeYAxisAttributes_Test5() {
        val data = listOf(Datum(1f, "d1"), Datum(9f, "d2"))
        val yAxisAttributes = makeYAxisAttributes(data = data)
        println("yAxisAttributes = $yAxisAttributes")
        assertEquals(0f, yAxisAttributes.minValue)
        assertEquals(9f, yAxisAttributes.maxValue)
        assertArrayEquals(arrayOf(0f, 2f, 4f, 6f, 8f), yAxisAttributes.gridList.toTypedArray())
    }

    @Test
    fun makeYAxisAttributes_Test6() {
        val data = listOf(Datum(-31f, "d1"), Datum(47f, "d2"))
        val yAxisAttributes = makeYAxisAttributes(data = data)
        println("yAxisAttributes = $yAxisAttributes")
        assertEquals(-31f, yAxisAttributes.minValue)
        assertEquals(47f, yAxisAttributes.maxValue)
        assertArrayEquals(arrayOf(-20f, 0f, 20f, 40f), yAxisAttributes.gridList.toTypedArray())
    }

    @Test
    fun makeYAxisAttributes_Test7() {
        val data = listOf(Datum(-218, "d1"), Datum(-121, "d2"))
        val yAxisAttributes = makeYAxisAttributes(data = data)
        println("yAxisAttributes = $yAxisAttributes")
        assertEquals(-218f, yAxisAttributes.minValue)
        assertEquals(0f, yAxisAttributes.maxValue)
        assertArrayEquals(
            arrayOf(-200f, -150f, -100f, -50f, 0f),
            yAxisAttributes.gridList.toTypedArray()
        )
    }

    @Test
    fun makeYAxisAttributes_Test8() {
        val data = listOf(Datum(-0.0235f, "d1"), Datum(0.0958f, "d2"))
        val yAxisAttributes = makeYAxisAttributes(data = data)
        println("yAxisAttributes = $yAxisAttributes")
        assertEquals(-0.0235f, yAxisAttributes.minValue)
        assertEquals(0.0958f, yAxisAttributes.maxValue)
        assertArrayEquals(
            arrayOf(-0.02f, 0f, 0.02f, 0.04f, 0.06f, 0.08f),
            yAxisAttributes.gridList.toTypedArray()
        )
    }

    @Test
    fun makeYAxisAttributes_Test9() {
        val yAxisAttributes =
            makeYAxisAttributes(attributes = BarChartAttributes(yMin = 1f, yMax = -1f))
        println("yAxisAttributes = $yAxisAttributes")
        assertEquals(0f, yAxisAttributes.minValue)
        assertEquals(1f, yAxisAttributes.maxValue)
        assertArrayEquals(
            emptyArray(),
            yAxisAttributes.gridList.toTypedArray()
        )
    }

    @Test
    fun makeYAxisAttributes_Test10() {
        val data = listOf(Datum(0f, "d1"), Datum(0f, "d2"))
        val yAxisAttributes = makeYAxisAttributes(data = data)
        println("yAxisAttributes = $yAxisAttributes")
        assertEquals(0f, yAxisAttributes.minValue)
        assertEquals(1f, yAxisAttributes.maxValue)
        assertArrayEquals(
            emptyArray(),
            yAxisAttributes.gridList.toTypedArray()
        )
    }
}