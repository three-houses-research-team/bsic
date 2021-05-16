package edit

import bai.BaiFile
import edit.bsi.EditBsiController
import io.reactivex.subjects.BehaviorSubject
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import javafx.scene.control.TabPane
import javafx.scene.control.TableColumn
import javafx.scene.control.cell.ComboBoxTableCell
import javafx.scene.control.cell.TextFieldTableCell
import javafx.util.StringConverter
import models.InventorySlottable
import terrain.TerrainTileType
import terrain.TerrainTiles
import tornadofx.*
import utils.EnumStringConverter
import kotlin.reflect.full.starProjectedType

class EditBaiView : View() {
  val scenarioController: ScenarioController by inject()
  val controller: EditBsiController by inject()

  override val root = tabpane {
    tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
    for (versionIndex in 0..2) tab("$versionIndex") {

//      val withIndex = bai.blocks[versionIndex].withIndex()
//      val version = withIndex.toList().map { BaiModel(it.index, it.value) }.asObservable()
      borderpane {
        val view = PickTileView()

//        top {
//          val toMap = withIndex.associate { (it.value.xCoord.toInt() to it.value.yCoord.toInt()) to it.index }
//          for ((k, v) in toMap) view.posMap[k] = v
//          add(view)
//        }

        center = tableview<BaiModel> {
          readonlyColumn("ID", BaiModel::index)
          column("Character", BaiModel::characterProp).makeEnumEditable().notSortable()
          column("Team", BaiModel::team).makeEnumEditable().notSortable()
          column("Level", BaiModel::level).makeEditable().notSortable()
          column("X", BaiModel::xProp).makeEditable().notSortable()
          column("Y", BaiModel::yProp).makeEditable().notSortable()
          column("Class", BaiModel::classID).makeEnumEditable().notSortable()
          column("Item1", BaiModel::item1).makeItemEditable().notSortable()
          column("Item2", BaiModel::item2).makeItemEditable().notSortable()
          column("Item3", BaiModel::item3).makeItemEditable().notSortable()
          column("Item4", BaiModel::item4).makeItemEditable().notSortable()
          column("Item5", BaiModel::item5).makeItemEditable().notSortable()
          column("Item6", BaiModel::item6).makeItemEditable().notSortable()
          column("Spell", BaiModel::spell1).makeEnumEditable().notSortable()
          column("Spell2", BaiModel::spell2).makeEnumEditable().notSortable()
          column("Battalion", BaiModel::battalion).makeEnumEditable().notSortable()
          column("Batta lvl", BaiModel::battalionLevel).makeEditable().notSortable()

          column("Ability 1", BaiModel::ability1).makeEnumEditable().notSortable()
          column("Ability 2", BaiModel::ability2).makeEnumEditable().notSortable()
          column("Ability 3", BaiModel::ability3).makeEnumEditable().notSortable()
          column("Ability 4", BaiModel::ability4).makeEnumEditable().notSortable()
          column("Ability 5", BaiModel::ability5).makeEnumEditable().notSortable()

//          view.clickedOnTile = fun(pair) {
//            val (x, y) = pair
//            val found = version.find { it.xProp.value.toInt() == x && it.yProp.value.toInt() == y }?.index ?: return
//            selectionModel.select(found)
//          }
          scenarioController.bai.data().map { it.blocks[versionIndex].mapIndexed { index, value -> BaiModel(index, value) } }.subscribe {
            items.setAll(it)
          }

          isEditable = true
        }
      }
    }
  }
}

inline fun <T, S : Any?> TableColumn<T, S>.notSortable() = apply { isSortable = false }
inline fun <T, reified S : Enum<*>?> TableColumn<T, S>.makeEnumEditable() = apply {
  isEditable = true
  val constants = S::class.java.enumConstants

  val values: Array<S?> = if (S::class.starProjectedType.isMarkedNullable) (listOf(null) + constants).toTypedArray() else constants
  cellFactory = ComboBoxTableCell.forTableColumn(EnumStringConverter<S>(), *values)
}

inline fun <T> TableColumn<T, InventorySlottable?>.makeItemEditable() = apply {
  isEditable = true
  val items: List<InventorySlottable?> = listOf(null) + InventorySlottable.all
  val x = object : StringConverter<InventorySlottable?>() {
    override fun toString(obj: InventorySlottable?): String = obj?.toString() ?: "None"

    override fun fromString(string: String?): InventorySlottable? {
      TODO("Not yet implemented")
    }

  }
  cellFactory = ComboBoxTableCell.forTableColumn(x, items.asObservable())
}

