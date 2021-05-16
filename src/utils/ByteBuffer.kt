
package utils

import java.nio.ByteBuffer
import java.nio.ByteOrder

fun ByteBuffer.assert1(b: UByte) = get().also { if (it != b.toByte()) error("assert: Expected $b but got $it") }
fun ByteBuffer.assert2(i: UShort) =
  short.toUShort().also { if (it != i) error("assert: Expected ${i.toHexString()} but got ${it.toHexString()}") }

fun ByteBuffer.assert4(i: UInt) =
  int.toUInt().also { if (it != i) error("assert: Expected ${i.toHexString()} but got ${it.toHexString()}") }

val ByteBuffer.u8 get() = long.toULong()
val ByteBuffer.u4 get() = int.toUInt()
inline val ByteBuffer.u4n get() = u4n()
fun ByteBuffer.u4n(nullValue: UInt = 0xffffffffu) = u4.let { if (it == nullValue) null else it }

fun ByteBuffer.u4(i: UInt): ByteBuffer = this.putInt(i.toInt())
fun ByteBuffer.u4(i: Int): ByteBuffer = this.putInt(i)
val ByteBuffer.u2 get() = short.toUShort()
fun ByteBuffer.u2(i: Short): ByteBuffer = this.putShort(i)
fun ByteBuffer.u2(i: UShort): ByteBuffer = this.putShort(i.toShort())
val ByteBuffer.u1 get() = get().toUByte()
fun ByteBuffer.u1(i: Byte) = this.put(i)!!
fun ByteBuffer.u1(i: UByte) = this.put(i.toByte())!!

fun ByteBuffer.u4s(i: Int) = (0 until i).map { u4 }
fun ByteBuffer.u4s(i: UInt) = (0u until i).map { u4 }

fun ByteBuffer.offset(amount: Int) {
  this.position(position() + amount)
}

fun ByteBuffer.offset(amount: UInt) {
  this.position(position() + amount.toInt())
}

fun ByteBuffer.position(amount: UInt) {
  this.position(amount.toInt())
}

fun ByteBuffer.array(size: Int) = ByteArray(size).also { get(it) }
fun ByteBuffer.array(size: UInt) = array(size.toInt())
fun ByteBuffer.slice(length: UInt): ByteBuffer =
  slice().order(ByteOrder.LITTLE_ENDIAN).limit(length.toInt()) as ByteBuffer

fun ByteBuffer.alignToNext(interval: UInt) {
  this.offset(interval - (position().toUInt().rem(interval)))
}

fun <T> ByteBuffer.peek(f: () -> T): T {
  val pos = position()
  val thing = f()
  position(pos)
  return thing
}

val ByteBuffer.nullString
  get(): String {
    val start = position()

    var current: UByte
    do {
      current = u1
    } while (current != 0x0.toUByte())
    val end = position()
    position(start) // rewind
    val len = end - start
    val array = array(len-1) // remove null terminal
    return String(array)
  }
