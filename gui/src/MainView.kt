import settings.dump.SettingsDataDumpView
import settings.dump.dataDump
import tornadofx.View
import tornadofx.vbox

class MainView : View() {
  val scenarioChooser = find<ScenarioGroupChooserFragment>()
  val datadumpSettings = find<SettingsDataDumpView>()

  override val root = vbox {
    add(scenarioChooser)

    while (app.config.dataDump?.baseGame == null) {
      datadumpSettings.openModal(block = true)
    }
  }

  init {
    title = "Bsic"
  }
}
