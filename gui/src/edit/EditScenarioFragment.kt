package edit

import bai.BaiFile
import bsi.BsiFile
import containsData0
import containsExtractedIndexNum
import edit.bsi.EditBsiView
import fs.DATA0Format
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import javafx.scene.control.TabPane
import kt.fs.FeFileSystem
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
      { index -> File("$index").copyTo(File(mods, "$index"), false) }
    }
    else -> error("not data0 or extractedindexnum")
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

class EditScenarioFragment : Fragment("Edit Scenario") {
  val scenarioController: ScenarioController by inject()
  val editBsiView by inject<EditBsiView>()
  val editBaiView by inject<EditBaiView>()
  val editTerrainView by inject<EditTerrainView>()
  val editStringsView by inject<EditStringsView>()

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
