import edit.EditScenarioFragment
import edit.ScenarioController
import edit.copyToMods
import io.reactivex.subjects.BehaviorSubject
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import models.CanonicalScenario
import models.canonicalScenarios
import settings.dump.dataDump
import tornadofx.*
import java.io.File

class ScenarioGroupChooserFragment : Fragment() {
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
          val selected = selectedCanonicalScenario.value ?: return@action
          copyScenarioAndOpen(selected)
        }
      }
    }
  }

  fun copyScenarioAndOpen(selected: CanonicalScenario) {
    val rootFile = app.config.dataDump?.baseGame ?: return
    selected.copyToMods(rootFile)
    refreshRecentProjects.onNext(Unit)
    openScenarioEditor(selected)
  }
}

fun File.containsData0() = File(this, "DATA0.bin").exists() && File(this, "DATA1.bin").exists()
fun File.containsExtractedIndexNum() = File(this, "0").exists() && File(this, "1").exists()

fun File.validateBaseDir(): Boolean = containsData0() || containsExtractedIndexNum()

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
