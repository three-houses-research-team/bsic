package bsi

import bsi.blocks.BsiAction
import bsi.blocks.BsiCondition
import bsi.blocks.bsiActionIds
import bsi.blocks.bsiConditionIds
import utils.*
import java.nio.ByteBuffer

data class EndingsBsiHeader(
  val magic: UInt,
  val unk1: UInt,
  val numEvents: UInt,
  val unk2: UInt,
  val unk3: UInt,
  val unk4: UInt,
  val unk5: UInt,
  val unk6: UInt,
  val startOfEvents: UInt,
  val len1: UInt,
  val len2: UInt,
  val len3: UInt,
  val len4: UInt,
  val len5: UInt
)

data class Trigger(val type: UInt, val param1: UInt, val param2: UInt, val param3: UInt)

data class BsiEventHeader(
  val eventNum: UInt,
  val unk1: UInt,
  val trigger: Trigger,
  val conditionsStartPointer: UInt,
  val pointer2: UInt,
  val actionsCount: UInt,
  val actionsStartPointer: UInt,
  val unk: UInt,
  val end: UInt
)

data class BsiEvent(
  val header: BsiEventHeader,
  val conditions: BsiTree.TList,
  val blocks2: ByteArray,
  val actions: List<BsiAction>
)

class BsiFile(buffer: ByteBuffer) {
  val header = buffer.readClass(EndingsBsiHeader::class)
  val offsets = header.numEvents.times { buffer.u4 }

  val events = header.numEvents.times { index ->
    with(buffer) {
      position(offsets[index])
      val header = buffer.readClass(BsiEventHeader::class)
      position(header.conditionsStartPointer)

      val conditions = buffer.slice(header.pointer2 - header.conditionsStartPointer).parseConditions()

      position(header.pointer2)
      if (header.actionsStartPointer - header.pointer2 != 16u) println("block2 length: ${header.actionsStartPointer - header.pointer2}")
      val blocks2 = buffer.array(header.actionsStartPointer - header.pointer2)

      position(header.actionsStartPointer)
      val actions = buffer.slice(header.end - header.actionsStartPointer).parseActions()
      if (actions.size.toUInt() != header.actionsCount) {
        println("Expected ${header.actionsCount} but read ${actions.size} for index $index.")
        println(actions)
      }
      BsiEvent(header, conditions, blocks2, actions)
    }
  }
}

fun ByteBuffer.readCondition(): BsiCondition {
  val magic = u4
  val findMagic = bsiConditionIds[magic]
  if (findMagic != null) return readClass(findMagic)

  val length = conditionUnknownBlockLengths[magic] ?: error("Don't know what a $magic is, found it at ${position()}")
  return BsiCondition.UnknownCondition(magic, length.times { u4 })
}

private fun ByteBuffer.parseConditions(): BsiTree.TList {
  val conditionBlocks = buildList {
    runCatching {
      while (hasRemaining()) this@buildList.add(readCondition())
    }.onFailure { e ->
      println("Failed in conditions: $this")
      dumpBuffer()
      error(e)
    }
  }
  return conditionBlocks.parseToTree()
}

private fun ByteBuffer.parseActions(): List<BsiAction> {
  return buildList {
    runCatching {
      while (hasRemaining()) this@buildList.add(readAction())
    }.onFailure { e ->
      println("Failed in actions: $this")
      dumpBuffer()
      error(e)
    }
  }
}

private fun ByteBuffer.dumpBuffer(readBack: Int = 15, readForward: Int = 25) = peek {
  val p = position()
  println("Read backwards: ")
  println(readBack.times { n ->
    runCatching { position(p - 4 * (readBack - n + 1)); u4 }.getOrNull()
  }.filterNotNull().joinToString())
  println("Read ahead: " + readForward.times { runCatching { u4 }.getOrNull() }.filterNotNull().joinToString())
}

fun ByteBuffer.readAction(): BsiAction {
  val magic = u4
  val findMagic = bsiActionIds[magic]
  if (findMagic != null) return readClass(findMagic)

  val length = actionUnknownBlockLengths[magic] ?: error("Don't know what a $magic is, found it at ${position()}")
  return BsiAction.UnknownAction(magic, length.times { u4 })
}
