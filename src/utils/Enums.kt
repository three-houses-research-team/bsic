package utils

import java.util.*

inline fun <E: Enum<E>, R> enumMap(clz: Class<E>, crossinline mapper: (E) -> R) = EnumMap<E, R>(clz).apply {
  for (v in clz.enumConstants) {
    put(v, mapper(v))
  }
}
