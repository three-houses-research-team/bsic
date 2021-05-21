package utils

import edit.ScenarioController
import io.reactivex.Observable
import javafx.scene.control.Tab
import javafx.scene.control.Tooltip

fun <T> Tab.setFileTooltip(obs: Observable<ScenarioController.RefreshFromFilesystem<T>>) =
  obs.map { it.file }.subscribe {
    tooltip = Tooltip("File index: ${it.name}")
  }
