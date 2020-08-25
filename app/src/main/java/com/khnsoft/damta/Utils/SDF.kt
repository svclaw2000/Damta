package com.khnsoft.damta.utils

import java.text.SimpleDateFormat
import java.util.*

class SDF {
    companion object {
        val dateDot = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)
        val dateBar = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
    }
}