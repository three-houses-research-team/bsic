package models

/**
 * 213 = 2nd BAI version slot 13
 */
data class BaiSlot(val raw: UInt) {
  val version = raw / 100u
  val slot = raw % 100u
}
