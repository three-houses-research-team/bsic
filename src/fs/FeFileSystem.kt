package kt.fs

import fs.DATA0Format
import utils.array
import utils.memmap
import java.io.File
import java.nio.ByteBuffer
import java.nio.MappedByteBuffer

class FeFileSystem {

  val root = File("/data/fe3h")
  val base = File(root, "base")
  val patch = File(root, "patch")
  val dlcPath = File(patch, "dlc")
  val patches: Array<File> = patch.listFiles()!!

  val info0 = Info0(File(patch, "patch4/INFO0.bin"))
  val info1 = Info1(File(patch, "patch4/INFO1.bin"))

  val Data0 = DATA0Format(File(root, "raw/base_copy/DATA0.bin"))
  val Data1 = File(root, "raw/base_copy/DATA1.bin")

  operator fun get(entry: FeEntry): ByteBuffer = when (entry.type) {
    EntryType.BASE -> {
      val asLong = entry.id.toULong()
      val info0Entry = info0.entries.find { it.entryID == asLong }
      if (info0Entry == null) {
        Data1.memmap(start = Data0[entry.id].offset.toLong(), size = Data0[entry.id].decompressedSize.toLong())
      }
      else lookupRomFs(info0Entry.path).memmap()
    }
    EntryType.INFO1 -> lookupRomFs(info1.entries[entry.id].path).memmap()
    EntryType.DLC -> File(dlcPath, entry.id.toString()).memmap()
  }
  private fun lookupRomFs(path: String) = File(patch, path.removePrefix("rom:/"))

  fun bytes(entry: FeEntry): ByteArray {
    return when (entry.type) {
      EntryType.BASE -> {
        val asLong = entry.id.toULong()
        val info0Entry = info0.entries.find { it.entryID == asLong }
        if (info0Entry == null) {
          val data0entry = Data0[entry.id]
          val length = data0entry.decompressedSize.toLong()
          val map = Data1.memmap(data0entry.offset.toLong(), length)
          val arr = map.array(length.toInt())
          arr
        }
        else lookupRomFs(info0Entry.path).readBytes()
      }
      EntryType.INFO1 -> lookupRomFs(info1.entries[entry.id].path).readBytes()
      EntryType.DLC -> File(dlcPath, entry.id.toString()).readBytes()
    }
  }
}
