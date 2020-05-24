package com.lucy.ocserver.objects

import com.lucy.ocserver.EUt

class PowerGen(var type: PowerGenType, var production: EUt)

enum class PowerGenType {
    Hydro, Wind, Solar
}