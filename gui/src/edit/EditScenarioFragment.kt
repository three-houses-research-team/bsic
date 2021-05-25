package edit

import ScenarioController
import edit.bsi.EditBsiView
import javafx.scene.control.TabPane
import tornadofx.Fragment
import tornadofx.tab
import tornadofx.tabpane
import utils.setFileTooltip

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
