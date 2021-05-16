package edit.bsi

import bsi.blocks.BsiCondition
import bsic.BsicNode
import com.github.thomasnield.rxkotlinfx.actionEvents
import convert.toBsic
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeView
import models.BaiSlot
import tornadofx.*

class EditConditions : View() {
  val controller: EditBsiController by inject()
  val conditions = controller.selectedEvent.map { it.conditions }.map { it.simplify().toBsic() }

  override val root = treeview<BsicNode> {
    conditions.subscribe { bsic ->
      root = TreeItem(bsic)

      populate { parent ->
        when (val node = parent.value) {
          is BsicNode.And -> node.items
          is BsicNode.Or -> node.items
          is BsicNode.Wraps -> null
        }
      }
      this.expandAll()
      this.refresh()
    }

    lazyContextmenu {
      when (val selection = selectedValue) {
        is BsicNode.Wraps -> when (val condition = selection.condition) {
          is BsiCondition.HasOtherScriptRun -> item("Go to script ${condition.scriptIndex}") {
            actionEvents().map { condition.scriptIndex.toInt() }.subscribe(controller.jumpToEventId)
          }
        }
        is BsicNode.And -> item("Change to \"Any of\"") {
        }
        is BsicNode.Or -> item("Change to \"All of\"") {

        }
      }
    }

    cellFormat {
      tooltip(it.toString()) { }
      text = it.describe()
    }
  }

  fun BaiSlot.describe(): String {
//    val lookup = scope.bai[this]?.character
//    return "BAI ${this.raw} (${lookup})"
    return "BAI ${this.raw}"
  }

  private fun BsicNode.describe() = when (this) {
    is BsicNode.And -> "All of:"
    is BsicNode.Or -> "One of:"
    is BsicNode.Wraps -> when (val condition = condition) {
      is BsiCondition.HasOtherScriptRun -> "Script #${condition.scriptIndex} ${if (condition.didRun) "already completed" else "did not complete"}"
      is BsiCondition.Route -> "Route ${if (condition.isRoute) "is" else "isn't"} ${condition.route.niceString}"
      is BsiCondition.CheckDeathStatus -> "${condition.bai.describe()} ${if (condition.isDead) "is slain" else "has not been slain"}"
      is BsiCondition.IsCharacterIDSlain -> "${condition.character} ${if (condition.slain) "is slain" else "has not been slain"}"
      else -> condition.toString()
    }
  }
}

private fun <T> TreeView<T>.expandAll() {
  val item = this.root
  fun <T> expandItem(item: TreeItem<T>) {
    item.isExpanded = true
    for (child in item.children) expandItem(child)
  }
  expandItem(item)
}
