import edit.EditScenarioFragment
import edit.ScenarioController
import edit.copyToMods
import io.reactivex.subjects.BehaviorSubject
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import models.CanonicalScenario
import models.canonicalScenarios
import tornadofx.*
import java.io.File

class ScenarioGroupChooserFragment : Fragment() {
  val rootdir = SimpleStringProperty(config.string("rootdir"))
  val selectedCanonicalScenario = SimpleObjectProperty<CanonicalScenario>()
  val canon = FXCollections.unmodifiableObservableList(canonicalScenarios.toList().asObservable())

  val refreshRecentProjects = BehaviorSubject.create<Unit>()

  override val root = borderpane {
    top = menubar {
      menu("File") {
        menu("Open Recent...") {
          refreshRecentProjects.startWith(Unit).map { findRecentProjects() }.subscribe { recents ->
            replaceChildren {
              for (recentItem in recents) {
                item(recentItem.nameEngU)
              }
            }
          }
        }
      }
    }
    center = form {
      fieldset("From data dump") {
        label("Opening a scenario here will copy the files out of your dump directory and into the \"mods\" folder.")
        field("Base game dump") {
          textfield(rootdir)
          button("Select root directory").action {
            val result = chooseDirectory("Select root")
            if (result != null) {
              rootdir.value = result.absolutePath
            }
          }
        }
        field("Scenarios") {
          listview<CanonicalScenario>(canon) {
            bindSelected(selectedCanonicalScenario)
            onDoubleClick {
              selectedItem?.let {
                copyScenarioAndOpen(it)
              }
            }
          }
        }
        button("Open").action {
          config["rootdir"] = rootdir.value
          config.save()
          val selected = selectedCanonicalScenario.value ?: return@action
          copyScenarioAndOpen(selected)
        }
      }
    }
  }

  fun copyScenarioAndOpen(selected: CanonicalScenario) {
    val rootFile = File(rootdir.value)
    selected.copyToMods(rootFile)
    refreshRecentProjects.onNext(Unit)
    openScenarioEditor(selected)
  }
}

fun openScenarioEditor(selected: CanonicalScenario) {
  find<ScenarioController>().scenario.onNext(selected)
  find<EditScenarioFragment>().openModal()
}

private fun findRecentProjects(): List<CanonicalScenario> {
  val root = File("mods").also(File::mkdirs)
  val files = root.listFiles() ?: return listOf()
  val asEffList = files.mapNotNull { it.name.toIntOrNull() }.toSortedSet()
  return canonicalScenarios.filter { asEffList.contains(it.bsiEntryID) }
}
