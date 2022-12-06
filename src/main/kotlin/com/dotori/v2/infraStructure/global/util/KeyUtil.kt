package com.dotori.v2.infraStructure.global.util

import org.springframework.stereotype.Component
import java.util.*

@Component
class KeyUtil {
    fun keyIssuance(): String {
        val random = Random()
        val buffer = StringBuffer()
        var num = 0
        while (buffer.length < 6) {
            num = random.nextInt(10)
            buffer.append(num)
        }
        return buffer.toString()
    }
}