import edit.EditScenarioFragment
import fs.DATA0Format
import io.reactivex.subjects.BehaviorSubject
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import models.CanonicalScenario
import models.Language
import models.canonicalScenarios
import settings.dump.dataDump
import tornadofx.*
import utils.fs.containsData0
import utils.fs.containsExtractedIndexNum
import utils.memmap
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
                item(recentItem.nameEngU) {
                  action {
                    openScenarioEditor(recentItem)
                  }
                }
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

  fun CanonicalScenario.copyToMods(root: File) {
    val mods = File("mods").also(File::mkdirs)

    val copyFileToMods: (Int) -> Unit = when {
      root.containsData0() -> {
        val data0 = DATA0Format(File(root, "DATA0.bin"))
        val data1 = File(root, "DATA1.bin");
        { index ->
          val buf = data1.memmap(size = data0[index].decompressedSize.toLong(), start = data0[index].offset.toLong())
          File(mods, "$index").outputStream().channel.use {
            it.write(buf)
          }
        }
      }
      root.containsExtractedIndexNum() -> {
        { index -> File(root, "$index").copyTo(File(mods, "$index"), false) }
      }
      else -> kotlin.error("not data0 or extractedindexnum")
    }

    copyFileToMods(baiEntryID)
    copyFileToMods(bsiEntryID)
    copyFileToMods(terrainID)
    Language.values().forEach {
      copyFileToMods(textSBase[it]!!)
      copyFileToMods(textBBase[it]!!)
      copyFileToMods(textVBase[it]!!)
    }
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
