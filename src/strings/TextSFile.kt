package strings

import utils.*
import java.io.File
import java.nio.ByteBuffer
import kotlin.math.min

class TextSFile(buffer: ByteBuffer) {
  val strings = with(buffer) {
    u4
    u4
    val part1Length = u4
    val textPointersLength = u4
    val count = u4
    u4; u4; u4 // probably padding
    val pointers = peek {
      val readActual = min(count.toInt(), textPointersLength.toInt() / 4)
      readActual.times { u4 }
    }
    offset(textPointersLength)
    val pos = position().toUInt()
    pointers.mapNotNull { textPointer -> // 8661 breaks this :/
      runCatching {
        position(pos + textPointer)
        nullString
      }.getOrNull()
    }
  }

  companion object {
    fun write(destination: File, strings: List<String>) =
      destination.byteBufferWriter {
        u4(1) // ???
        u4(1) // ???
        u4(0x20) // header size
        val pointerCount = strings.size + 1
        val paddedPointerLength = (pointerCount * 4).ceilTo0x10()
        u4(paddedPointerLength) // pointer size
        u4(strings.size)
        u4(0xeeeeeeeeu); u4(0xeeeeeeeeu); u4(0xeeeeeeeeu) // pad to 0x20
        val byteArrs = strings.map { it.encodeToByteArray().toList() + listOf(0) }
        var pointers = 0
        for (byteArr in byteArrs) {
          u4(pointers)
          pointers += byteArr.size
        }
        u4(pointers)

        for (a in pointerCount * 4 until paddedPointerLength) u1(0xddu)

        for (arr in byteArrs) {
          for (b in arr) put(b)
        }
      }
  }
}

