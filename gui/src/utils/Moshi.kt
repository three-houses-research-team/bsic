package utils

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.File

val moshi = Moshi.Builder()
  .addLast(KotlinJsonAdapterFactory())
  .add(File::class.java, object : JsonAdapter<File>() {
    override fun fromJson(reader: JsonReader) = File(reader.nextString())

    override fun toJson(writer: JsonWriter, value: File?) {
      writer.value(value?.absolutePath)
    }
  })
  .build()
