package kt.fs

interface FeEntry {
  val id: Int
  val type: EntryType
}

enum class EntryType {
  BASE, INFO1, DLC
}
