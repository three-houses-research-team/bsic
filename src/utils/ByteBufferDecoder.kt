@file:Suppress("UNCHECKED_CAST")

package utils

import java.nio.ByteBuffer
import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.KType
import kotlin.reflect.full.*
import kotlin.reflect.jvm.jvmErasure

annotation class StringOfLength(val length: Int)
annotation class BooleanOfLength(val length: Int)
annotation class NullString
annotation class OfLength(val length: Int)
annotation class OfType(val clz: KClass<*>)

interface HasID {
  val id: UInt
}

fun <T : Any> ByteBuffer.readClass(kls: KClass<T>, length: Int? = null): T {
  return try {
    val start = position()
    val constructor = kls.primaryConstructor!!
    val values = constructor.parameters.map { parameter ->
      inflateThingFromParameter(parameter.type, parameter = parameter)
    }.toTypedArray()
    val read = position() - start
    if (length != null && read != length) error("Didn't read $length bytes.")
    constructor.call(*values)
  } catch (e: Throwable) {
    println("Failed to inflate ${kls.simpleName}.")
    e.printStackTrace()
    throw e
  }
}


@OptIn(ExperimentalStdlibApi::class)
private fun ByteBuffer.inflateThingFromParameter(type: KType, parameter: KParameter? = null): Any? {
  return when {
    type == UInt::class.starProjectedType -> int.toUInt()
    type == Int::class.starProjectedType -> int
    type == Int::class.starProjectedType.withNullability(true) -> int.let { if (it.toUInt() == 0xffffffffu) null else it }
    type == UShort::class.starProjectedType -> u2
    type == Short::class.starProjectedType -> u2
    type == Short::class.starProjectedType.withNullability(true) -> u2.let { if (it == 0xffffu.toUShort()) null else it }
    type == UByte::class.starProjectedType -> u1
    type == Byte::class.starProjectedType -> u1
    type == Byte::class.starProjectedType.withNullability(true) -> u1.let { if (it == 0xffu.toUByte()) null else it }
    type == ULong::class.starProjectedType -> u8
    type == Float::class.starProjectedType -> float
    parameter != null && parameter.hasAnnotation<StringOfLength>() -> {
      val annotation = parameter.findAnnotation<StringOfLength>()!!.length
      String(array(annotation)).replace("\u0000", "")
    }
    parameter != null && parameter.hasAnnotation<BooleanOfLength>() -> {
      val annotation = parameter.findAnnotation<BooleanOfLength>()!!.length
      val array = array(annotation)
      array[0] == 1.toByte()
    }
    else -> {
      val kclass = type.asKClass()
      val supertypes = kclass?.supertypes
      when {
        type.classifier == List::class -> {
          val elementType = (parameter?.findAnnotation<OfType>()?.clz
            ?: error("Unknown list type ${type.jvmErasure.simpleName}")).starProjectedType.withNullability(true)
          val constructor = type.jvmErasure.primaryConstructor?.call() as? MutableList<Any?>
            ?: mutableListOf()
          constructor.apply {
            val length = parameter.findAnnotation<OfLength>()?.length
            if (length != null) {
              (0 until length).map { add(inflateThingFromParameter(elementType)) }
            } else {
              while ((position() - 0x10) % 0x30 != 0) {
                try {
                  val element = inflateThingFromParameter(elementType)
                  if (element != null) add(element)
                } catch (e: Exception) {
                } // TODO remove this
              }
            }
          }
        }
        supertypes?.get(0)?.asKClass() == Enum::class -> {
          val enumType = Class.forName(type.jvmErasure.qualifiedName) as Class<Enum<*>>
          val length = parameter?.findAnnotation<OfLength>()?.length
            ?: enumType.getAnnotation(OfLength::class.java)?.length ?: 4
          val ord = when (length) {
            1 -> u1.toInt()
            2 -> u2.toInt()
            4 -> u4.toInt()
            8 -> u8.toInt()
            else -> error("dunno what this is $length")
          }
          when {
            length == 1 && ord == 0xff -> null
            length == 2 && ord == 0xffff -> null
            ord.toUInt() == 0xffffffffu && type.isMarkedNullable -> null
            ord == -1 -> null
            else -> try {
              if (supertypes.contains(HasID::class.starProjectedType)) enumType.enumConstants.find { (it as HasID).id == ord.toUInt() }
              else enumType.enumConstants[ord]
            } catch (t: Throwable) {
              error("Attempted to read ${ord.toUInt().toHexString()} from ${enumType.simpleName}")
            }
          }
        }
        else -> readClass(type.jvmErasure)
      }
    }
  }
}

fun KType.asKClass() = this.classifier as? KClass<*>
