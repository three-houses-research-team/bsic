package utils

import java.io.File
import java.io.RandomAccessFile
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

fun <T> File.memmap(size: Long = this.length(), start: Long = 0, fn: MappedByteBuffer.() -> T): T {
  if (!this.exists()) throw RuntimeException("${this.absolutePath} does not exist.")
  return RandomAccessFile(this, "r").use {
    val channel = it.channel.map(FileChannel.MapMode.READ_ONLY, start, size).apply {
      order(ByteOrder.LITTLE_ENDIAN)
    }
    fn(channel)
  }
}

fun File.memmap(size: Long = this.length(), start: Long = 0): MappedByteBuffer {
  if (!this.exists()) throw RuntimeException("${this.absolutePath} does not exist.")
  return RandomAccessFile(this, "r").use {
    it.channel.map(FileChannel.MapMode.READ_ONLY, start, size).apply {
      order(ByteOrder.LITTLE_ENDIAN)
    }
  }
}
