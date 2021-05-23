package edit.bsi

import bsi.blocks.BsiAction
import javafx.scene.layout.Priority
import models.BaiSlot
import models.FeCharacter
import tornadofx.*
import utils.monospace
import utils.nl2sp

class EditActions : View() {

  val controller: EditBsiController by inject()
  val actions = controller.selectedEvent.map { it.actions }

  override val root = hbox {
    listview<BsiAction> {
      hgrow = Priority.ALWAYS
      actions.subscribe { items.setAll(it) }

      lazyContextmenu {
        item("Add") {
          action {
            val result = NewActionDialog().showAndWait().orNull()
            println(result)
          }
        }
      }
      cellFormat { action ->
        monospace()
        tooltip(action.toString()) { }
        text = action.describe()
      }
    }

    button("+") {
      style { fontSize = 20.px }

    }
  }

  fun BaiSlot.describe(): String {
    val lookup = controller.scenarioController.bai.value?.data?.value?.get(this)?.character
    return "BAI ${this.raw} (${lookup})"
  }


  private fun BsiAction.describe(): String = when (this) {
    is BsiAction.ShowTextS -> {
      val textString = controller.textS.value?.data?.value?.strings?.getOrNull(id.toInt()-1)?.nl2sp() ?: "null"
      "Show TextS with ID=${id} - \"$textString\""
    }
    is BsiAction.BattleTalk -> {
      val mapString = controller.textV.value?.data?.value?.getOrNull(entryID.toInt() - 1)
      val description = mapString?.let {
        "${FeCharacter.values().getOrNull(mapString.who)}: \"${
          mapString.text.replace(
            "\n",
            " "
          )
        }\""
      } ?: "null"
      "Battle Talk - $description"
    }
    is BsiAction.RemoveFromMap -> {
      "Remove ${target.describe()} from the map"
    }
    is BsiAction.SetDeathString -> {
      val mapString = controller.textB.value?.data?.value?.getOrNull(this.textString.toInt() - 1)
      "When ${who.describe()} dies, talk: ${mapString?.character} - ${mapString?.text?.nl2sp()}"
    }
    else -> toString()
  }
}
