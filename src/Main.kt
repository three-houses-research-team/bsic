@file:Suppress("EXPERIMENTAL_API_USAGE", "EXPERIMENTAL_OVERRIDE")

import bsi.BsiFile
import bsic.appendBsiFile
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.Subcommand
import kotlinx.cli.required
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
}
