package settings.dump

import tornadofx.ValidationContext
import tornadofx.ValidationMessage
import java.io.File

val PatchDumpValidator: ValidationContext.(String?) -> ValidationMessage? = { str ->
  val file = str?.let { File(it) }
  val validDirectory = DirectoryValidator(this, file)
  when {
    validDirectory != null -> validDirectory
    else ->
      (1..4)
        .firstOrNull { num ->
          val patchSubdirectory = File(file, "patch$num")
          !(patchSubdirectory.exists() && patchSubdirectory.isDirectory)
        }
        ?.let { num -> error("patch$num isn't in this directory") }
  }
}

