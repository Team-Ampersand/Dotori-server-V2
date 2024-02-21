package com.dotori.v2.util

object TestUtils {

    fun data() = TestDataUtil

    object TestDataUtil {
        fun auth() = AuthDataUtil
        fun member() = MemberDataUtil
    }
}