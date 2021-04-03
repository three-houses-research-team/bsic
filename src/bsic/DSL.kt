package bsic

import bsi.blocks.BsiAction
import bsi.blocks.BsiCondition
import bsi.BsiEvent
import bsi.BsiFile
import bsi.Trigger


class EventBuilder {
  var trigger: Trigger? = null
  var conditions: BsicNode = BsicNode.And(emptyList())
  val actions = mutableListOf<BsiAction>()

  fun build(): BsiEvent = TODO()
}

class BsicBuilder {
  private val events = mutableListOf<BsiEvent>()
  fun event(event: BsiEvent) = events.add(event)
  fun event(fn: EventBuilder.() -> Unit) = EventBuilder().also(fn).build()
  fun build(): BsiFile {
    TODO()
  }
}
fun bsic(fn: BsicBuilder.() -> Unit): BsiFile = BsicBuilder().also(fn).build()

val BsiCondition.bsic get() = BsicNode.Wraps(this)

