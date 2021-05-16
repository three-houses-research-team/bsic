package fs

import utils.u8
import java.io.File
import java.nio.ByteOrder
import java.nio.channels.FileChannel
import java.nio.file.Files
import java.nio.file.StandardOpenOption
import java.util.*

class DATA0Format(private val file: File) : AutoCloseable, Iterable<DATA0Format.Entry> {
  /* mmap DATA0 */
  val channel = Files.newByteChannel(file.toPath(), EnumSet.of(StandardOpenOption.WRITE, StandardOpenOption.READ)) as FileChannel
  val map = channel.map(FileChannel.MapMode.READ_WRITE, 0, channel.size()).apply { order(ByteOrder.LITTLE_ENDIAN) }

  val size = (channel.size() / 0x20).toInt()

  data class Entry(val offset: ULong, val decompressedSize: ULong, val compressedSize: ULong, val isCompressed: Boolean) {
    val size = if (isCompressed) compressedSize else decompressedSize
  }

  operator fun get(index: Int): Entry = with(map) {
    position(index * 0x20)
    return Entry(u8, u8, u8, u8 == 1uL)
  }

  override operator fun iterator() = iterator<Entry> {
    with(map) {
      position(0)
      (0 until size).forEach { i -> yield(this@DATA0Format[i]) }
    }
  }

  override fun close() {
    channel.close()
  }
}
