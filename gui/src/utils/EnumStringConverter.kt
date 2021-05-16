package utils;

import javafx.util.StringConverter

class EnumStringConverter< E : Enum<*>?> : StringConverter<E>() {
  override fun toString(obj: E?): String = obj?.name ?: "None"

  override fun fromString(string: String?): E {
    TODO("Not yet implemented")
  }
}


class UShortStringConverter : StringConverter<UShort>() {
  override fun toString(obj: UShort?): String = obj.toString()

  override fun fromString(string: String?): UShort {
    TODO("Not yet implemented")
  }
}

class UByteStringConverter : StringConverter<UByte>() {
  override fun toString(obj: UByte?): String = obj.toString()

  override fun fromString(string: String): UByte = string.toInt().toUByte()
}
