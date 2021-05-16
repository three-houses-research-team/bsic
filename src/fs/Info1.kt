package kt.fs

import utils.array
import utils.memmap
import utils.u8
import java.io.File

class Info1(val file: File) {
  val entriesCount = file.length() / 0x118
  val entries = file.memmap {
    (0 until entriesCount).map {
      Entry(
        u8,
        u8,
        u8 == 1uL,
        String(array(0x100)).removeSuffix("\u0000")
      )
    }
  }

  data class Entry(
    val decompressedSize: ULong,
    val compressedSize: ULong,
    val isCompressed: Boolean,
    val path: String
  )
}
