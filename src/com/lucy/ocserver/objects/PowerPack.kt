package com.lucy.ocserver.objects

import com.lucy.ocserver.EU
import com.lucy.ocserver.EUt

class PowerPack(var capacity: EU, private var stored: EU, var maintenance: Boolean) {
    private var changeHist: Array<EUt> = arrayOf(0, 0, 0, 0, 0)
    private var oldStored: EU = 0
    private var changeHistIterator = 0

    fun set(stored: EU) {
        val change = (stored - oldStored).toInt()
        changeHist[changeHistIterator++] = change
        changeHistIterator++
        if (changeHistIterator > 4) changeHistIterator = 0

        oldStored = stored
        this.stored = stored
    }

    private fun getChange(): EUt {
        var sum = 0
        for (change in changeHist) {
            sum += change
        }
        sum /= changeHist.size
        sum /= 20
        return sum
    }

    fun getJsonObject(): PowerPackJson {
        return PowerPackJson(capacity, stored, maintenance, getChange())
    }
}

class PowerPackJson(var capacity: EU, var stored: EU, var maintenance: Boolean, var change: EUt)