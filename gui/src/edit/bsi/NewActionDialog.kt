package edit.bsi

import bsi.blocks.BsiAction
import bsic.BsicNode
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.control.ButtonType
import javafx.scene.control.Dialog
import models.BaiSlot
import tornadofx.*
import utils.monospace
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.full.starProjectedType
import kotlin.reflect.full.valueParameters

class NewActionDialog : Dialog<BsicNode>() {
  val root = Form()
  val types = BsiAction::class.sealedSubclasses.sortedBy { clz ->
    BsiAction.idMap.entries.find { it.value == clz }?.key?.toInt() ?: 0
  }.asObservable()
  val selected = SimpleObjectProperty<KClass<out BsiAction>>()

  init {
    title = "Add BSI Action"
    with(root) {
      fieldset {
        field("Type") {
          combobox(selected, types) {
            cellFormat(FX.defaultScope) {
              text = it.simpleName
              monospace()
            }
          }
        }
      }
      pane {
        selected.onChange { klz ->
          klz ?: return@onChange
          replaceChildren {
            fieldset("Parameters") {
              for (param in klz.primaryConstructor!!.valueParameters) {
                field(param.name!!) {
                  monospace()

                  if (param.type.isSubtypeOf(UInt::class.starProjectedType)) {
                    textfield { }
                  } else if (param.type.isSubtypeOf(Boolean::class.starProjectedType)) {
                    combobox(SimpleBooleanProperty(), listOf(true, false))
                  } else if (param.type.isSubtypeOf(BaiSlot::class.starProjectedType)) {
                    textfield { }
                  }
                }
              }
            }
          }
          requestLayout()
        }
      }
    }


    dialogPane.content = root
    dialogPane.buttonTypes.addAll(ButtonType.OK, ButtonType.CANCEL)

    setResultConverter { if (it == ButtonType.OK) null else null }

  }
}
