package utils

fun ByteArray.toHexString() = asUByteArray().joinToString("") { it.toString(16).padStart(2, '0') }

fun UShort.toHexString() = "0x" + toString(16).padStart(4, '0')
fun UInt.toHexString() = "0x" + toString(16).padStart(8, '0')
fun ULong.toHexString() = "0x" + toString(16).padStart(16, '0')

fun UByte.toHexString() = "0x" + toString(16).padStart(2, '0')
operator fun <T> Array<T>.get(uInt: UInt): T = get(uInt.toInt())
operator fun <T> List<T>.get(uInt: UInt): T = get(uInt.toInt())
