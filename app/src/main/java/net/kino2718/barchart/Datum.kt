package net.kino2718.barchart

data class Datum<T>(val value: T, val label: String) where T : Number, T : Comparable<T>