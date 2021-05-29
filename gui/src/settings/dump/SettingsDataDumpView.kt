package settings.dump

import javafx.beans.property.SimpleObjectProperty
import javafx.event.EventTarget
import javafx.geometry.Pos
import javafx.scene.text.FontWeight
import tornadofx.*
import utils.AbsoluteFileStringConverter
import utils.hgrow
import java.io.File

class SettingsDataDumpView : View(title = "Data dump settings") {
  val model by inject<SettingsDataDumpModel>()
  val dataDumpFromConfig = app.config.dataDump
  init {
    model.rebind {
      item = dataDumpFromConfig
    }
  }

  override val root = form {
    fieldset("Base game dump") {
      label("Select a directory that contains the base game dump for fe3h.\nThis directory should have one of the following structures:\n")
      //region Base game explanation
      hbox {
        vbox {
          hgrow()
          textflow {
            text("Raw data dump (from NSP using e.g. ") { style { fontWeight = FontWeight.BOLD } }
            hyperlink("hactool") {
              action {
                hostServices.showDocument("https://github.com/SciresM/hactool")
              }
            }
            text(")") { style { fontWeight = FontWeight.BOLD } }
          }
          label(
            """
          selected folder
          ├── DATA0.bin (~ 1MB)
          ├── DATA1.bin (~ 6.5 GiB)
          └── nx
             ├── movie
             │  └── jpn
             └── sound
        """.trimIndent()
          ) {
            style {
              fontFamily = "monospaced"
            }
          }
        }
        vbox {
          hgrow()
          textflow {
            text("Split data dump (using e.g. ") { style { fontWeight = FontWeight.BOLD } }
            hyperlink("extractIndexNum.py") {
              action {
                hostServices.showDocument("https://github.com/three-houses-research-team/Throne-of-Knowledge/wiki/Dump-&-Extract-Game#extract-romfs--exefs-of-base-game")
              }
            }
            text(")") { style { fontWeight = FontWeight.BOLD } }
          }
          label("NOT compatible with dumps made with Steven's Gas Machine")
          label(
            """
          selected folder
          ├── 0
          ├── 1
          ├── 2
          ├── 3
          ├── ...
        """.trimIndent()
          ) {
            style {
              fontFamily = "monospaced"
            }
          }
        }
      }
      //endregion

      field {
        chooseDirectoryInto(model.baseGame, BaseDumpValidator)
      }
    }
    fieldset("Patch dump") {
      label("Select a directory that contains the 1.2.0 patch files.\nThis directory should have the following structure:")
      label(
        """
          selected folder
          ├── patch1
          ├── patch2
          ├── patch3
          └── patch4
        """.trimIndent()
      ) {
        style {
          fontFamily = "monospaced"
        }
      }

      field {
        chooseDirectoryInto(model.patch, PatchDumpValidator)
      }

      /*field("Cindered Shadows DLC") {
        chooseDirectoryInto(model.csDLC) {
          null
        }
      }*/
    }
    fieldset {
      button("Save and Quit") {
        enableWhen(model.valid)
        action {
          close()
        }
      }
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
      setOnKeyTyped { save() }
    }
    button("Choose...") {
      action {
        property.set(chooseDirectory())
      }
    }
  }
}




