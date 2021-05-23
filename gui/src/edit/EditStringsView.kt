package edit

import com.github.thomasnield.rxkotlinfx.onChangedObservable
import com.github.thomasnield.rxkotlinfx.toObservable
import edit.bsi.EditBsiController
import io.reactivex.rxkotlin.Observables.combineLatest
import io.reactivex.subjects.BehaviorSubject
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.TabPane
import javafx.scene.layout.Priority
import models.FeCharacter
import models.Language
import strings.MapString
import strings.MapStrings
import strings.TextSFile
import tornadofx.*
import utils.*

class EditStringsView : View() {
  val scenarioController: ScenarioController by inject()
  val controller: EditBsiController by inject()

  override val root = vbox {
    combobox<Language> {
      controller.selectedLanguage.subscribe { selectionModel.select(it) }
      items.setAll(*Language.EnglishFirst)
      selectionModel.selectedItemProperty().toObservable().subscribe(controller.selectedLanguage)
    }
    tabpane {
      vgrow = Priority.ALWAYS
      tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
      tab("TextS") {
        setFileTooltip(controller.textS)
        vbox {
          scrollpane(fitToWidth = true, fitToHeight = true) {
            vgrow = Priority.ALWAYS
            listview<String> {
              isEditable = true
              cellFactory = TextAreaListCell.forListView()

              controller.textS.data().map { it.strings.toObservable() }.subscribe { itemsProperty().set(it) }
              combineLatest(items.onChangedObservable().skip(1), controller.textS).subscribe { (newItems, textS) ->
                textS.refresh {
                  TextSFile.write(textS.file, newItems)
                }
              }
            }
          }
        }
      }
      tab("TextV") {
        setFileTooltip(controller.textV)
        add(find<MapStringsEditor>(MapStringsEditor::mapStrings to controller.textV))
      }
      tab("TextB") {
        setFileTooltip(controller.textB)
        add(find<MapStringsEditor>(MapStringsEditor::mapStrings to controller.textB))
      }
    }
  }
}

class MapStringModel(original: MapString) {
  val talkerProperty = SimpleObjectProperty(FeCharacter.values().getOrNull(original.who))
  val talker by talkerProperty

  val textProperty = SimpleStringProperty(original.text)
  val text by textProperty

  val voiceIDProperty = SimpleObjectProperty(original.voice)
  val voiceID by voiceIDProperty

  val portraitProperty = SimpleObjectProperty(original.portraitId)
  val portrait: Int? by portraitProperty

  fun build() = MapString(buildString {
    append("[${(talker?.ordinal ?: 9999).toString().padStart(4, '0')}]")
    append(text)
    if (voiceID != null) append("＠${voiceID.toString().padStart(6, '0')}")
    if (portrait != null) append("#${portrait.toString().padStart(2, '0')}")
  })
}


class MapStringsEditor() : Fragment() {
  val mapStrings: BehaviorSubject<RefreshFromFilesystem<List<MapString>>> by param()

  override val root = borderpane {
    center = tableview<MapStringModel> {
      column("Speaker", MapStringModel::talkerProperty).makeEnumEditable()
      column("Text", MapStringModel::textProperty) {
        cellFactory = TextAreaTableCell.forTableColumn()
      }
      column("Voice ID", MapStringModel::voiceIDProperty).makeEditable()
      column("Portrait", MapStringModel::portraitProperty).makeEditable()

      isEditable = true

      mapStrings.data().map { it.map(::MapStringModel) }.subscribe { items.setAll(it) }

      fun save() {
        val fs = mapStrings.value!!
        fs.refresh {
          MapStrings.write(fs.file, items.map { it.build() })
        }
      }
      onEditCommit {
        save()
      }
    }
    bottom = vbox {
      // TODO map string preview
    }
  }
}
