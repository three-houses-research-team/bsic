package utils

import edit.RefreshFromFilesystem
import io.reactivex.Observable
import javafx.scene.control.Tab
import javafx.scene.control.Tooltip

fun <T> Tab.setFileTooltip(obs: Observable<RefreshFromFilesystem<T>>) =
  obs.map { it.file }.subscribe {
    tooltip = Tooltip("File index: ${it.name}")
  }
