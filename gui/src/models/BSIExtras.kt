package models

import com.squareup.moshi.JsonAdapter
import utils.moshi

var bsiExtrasAdapter: JsonAdapter<BSIExtras> = moshi.adapter(BSIExtras::class.java)

data class BSIExtra(
  val comment: String?
)
data class BSIExtras(
  val events: Map<Int, BSIExtra>
)

//class BSIExtras(private val backing: JsonObject) {
//  private fun getBsi(num: Int) = backing.putObjIfMissing("$num")
//  fun getBsiComment(num: Int) = getBsi(num).getAsJsonPrimitive("comment").asString
//  fun putBsiComment(num: Int, string: String?) {
//    val obj = backing.asObject?.asObject("comments")?.asObject
//    when {
//      string == null && obj == null -> {}
//      string == null && obj != null -> obj.remove(num.toString())
//      string != null && obj == null -> backing.putObjIfMissing("comments").putObjIfMissing("$num")
//    }!!
//  }
//}
//
//
//private fun JsonObject.putObjIfMissing(member: String) = asObject(member) ?: run {
//  val ret = JsonObject()
//  add(member, ret)
//  ret
//}
