package utils

import javafx.scene.control.TableColumn
import javafx.scene.control.cell.ComboBoxTableCell
import javafx.scene.control.cell.TextFieldTableCell
import javafx.util.StringConverter
import models.InventorySlottable
import tornadofx.asObservable
import kotlin.reflect.full.starProjectedType

fun <T, S : Any?> TableColumn<T, S>.notSortable() = apply { isSortable = false }
inline fun <T, reified S : Enum<*>?> TableColumn<T, S>.makeEnumEditable() = apply {
  isEditable = true
  val constants = S::class.java.enumConstants

  val values: Array<S?> =
    if (S::class.starProjectedType.isMarkedNullable) (listOf(null) + constants).toTypedArray() else constants
  cellFactory = ComboBoxTableCell.forTableColumn(EnumStringConverter<S>(), *values)
}

fun <T> TableColumn<T, InventorySlottable?>.makeItemEditable() = apply {
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
