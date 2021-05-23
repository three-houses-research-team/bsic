package edit.bsi

import com.github.thomasnield.rxkotlinfx.actionEvents
import javafx.geometry.Orientation
import tornadofx.*
import utils.hgrow

class EditTrigger : View() {
  val controller: EditBsiController by inject()
  val triggerMap = controller.selectedEvent.map { it.header.trigger }
  override val root = fieldset("Trigger", labelPosition = Orientation.VERTICAL) {
    hbox {
      field("Type") {
        hgrow()
        textfield {
          hgrow()
          triggerMap.subscribe { text = it.type.toString() }
        }
      }
      field("Param 1") {
        hgrow()
        textfield {
          hgrow()
          triggerMap.subscribe { text = it.param1.toString() }
        }
      }
      field("Param 2") {
        hgrow()
        textfield {
          hgrow()
          triggerMap.subscribe { text = it.param2.toString() }
        }
        button("Goto") {
          actionEvents()
            .flatMap { triggerMap.take(1) }
            .map { it.param2.toInt() }
            .subscribe(controller.jumpToEventId)
        }
      }
      field("Param 3") {
        hgrow()
        textfield {
          hgrow()
          triggerMap.subscribe { text = it.param3.toString() }
        }
      }
    }
  }
}
