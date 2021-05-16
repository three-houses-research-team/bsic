package strings

import models.FeCharacter
import utils.*
import java.io.File
import java.nio.ByteBuffer

val MapStringsParser: ByteBuffer.() -> List<MapString> = {
  val linesCount = int
  linesCount.times { u4 to u4 }.map { (start, _) ->
    position(start)
    MapString(nullString)
  }
}

class MapString(val original: String) {
  private val match = Regex(
    "^\\[(\\d\\d\\d\\d)](.*)＠(.+?)(#.*)?$",
    RegexOption.DOT_MATCHES_ALL
  ).find(original.dropLast(1))!!.destructured.toList()
  val who = match[0].toInt()
  val character = FeCharacter.values().getOrNull(who)
  val text = match[1]
  val voice = match[2].toIntOrNull()
  val portraitId = match[3].drop(1).toIntOrNull()
  override fun toString(): String {
    return "MapString(original=$original, who=$who, text='$text', voice=$voice, portraitId=$portraitId)"
  }
}

fun MapString.serialize(): ByteArray = buildString {
  append("[${(character?.ordinal ?: 9999).toString().padStart(4, '0')}]")
  append(text)
  if (voice != null) append("＠${voice.toString().padStart(6, '0')}")
  if (portraitId != null) append("#${portraitId.toString().padStart(2, '0')}")
}.encodeToByteArray()

object MapStrings {
  fun write(destination: File, strings: List<MapString>) = destination.byteBufferWriter {
    u4(strings.size)
    val serialized = strings.map(MapString::serialize)
    var position = strings.size * 4 * 2 + 4
    for (ser in serialized) {
      u4(position)
      u4(ser.size - 1) // wtf
      position += ser.size + 1
    }
    for (ser in serialized) {
      put(ser)
      u1(0)
    }
  }
}
