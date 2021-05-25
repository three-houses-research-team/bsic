package utils.fs

import java.io.File

fun File.containsData0() = File(this, "DATA0.bin").exists() && File(this, "DATA1.bin").exists()
fun File.containsExtractedIndexNum() = File(this, "0").exists() && File(this, "1").exists()

fun File.validateBaseDir(): Boolean = containsData0() || containsExtractedIndexNum()
