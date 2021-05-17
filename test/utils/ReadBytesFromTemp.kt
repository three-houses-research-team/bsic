package utils

import java.io.File
import java.nio.file.Files

fun useTemp(fn: (File) -> Unit): ByteArray {
  val temp = Files.createTempFile(null, null).toFile()
  println("Using temporary file ${temp.absolutePath}")
  fn(temp)
  return temp.readBytes()
}
