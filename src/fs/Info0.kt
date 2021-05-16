package kt.fs

import utils.*
import java.io.File

class Info0(val file: File) {
  val entriesCount = file.length() / 0x120
  val entries = file.memmap {
    (0 until entriesCount).map {
      Info0Entry(
        u8,
        u8,
        u8,
        u8 == 1uL,
        String(array(0x100)).replace(0.toChar()+"", "")
      )
    }
  }

  data class Info0Entry(
    val entryID: ULong,
    val decompressedSize: ULong,
    val compressedSize: ULong,
    val isCompressed: Boolean,
    val path: String
  )
}
