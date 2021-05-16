package edit.bsi

import javafx.geometry.Orientation
import tornadofx.*

class EditTrigger : View() {
  val controller: EditBsiController by inject()
  val triggerMap = controller.selectedEvent.map { it.header.trigger }
  override val root = fieldset("Trigger", labelPosition = Orientation.VERTICAL) {
    hbox {
      field("Type") {
        textfield {
          triggerMap.subscribe { text = it.type.toString() }
        }
      }
      field("Param 1") {
        textfield {
          triggerMap.subscribe { text = it.param1.toString() }
        }
      }
      field("Param 2") {
        textfield {
          triggerMap.subscribe { text = it.param2.toString() }
        }
      }
      field("Param 3") {
        textfield {
          triggerMap.subscribe { text = it.param3.toString() }
        }
      }
    }
  }
}
