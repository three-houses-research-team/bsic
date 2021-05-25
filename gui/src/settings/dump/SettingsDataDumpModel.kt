package settings.dump

import utils.moshi
import javafx.beans.property.SimpleObjectProperty
import tornadofx.ConfigProperties
import tornadofx.ItemViewModel
import java.io.File

val settingsAdapter = moshi.adapter(SettingsDataDump::class.java)

data class SettingsDataDump(val baseGame: File?, val patch: File?, val csDLC: File?)

class SettingsDataDumpModel() : ItemViewModel<SettingsDataDump>() {
  val baseGame = bind { SimpleObjectProperty<File>(item?.baseGame) }
  val patch = bind { SimpleObjectProperty<File>(item?.patch) }
  val csDLC = bind { SimpleObjectProperty<File>(item?.csDLC) }

  override fun onCommit() {
    item = SettingsDataDump(baseGame.value, patch.value, csDLC.value) // ???
  }
}

val ConfigProperties.dataDump: SettingsDataDump? get() {
  val dataDumpFromConfig = this["datadump"] as? String ?: return null
  return settingsAdapter.fromJson(dataDumpFromConfig)
}
