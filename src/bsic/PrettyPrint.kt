package bsic

import bsi.BsiEvent
import bsi.BsiFile
import bsi.blocks.BsiCondition
import convert.toBsic

fun Appendable.tab(amount: Int) = this.append("  ".repeat(amount))


fun Appendable.appendBsiFile(bsiFile: BsiFile) {
  appendLine("bsic {")
  for (event in bsiFile.events) appendBsiEvent(event)
  appendLine("}")
}

fun Appendable.appendBsiEvent(event: BsiEvent) {
  tab(1); appendLine("event {")

  tab(2); append("trigger = "); appendLine(event.header.trigger.toString())

  val conditions = event.conditions.simplify()
  if (!conditions.isEmpty()) {
    tab(2); append("conditions = "); appendLine(conditions.toBsic().toPretty())
  }

  for (action in event.actions) {
    tab(2)
    append("actions += ")
    appendLine(action.toString())
  }

  tab(1); appendLine("}")
}


private fun BsiCondition.toPretty() = buildString {
  append(this@toPretty)
}

private fun BsicNode.toPretty(): String = buildString {
  when (this@toPretty) {
    is BsicNode.Wraps -> {
      append(condition.toPretty())
    }
    is BsicNode.And -> append(items.joinToString(separator = " and ", prefix = "(", postfix = ")") { it.toPretty() })
    is BsicNode.Or -> append(items.joinToString(separator = " or ", prefix = "(", postfix = ")") { it.toPretty() })
  }
}
