package models

import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import okio.Buffer
import okio.buffer
import okio.sink
import java.io.File

class BsiExtrasFs(bsiEntry: Int) : RefreshFromFilesystem<BSIExtras>(File("mods", "$bsiEntry.json"), { buf ->
  bsiExtrasAdapter.fromJson(
    JsonReader.of(Buffer().apply {
      write(buf)
    })
  )!!
}) {
  fun put(extras: BSIExtras) {
    refresh {
      val writer = JsonWriter.of(file.sink(false).buffer())
      bsiExtrasAdapter.toJson(writer, extras)
      writer.flush()
    }
  }
}
