@file:Suppress("EXPERIMENTAL_API_USAGE", "EXPERIMENTAL_OVERRIDE")

import bsi.blocks.BsiCondition
import convert.toBsic
import bsic.BsicNode
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.Subcommand
import kotlinx.cli.required
import bsi.BsiFile
import utils.memmap
import java.io.File

fun main(args: Array<String>) {
  val parser = ArgParser("BSIC")
  parser.subcommands(Parse())
  parser.parse(args)
}


class Parse : Subcommand("parse", "Converts a binary BSI file into a BSIC file.") {
  val input by option(ArgType.String, shortName = "i", description = "Input file").required()
  val output by option(ArgType.String, shortName = "o", description = "Output file. Will output to stdout if empty.")
  override fun execute() {
    val file = File(input)
    val bsi = file.memmap(fn = ::BsiFile)

    if (output == null) {
      System.out.appendBsiFile(bsi)
    } else {
      file.printWriter().use { it.appendBsiFile(bsi) }
    }
  }

  fun Appendable.appendBsiFile(bsiFile: BsiFile) {
    fun tab(amount: Int) = print("  ".repeat(amount))
    appendLine("bsic {")
    for (event in bsiFile.events) {
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
    appendLine("}")
  }
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
