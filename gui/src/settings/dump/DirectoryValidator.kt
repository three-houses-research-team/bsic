package settings.dump

import tornadofx.ValidationContext
import tornadofx.ValidationMessage
import java.io.File

val DirectoryValidator: ValidationContext.(File?) -> ValidationMessage? = { file ->
  when {
    file == null -> error("This field is required")
    !file.exists() -> error("This directory does not exist")
    !file.isDirectory -> error("This is not a directory")
    else -> null
  }
}

