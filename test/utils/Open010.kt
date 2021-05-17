package utils

import java.io.File

object Open010 {
  fun compare(f1: File, f2: File) = "-compare:${f1.absolutePath}::${f2.absolutePath}"
}
