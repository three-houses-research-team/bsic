package settings.dump

import javafx.beans.property.SimpleObjectProperty
import javafx.event.EventTarget
import tornadofx.*
import utils.AbsoluteFileStringConverter
import java.io.File

class SettingsDataDumpView : View() {
  val model by inject<SettingsDataDumpModel>()
  val dataDumpFromConfig = app.config.dataDump
  init {
    model.rebind {
      item = dataDumpFromConfig
    }
  }

  override val root = form {
    fieldset {
      field("Base game dump directory") {
        chooseDirectoryInto(model.baseGame, BaseDumpValidator)
      }

      field("1.2.0 patch directory") {
        chooseDirectoryInto(model.patch, PatchDumpValidator)
      }

      /*field("Cindered Shadows DLC") {
        chooseDirectoryInto(model.csDLC) {
          null
        }
      }*/
    }
  }

  fun save() {
    model.commit {
      app.config["datadump"] = settingsAdapter.toJson(model.item)
      app.config.save()
    }
  }

  private fun EventTarget.chooseDirectoryInto(
    property: SimpleObjectProperty<File>,
    validator: ValidationContext.(String?) -> ValidationMessage?
  ) {
    textfield(property, AbsoluteFileStringConverter) {
      validator(validator = validator)
      setOnKeyTyped { save()  }
    }
    button("Choose...") {
      action {
        property.set(chooseDirectory())
      }
    }
  }
}




