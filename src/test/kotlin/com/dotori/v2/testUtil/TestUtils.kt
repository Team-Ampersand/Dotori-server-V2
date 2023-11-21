package com.dotori.v2.testUtil

object TestUtils {

    fun data() = TestDataUtil

    object TestDataUtil {
        fun auth() = AuthDataUtil
        fun member() = MemberDataUtil
    }
}