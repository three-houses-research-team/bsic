package edit

import com.github.thomasnield.rxkotlinfx.itemSelections
import edit.bsi.EditBsiController
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables.combineLatest
import io.reactivex.subjects.BehaviorSubject
import javafx.geometry.Pos
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.paint.Color
import models.terrainColors
import terrain.TerrainGrid
import terrain.TerrainTileType
import tornadofx.*

private class MutableTerrainObservable(
  val obs: Observable<TerrainGrid>
) {
  val putTile = BehaviorSubject.create<Pair<TerrainTileType, Pair<Int, Int>>>()
  val updatedTerrain = BehaviorSubject.create<TerrainGrid>()
  private var data: MutableMap<Pair<Int, Int>, Pair<TerrainTileType, TerrainTileType>> = mutableMapOf()
  init {
    obs.subscribe {
      data.clear()
      data.putAll(it)
      updatedTerrain.onNext(TerrainGrid(data))
    }
    putTile.subscribe { (type, coords) ->
      val (x, y) = coords
      data[x to y] = type to type
      updatedTerrain.onNext(TerrainGrid(data))
    }
  }
}

class EditTerrainView : View() {
  val scenarioController: ScenarioController by inject()
  val controller: EditBsiController by inject()

  val tiles = TerrainTileType.values().toList()
  val brush = BehaviorSubject.createDefault<TerrainTileType>(TerrainTileType.Impassable_0)
  val mappedObservable = scenarioController.terrain.data().map { it.grid }
  val selectedTile = BehaviorSubject.create<Pair<Int, Int>>()
  private val mutableTerrain = MutableTerrainObservable(mappedObservable)

  override val root = borderpane {
    left = listview<TerrainTileType> {
      items.setAll(tiles)
      itemSelections.subscribe(brush)
    }
    center = borderpane {
      top = gridpane {
        for (y in 0 until 32) row {
          for (x in 0 until 32) hbox {
            minWidth = 20.0
            minHeight = 20.0
            alignment = Pos.CENTER
            setOnDragDetected { startFullDrag() }
            setOnMouseDragOver {
              val brushTile = brush.value!!
              mutableTerrain.putTile.onNext(brushTile to (x to y))
            }
            setOnMouseEntered { selectedTile.onNext(x to y) }

            val tileObs = mutableTerrain.updatedTerrain.map { it[x, y]!! }
            tileObs.subscribe { (first, second) ->
              background = Background(BackgroundFill(Color.web(terrainColors[first]), null, null))
            }
            label {
              style = "-fx-font-size: 10px;"
              tileObs.subscribe { (first, second) ->
                text = "${first.ordinal}"
              }
            }
          }
        }
      }
      center = vbox {
        label {
          combineLatest(selectedTile, mutableTerrain.updatedTerrain).subscribe { (coords, grid) ->
            val (x, y) = coords
            text = "($x, $y): ${grid[x, y]!!.first.name}"
          }
        }
      }
    }
  }
}
