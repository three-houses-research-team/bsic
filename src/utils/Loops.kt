package utils

fun <R> Int.times(fn: (Int) -> R) = (0 until this).map(fn)
fun <R> Long.times(fn: (Long) -> R) = (0 until this).map(fn)
fun <R> UInt.times(fn: (UInt) -> R) = (0u until this).map(fn)
fun <R> UShort.times(fn: (UInt) -> R) = (0u.toUShort() until this).map(fn)
fun <R> UByte.times(fn: (Int) -> R) = (0 until this.toInt()).map(fn)
