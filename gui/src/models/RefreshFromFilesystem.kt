package models

import io.reactivex.subjects.BehaviorSubject
import utils.memmap
import utils.next
import java.io.File
import java.nio.ByteBuffer

open class RefreshFromFilesystem<T>(val file: File, val reader: (ByteBuffer) -> T) {
  constructor(name: String, reader: (ByteBuffer) -> T) : this(File("mods", name), reader)

  val data = BehaviorSubject.create<T>()
  val refresh = BehaviorSubject.create<Unit>()

  init {
    refresh.startWith(Unit).map { file.memmap(fn = reader) }.subscribe(data)
  }

  fun refresh(writer: () -> Any) {
    writer()
    refresh.next()
  }
}

fun <Y, T : RefreshFromFilesystem<Y>> BehaviorSubject<T>.data() = flatMap { it.data }
fun <T : RefreshFromFilesystem<*>> BehaviorSubject<T>.file() = map { it.file }
