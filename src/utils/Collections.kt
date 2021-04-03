package utils

fun <T> Collection<T>.indexOfOrNull(predicate: (T) -> Boolean) = run {
  val index = indexOfFirst(predicate)
  if (index == -1) null
  else index
}

fun <T> Collection<T>.indexOfOrNull(thing: T) = run {
  val index = indexOf(thing)
  if (index == -1) null
  else index
}

@Suppress("UNCHECKED_CAST")
fun <K : Any, V : Any?> Map<K?, V>.removeNullKey() = (this.filterKeys { it != null }) as Map<K, V>
