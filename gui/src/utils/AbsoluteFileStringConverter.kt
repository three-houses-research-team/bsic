package utils

import javafx.util.StringConverter
import java.io.File

object AbsoluteFileStringConverter : StringConverter<File>() {
  override fun toString(obj: File?): String = obj?.absolutePath ?: ""
  override fun fromString(string: String?) = string?.let { File(it) }
}
