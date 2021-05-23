package edit.bsi

import bsi.BsiEvent
import edit.ScenarioController
import edit.data
import io.reactivex.rxkotlin.withLatestFrom
import io.reactivex.subjects.BehaviorSubject
import models.Language
import tornadofx.Controller
import utils.toSubject

class EditBsiController : Controller() {
  val scenarioController: ScenarioController by inject()

  val selectedEvent = BehaviorSubject.create<BsiEvent>()
  val selectedLanguage = BehaviorSubject.createDefault(Language.ENG_U)
  val textS = selectedLanguage.flatMap { scenarioController.textS[it]!! }.toSubject()
  val textV = selectedLanguage.flatMap { scenarioController.textV[it]!! }.toSubject()
  val textB = selectedLanguage.flatMap { scenarioController.textB[it]!! }.toSubject()

  val jumpToEventId = BehaviorSubject.create<Int>()

  init {
    jumpToEventId.withLatestFrom(scenarioController.bsi.data()).subscribe { (jumpToId, bsi) ->
      selectedEvent.onNext(bsi.events[jumpToId])
    }
  }
}
