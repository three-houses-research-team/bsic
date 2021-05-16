package edit

import bai.BaiFile
import bsi.BsiFile
import edit.bsi.EditBsiView
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import javafx.scene.control.TabPane
import models.CanonicalScenario
import models.Language
import strings.MapStringsParser
import strings.TextSFile
import terrain.TerrainData
import tornadofx.Controller
import tornadofx.Fragment
import tornadofx.tab
import tornadofx.tabpane
import utils.memmap
import utils.next
import utils.toSubject
import java.io.File
import java.nio.ByteBuffer

class ScenarioController : Controller() {
  val root = File("mods")
  val scenario = BehaviorSubject.create<CanonicalScenario>()

  inner class RefreshFromFilesystem<T>(val name: String, val reader: (ByteBuffer) -> T) {
    val file = File(root, name)
    val data = BehaviorSubject.create<T>()
    val refresh = BehaviorSubject.create<Unit>()

    init {
      refresh.startWith(Unit).map { file.memmap(fn = reader) }.subscribe(data)
    }
    fun refresh(writer: () -> Any) {
      writer()
      refresh.next()
    }
  }

  val bai = scenario.map { RefreshFromFilesystem(it.baiEntryID.toString(), ::BaiFile) }.toSubject()
  val bsi = scenario.map { RefreshFromFilesystem(it.bsiEntryID.toString(), ::BsiFile) }.toSubject()
  val terrain = scenario.map { RefreshFromFilesystem(it.terrainID.toString(), ::TerrainData) }.toSubject()

  val textS = Language.values().associateWith {
    scenario.map { scen -> RefreshFromFilesystem(scen.textSBase[it].toString(), ::TextSFile) }.toSubject()
  }

  val textB = Language.values().associateWith {
    scenario.map { scen -> RefreshFromFilesystem(scen.textBBase[it].toString(), MapStringsParser) }.toSubject()
  }

  val textV = Language.values().associateWith {
    scenario.map { scen -> RefreshFromFilesystem(scen.textVBase[it].toString(), MapStringsParser) }.toSubject()
  }
}

fun <T> Observable<ScenarioController.RefreshFromFilesystem<T>>.data() = flatMap { it.data }
fun <T> Observable<ScenarioController.RefreshFromFilesystem<T>>.file() = map { it.file }


fun CanonicalScenario.copyToMods(root: File) {
  val mods = File("mods").also(File::mkdirs)
  fun File.copyFileToMods() = copyTo(File(mods, name), false)
  File(root, "$baiEntryID").copyFileToMods()
  File(root, "$bsiEntryID").copyFileToMods()
  File(root, "$terrainID").copyFileToMods()
  listOf(Language.ENG_U).associateWith {
    File(root, "${textSBase[it]}")
  }.values.map { it.copyFileToMods() }
  listOf(Language.ENG_U).associateWith {
    File(root, "${textBBase[it]}")
  }.values.map { it.copyFileToMods() }
  listOf(Language.ENG_U).associateWith {
    File(root, "${textVBase[it]}")
  }.values.map { it.copyFileToMods() }
}

class EditScenarioFragment : Fragment("Edit Scenario") {
  val scenarioController: ScenarioController by inject()
  val editBsiView = find<EditBsiView>()
  val editBaiView = find<EditBaiView>()
  val editTerrainView = find<EditTerrainView>()
  val editStringsView = find<EditStringsView>()

  override val root = with(scope) {
    tabpane {
      tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
      tab("Tiles") {
        add(editTerrainView)
      }
      tab("BAI") {
        add(editBaiView)
      }
      tab("BSI") {
        add(editBsiView)
      }

      tab("SCENDAT") {

      }

      tab("Strings") {
        add(editStringsView)
      }
    }
  }
}
