package utils

import javafx.scene.Node
import javafx.scene.layout.Priority
import tornadofx.hgrow
import tornadofx.vgrow

fun Node.vgrow() {
  vgrow = Priority.ALWAYS
}

fun Node.hgrow() {
  hgrow = Priority.ALWAYS
}
