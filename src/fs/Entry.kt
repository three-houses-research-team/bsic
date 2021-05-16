package kt.fs

open class Entry(override val id: Int, override val type: EntryType = EntryType.BASE) : FeEntry {
  override fun toString(): String {
    return "kt.fs.Entry(id=$id, type=$type)"
  }

  companion object {
    operator fun get(entryId: Int) = Entry(entryId, EntryType.INFO1)
  }
}


operator fun FeEntry.plus(offset: Int) = Entry(id + offset, type)
operator fun FeEntry.minus(offset: Int) = Entry(id - offset, type)



