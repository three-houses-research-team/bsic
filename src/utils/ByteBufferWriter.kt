package utils

import java.io.File
import java.io.RandomAccessFile
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel


fun File.byteBufferWriter(fn: ByteBuffer.() -> Unit) {
  RandomAccessFile(this, "rw").use {
    val channel = it.channel.map(FileChannel.MapMode.READ_WRITE, 0, 10 * 1024 * 1024).apply {
      order(ByteOrder.LITTLE_ENDIAN)
    }
    fn(channel)
    it.channel.truncate(channel.position().toLong())
  }
}
