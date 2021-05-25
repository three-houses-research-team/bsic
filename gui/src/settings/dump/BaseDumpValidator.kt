package settings.dump

import tornadofx.ValidationContext
import tornadofx.ValidationMessage
import validateBaseDir
import java.io.File

val BaseDumpValidator: ValidationContext.(String?) -> ValidationMessage? = { str ->
  val file = str?.let { File(it) }
  when {
    file == null || str.isNullOrBlank() -> error("This field is required")
    !file.exists() -> error("This directory does not exist")
    !file.isDirectory -> error("This is not a directory")
    !file.validateBaseDir() -> error("This doesn't contain DATA0.bin or {0, 1, 2, ...}")
    else -> null
  }
}

