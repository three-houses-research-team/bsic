package settings

import javafx.scene.control.TabPane
import settings.dump.SettingsDataDumpView
import tornadofx.*

class SettingsView : View() {
  val dataDumpSettings by inject<SettingsDataDumpView>()
  override val root = tabpane {
    tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE

    tab("Game dump directories") {
      add(dataDumpSettings)
    }
  }
}

/** debug method for testing settings */
fun main(args: Array<String>) {
    launch<SettingsApp>(args)
}

class SettingsApp : App(SettingsView::class) {

}
