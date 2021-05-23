package edit.bsi

import com.github.thomasnield.rxkotlinfx.toObservable
import com.github.thomasnield.rxkotlinfx.toObservableChanges
import edit.ScenarioController
import edit.data
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.Observables.combineLatest
import io.reactivex.rxkotlin.withLatestFrom
import models.BSIExtra
import tornadofx.View
import tornadofx.textfield

class EditBsiComment : View() {
  val controller: EditBsiController by inject()
  val scenarioController: ScenarioController by inject()

  override val root = textfield {
    val selectedIndex = controller.selectedEvent.map { it.header.eventNum.toInt() }
    combineLatest(
      selectedIndex,
      scenarioController.bsiExtras.data()
    )
      .map { (index, bsiExtras) -> bsiExtras.events[index]?.comment ?: "" }
      .subscribe { comment ->
        text = comment
      }

    focusedProperty().toObservable().filter { !it } // blur
      .withLatestFrom(selectedIndex)
      .subscribe { (_, index) ->
        val bla = scenarioController.bsiExtras.value!!
        val newText = text.ifEmpty { null }
        bla.refresh {
          val old = bla.data.value!!
          val extra = old.events[index]?.copy(comment = newText) ?: BSIExtra(newText)
          val newExtras = old.copy(events = old.events + (index to extra))
          bla.put(newExtras)
        }
      }
  }
}