fun <T> TableColumn<T, Int?>.makeEditable() = apply {
  isEditable = true
  cellFactory = TextFieldTableCell.forTableColumn(object : StringConverter<Int?>() {
    override fun toString(obj: Int?): String = obj?.toString() ?: ""

    override fun fromString(string: String?) = string?.toIntOrNull()
  })
}

class BaiModel(var index: Int, orig: BaiFile.CharacterBlock) : ItemViewModel<BaiModel>() {
  val characterProp = SimpleObjectProperty(orig.character)
  val team = SimpleObjectProperty(orig.team)
  val level = SimpleIntegerProperty(orig.level.toInt())
  val classID = SimpleObjectProperty(orig.feClass)

  val xProp = SimpleIntegerProperty(orig.xCoord.toInt());
  var x by xProp
  val yProp = SimpleIntegerProperty(orig.yCoord.toInt())
  val itemsFlags = SimpleIntegerProperty(orig.itemsFlags.toInt())
  val rotation = SimpleIntegerProperty(orig.rotation.toInt())
  val movement = SimpleIntegerProperty(orig.movementFlags.toInt())
  val item1 = SimpleObjectProperty(orig.inventory.getOrNull(0)?.slotted)
  val item2 = SimpleObjectProperty(orig.inventory.getOrNull(1)?.slotted)
  val item3 = SimpleObjectProperty(orig.inventory.getOrNull(2)?.slotted)
  val item4 = SimpleObjectProperty(orig.inventory.getOrNull(3)?.slotted)
  val item5 = SimpleObjectProperty(orig.inventory.getOrNull(4)?.slotted)
  val item6 = SimpleObjectProperty(orig.inventory.getOrNull(5)?.slotted)
  val battalion = SimpleObjectProperty(orig.battalion)
  val battalionLevel = SimpleIntegerProperty(orig.battalionLevel.toInt())
  val spell1 = SimpleObjectProperty(orig.spell)
  val spell2 = SimpleObjectProperty(orig.spell2)
  val ability1 = SimpleObjectProperty(orig.abilities.getOrNull(0))
  val ability2 = SimpleObjectProperty(orig.abilities.getOrNull(1))
  val ability3 = SimpleObjectProperty(orig.abilities.getOrNull(2))
  val ability4 = SimpleObjectProperty(orig.abilities.getOrNull(3))
  val ability5 = SimpleObjectProperty(orig.abilities.getOrNull(4))
}

class PickTileView : View() {
  val selectTile = BehaviorSubject.create<Pair<Int, Int>>()
  val posMap = FXCollections.observableHashMap<Pair<Int, Int>, Int>()
  val scenarioController: ScenarioController by inject()

  override val root = borderpane {
    top = gridpane {
      scenarioController.terrain.data().map { it.grid }.subscribe { terrainData ->
        replaceChildren {
//          val (rows, cols) = findOccupiedMaxSizes(terrainData)
//          for ((y, row) in terrainData.withIndex().take(rows)) row {
//            for ((x, item) in row.withIndex().take(cols)) {
//              hbox {
//                val tile = item.first
//                background = Background(BackgroundFill(Color.web(terrainColors[tile]), null, null))
//                minWidth = 20.0
//                minHeight = 20.0
//                alignment = Pos.CENTER
////                setOnMouseDragged {
////                }
////                setOnMouseEntered {
////                  selectedTile.set(x to y)
////                }
////                setOnMouseClicked {
////                  clickedOnTile?.invoke(x to y)
////                }
//
//                label {
//                  Bindings.valueAt(posMap, x to y).onChange { text = it.toString() }
//                  style = "-fx-font-size: 10px;"
//                }
//              }
//            }
//          }
        }
      }
    }
//    center = vbox {
//      label {
//        selectTile.subscribe { (x, y) -> text = "($x, $y): ${terrain.grid[y][x]} }" }
//      }
//      label(selectedTile, converter = object : StringConverter<Pair<Int, Int>>() {
//        override fun toString(obj: Pair<Int, Int>?) = obj?.let {
//          val (x, y) = obj
//          "($x, $y): ${terrain.grid[y][x]} }"
//        } ?: ""
//
//        override fun fromString(string: String?): Pair<Int, Int> = TODO()
//      })
//    }
  }

  fun findOccupiedMaxSizes(tiles: TerrainTiles): Pair<Int, Int> {
    var rows = 0
    var cols = 0
    for ((y, row) in tiles.withIndex()) {
      for ((x, tile) in row.withIndex()) {
        if (tile.first != TerrainTileType.Impassable_0) {
          if (rows < y) rows = y
          if (cols < x) cols = x
        }
      }
    }
    return cols + 1 to rows + 1
  }
}
