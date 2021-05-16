package utils

import writers.TextSWriter
import java.nio.ByteBuffer
import java.nio.ByteOrder

private object ResourceRead

fun resourceBytes(path: String): ByteArray {
  return ResourceRead::class.java.getResourceAsStream(path)!!.readAllBytes()
}

fun ByteArray.leBuffer()  = ByteBuffer.wrap(this).order(ByteOrder.LITTLE_ENDIAN)
