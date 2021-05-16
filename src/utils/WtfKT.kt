package utils

import kotlin.math.ceil

fun Int.ceilTo0x10() = ceil(this.toFloat() / 0x10).toInt() * 0x10
fun UInt.ceilTo0x10() = ceil(this.toFloat() / 0x10).toUInt() * 0x10u
