import edit.EditScenarioFragment
import edit.ScenarioController
import models.canonicalScenarios
import tornadofx.App
import tornadofx.find
import tornadofx.launch

class BsicApp : App(MainView::class)

fun main(args: Array<String>) {
  if (args.getOrNull(0) == "--debug")
    launch<DebugApp>(args)
  else
    launch<BsicApp>(args)
}

class DebugApp : App(EditScenarioFragment::class) {
  init {
    val selected = canonicalScenarios[1]
    find<ScenarioController>().scenario.onNext(selected)
  }
}
