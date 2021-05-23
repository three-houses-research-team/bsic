package edit

import bai.BAIWriter
import bai.BaiFile
import com.github.thomasnield.rxkotlinfx.toObservable
import edit.bsi.EditBsiController
import edit.bsi.orNull
import io.reactivex.rxkotlin.Observables.combineLatest
import io.reactivex.subjects.BehaviorSubject
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.geometry.Pos
import javafx.scene.control.TabPane
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.paint.Color
import javafx.scene.paint.Paint
import models.ItemSlotU2
import models.index
import models.terrainColors
import tornadofx.*
import utils.makeItemEditable
import utils.notSortable
import utils.makeEnumEditable
import java.util.*

class EditBaiView : View() {
  val scenarioController: ScenarioController by inject()
  val controller: EditBsiController by inject()

  val pickTile: PickTileView by inject()

  override val root = borderpane {
    top { add(pickTile) }
    center = tabpane {
      tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE

      // subscribe tab selection -> bai list -> picktileview
      selectionModel.selectedIndexProperty().toObservable()
        .flatMap { versionIndex ->
          scenarioController.bai.data().map {
            it.blocks.getOrNull(versionIndex.toInt())?.withIndex()?.toList() ?: listOf()
          }
        }.subscribe(pickTile.loadedBaiSlots)

      for (versionIndex in 0..2) tab("Version ${versionIndex + 1}") {
        tableview<BaiModel> {
          isEditable = true

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

          // subscribe items
          scenarioController.bai.data()
            .map { it.blocks[versionIndex].mapIndexed { index, value -> BaiModel(index, value) } }.subscribe {
              items.setAll(it)
            }

          // subscribe row highlight -> pick title view highlight
          selectionModel.selectedItemProperty().toObservable()
            .map { Optional.of(it.xProp.value to it.yProp.value) }
            .subscribe(pickTile.highlighted)

          onEditCommit { edited ->
            val bai = scenarioController.bai.value!!
            val baiFlat = bai.data.value!!.blocks.flatten().toMutableList()
            val serialized = edited.convert()
            baiFlat[tablePosition.row + versionIndex * 100] = serialized
            bai.refresh {
              BAIWriter.write(bai.file, baiFlat)
            }
          }
        }
      }
    }
  }
}

class BaiModel(var index: Int, val orig: BaiFile.CharacterBlock) : ItemViewModel<BaiModel>() {
  val characterProp = SimpleObjectProperty(orig.character)
  val team = SimpleObjectProperty(orig.team)
  val level = SimpleIntegerProperty(orig.level.toInt())
  val classID = SimpleObjectProperty(orig.feClass)
  val spawn = SimpleObjectProperty(orig.scriptSpawn)

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

  fun convert() = BaiFile.CharacterBlock(
    (characterProp.value?.ordinal?.plus(1) ?: 0).toUShort(),
    orig.unk, // TODO
    (classID.value?.ordinal?.plus(1) ?: 0).toUShort(),
    listOf(item1, item2, item3, item4, item5, item6).map { it.value.index }.map { ItemSlotU2(it.toUShort()) },
    itemsFlags.value!!.toUShort(),
    listOf(ability1, ability2, ability3, ability4, ability5).map { it.value?.ordinal?.plus(1) ?: 0 }
      .map { it.toUByte() },
    x.toUByte(),
    yProp.value!!.toUByte(),
    rotation.value!!.toUByte(),
    level.value!!.toUByte(),
    team.value!!,
    spawn.value!!,
    movement.value!!.toUByte(),
    orig.unk1, // TODO
    orig.commanderStatus, // TODO
    (battalion.value?.ordinal?.plus(1) ?: 0).toUByte(),
    battalionLevel.value!!.toUByte(),
    (spell1.value?.ordinal?.plus(1) ?: 0).toUByte(),
    (spell2.value?.ordinal?.plus(1) ?: 0).toUByte(),
    orig.combatArt, // TODO
    orig.gender, // TODO
    orig.padding, // TODO
  )
}

class PickTileView : View() {
  val scenarioController: ScenarioController by inject()
  val loadedBaiSlots = BehaviorSubject.createDefault(listOf<IndexedValue<BaiFile.CharacterBlock>>())

  val highlighted = BehaviorSubject.createDefault(Optional.empty<Pair<Int, Int>>())

  override val root = gridpane {
    combineLatest(
      scenarioController.terrain.data().map { it.grid },
      loadedBaiSlots,
      highlighted,
    ).subscribe { (terrainData, baiSlots, highlight) ->
      val (width, height) = terrainData.findMinimumMapSize()

      replaceChildren {
        for (y in 0 until height) row {
          for (x in 0 until width) hbox {
            val isHighlighted = x == highlight.orNull()?.first && y == highlight.orNull()?.second
            style {
              if (isHighlighted) {
                borderWidth += box(2.px)
                borderColor += box(Paint.valueOf("red"))
              }
            }
            val (tile1, tile2) = terrainData.map[x to y]!!
            background = Background(BackgroundFill(Color.web(terrainColors[tile1]), null, null))
            if (isHighlighted) {
              minWidth = 16.0
              minHeight = 16.0
            } else {
              minWidth = 20.0
              minHeight = 20.0
            }
            alignment = Pos.CENTER


            label {
              style {
                fontSize = 10.px
              }

              val block = baiSlots.find { (_, it) -> it.xCoord.toInt() == x && it.yCoord.toInt() == y }
              if (block != null && block.value.character != null) {
                text = "${block.index % 100}"
              }
            }
          }
        }
      }
    }
  }
}
