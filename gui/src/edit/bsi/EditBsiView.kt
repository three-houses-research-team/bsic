package edit.bsi

import bsi.BsiEvent
import com.github.thomasnield.rxkotlinfx.toObservable
import edit.ScenarioController
import edit.data
import tornadofx.*
import java.util.*

class EditBsiView : View() {
  val editActions = find<EditActions>()
  val editConditions = find<EditConditions>()
  val filteredBsiEvents = find<FilteredBsiEvents>()
  val editTrigger = find<EditTrigger>()
  val controller: EditBsiController by inject()

  override val root = borderpane {
    left = filteredBsiEvents.root
    center = form {
      add(editTrigger)
      fieldset("Conditions") {
        add(editConditions)
      }
      fieldset("Actions") {
        add(editActions)
      }
    }
  }
}

class FilteredBsiEvents : View() {
  val scenarioController: ScenarioController by inject()
  val controller: EditBsiController by inject()

  override val root = listview<BsiEvent> {
    scenarioController.bsi.data().subscribe {
      items.setAll(it.events)
    }
    selectionModel.selectedItemProperty().toObservable().subscribe(controller.selectedEvent)

    cellFormat {
      text = it.header.eventNum.toString()
    }
  }
}

fun <T> Optional<T>.orNull() = if (isEmpty) null else get()
