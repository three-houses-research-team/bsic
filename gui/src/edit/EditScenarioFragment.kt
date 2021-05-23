package edit

import bai.BaiFile
import bsi.BsiFile
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import containsData0
import containsExtractedIndexNum
import edit.bsi.EditBsiView
import fs.DATA0Format
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import javafx.scene.control.TabPane
import models.BSIExtras
import models.CanonicalScenario
import models.Language
import okio.Sink
import okio.buffer
import okio.sink
import strings.MapStringsParser
import strings.TextSFile
import terrain.TerrainData
import tornadofx.Controller
import tornadofx.Fragment
import tornadofx.tab
import tornadofx.tabpane
import utils.memmap
import utils.next
import utils.setFileTooltip
import utils.toSubject
import java.io.File
import java.nio.ByteBuffer

val root = File("mods")


var moshi = Moshi.Builder()
  .addLast(KotlinJsonAdapterFactory())
  .build()
var bsiExtrasAdapter: JsonAdapter<BSIExtras> = moshi.adapter(BSIExtras::class.java)

open class RefreshFromFilesystem<T>(val file: File, val reader: (ByteBuffer) -> T) {
  constructor(name: String, reader: (ByteBuffer) -> T) : this(File(root, name), reader)

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

class BsiExtrasFs(bsiEntry: Int) : RefreshFromFilesystem<BSIExtras>(File(root, "$bsiEntry.json"), { buf ->
  bsiExtrasAdapter.fromJson(
    JsonReader.of(okio.Buffer().apply {
      write(buf)
    })
  )!!
}) {
  fun put(extras: BSIExtras) {
    refresh {
      val writer = JsonWriter.of(file.sink(false).buffer())
      bsiExtrasAdapter.toJson(writer, extras)
      writer.flush()
    }
  }

}

class ScenarioController : Controller() {
  val scenario = BehaviorSubject.create<CanonicalScenario>()

  val bai = scenario.map { RefreshFromFilesystem(it.baiEntryID.toString(), ::BaiFile) }.toSubject()
  val bsi = scenario.map { RefreshFromFilesystem(it.bsiEntryID.toString(), ::BsiFile) }.toSubject()

  val bsiExtras = scenario.map {
    val file = File(root, "${it.bsiEntryID}.json")
    if (!file.exists()) file.writeText(bsiExtrasAdapter.toJson(BSIExtras(mapOf())))

    BsiExtrasFs(it.bsiEntryID)
  }.toSubject()

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

fun <Y, T : RefreshFromFilesystem<Y>> BehaviorSubject<T>.data() = flatMap { it.data }
fun <T : RefreshFromFilesystem<*>> BehaviorSubject<T>.file() = map { it.file }

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

  override val root = tabpane {
    tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
    tab("Tiles") {
      setFileTooltip(scenarioController.terrain)
      add(editTerrainView)
    }
    tab("BAI") {
      setFileTooltip(scenarioController.bai)
      add(editBaiView)
    }
    tab("BSI") {
      setFileTooltip(scenarioController.bsi)
      add(editBsiView)
    }

    tab("SCENDAT") {

    }

    tab("Strings") {
      add(editStringsView)
    }
  }
}
