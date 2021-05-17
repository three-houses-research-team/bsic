import edit.EditScenarioFragment
import edit.ScenarioController
import models.canonicalScenarios
import tornadofx.App
import tornadofx.find
import tornadofx.launch

class BsicApp : App(MainView::class) {
}

fun main(args: Array<String>) {
//  launch<BsicApp>(args)
  launch<DebugApp>(args)
}

class DebugApp : App(EditScenarioFragment::class) {
  init {
    val selected = canonicalScenarios[1]
    find<ScenarioController>().scenario.onNext(selected)
  }
}
