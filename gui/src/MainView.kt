import tornadofx.View
import tornadofx.vbox

class MainView : View() {
  val scenarioChooser = find<ScenarioGroupChooserFragment>()
  override val root = vbox {
    add(scenarioChooser)
  }

  init {
    title = "Bsic"
  }
}
