import bai.BaiFile
import bsi.BsiFile
import io.reactivex.subjects.BehaviorSubject
import models.*
import strings.MapStringsParser
import strings.TextSFile
import terrain.TerrainData
import tornadofx.Controller
import utils.toSubject
import java.io.File

class ScenarioController : Controller() {
  val scenario = BehaviorSubject.create<CanonicalScenario>()

  val bai = scenario.map { RefreshFromFilesystem(it.baiEntryID.toString(), ::BaiFile) }.toSubject()
  val bsi = scenario.map { RefreshFromFilesystem(it.bsiEntryID.toString(), ::BsiFile) }.toSubject()

  val bsiExtras = scenario.map {
    val file = File("mods", "${it.bsiEntryID}.json")
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
