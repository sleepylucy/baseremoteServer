package com.lucy.ocserver

import kotlin.math.floor

object Utilities {
    fun convertRFtoEU(rf: Double): EUt {
        return floor(rf / 3.77).toInt()
    }
}