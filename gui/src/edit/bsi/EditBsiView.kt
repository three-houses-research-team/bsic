package edit.bsi

import bsi.BsiEvent
import com.github.thomasnield.rxkotlinfx.toObservable
import ScenarioController
import models.data
import io.reactivex.rxkotlin.Observables.combineLatest
import tornadofx.*
import java.util.*

class EditBsiView : View() {
  val editActions = find<EditActions>()
  val editConditions = find<EditConditions>()
  val filteredBsiEvents = find<FilteredBsiEvents>()
  val editTrigger = find<EditTrigger>()
  val editComment: EditBsiComment by inject()
  val controller: EditBsiController by inject()

  override val root = borderpane {
    left = filteredBsiEvents.root
    center = form {
      fieldset("Comment") {
        add(editComment)
      }
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

  override val root = listview<Pair<BsiEvent, String?>> {
    combineLatest(scenarioController.bsi.data(), scenarioController.bsiExtras.data())
      .map { (bsiFile, extras) ->
        bsiFile.events.map { it to extras.events[it.header.eventNum.toInt()]?.comment }
      }
      .subscribe {
        items.setAll(it)
      }

    selectionModel.selectedItemProperty().toObservable().map { (event, _) -> event }.subscribe(controller.selectedEvent)
    controller.selectedEvent.distinct().subscribe { e -> selectionModel.select(items.indexOfFirst{ it.first == e }) }

    cellFormat { (event, comment) ->
      text = event.header.eventNum.toString() + (comment?.let { " - $comment" } ?: "")
    }
  }
}

fun <T> Optional<T>.orNull() = if (isEmpty) null else get()
