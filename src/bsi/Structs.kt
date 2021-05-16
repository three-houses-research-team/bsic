package bsi

import models.Accessory
import models.Consumable
import models.InventorySlottable
import models.Weapon

enum class ComparisonOperator { LT, LTE, GT, GTE }
enum class ComparisonOperator2 { LT, LTE, EQ, GTE, GT }
enum class DifficultyMode { NORMAL, HARD, MADDENING }
enum class SpareKillResultEnum { SPARED, KILLED }


data class Rectangle(val left: UInt, val top: UInt, val right: UInt, val bottom: UInt) {
  override fun toString() = "Rectangle(($left, $top), ($right, $bottom))"
  fun asCoords() = Coordinates(left, top) to Coordinates(right, bottom)
}

data class Coordinates(val x: UInt, val y: UInt) {
  override fun toString() = "Coords($x, $y)"
}

data class ItemSlotU2(val raw: UShort) {
  val slotted: InventorySlottable? = when(val raw = raw.toInt()) {
    0 -> null
    in 1..311 -> Weapon.values()[raw]
    in 600..999 -> Accessory.values()[raw - 601]
    in 1000..1226 -> Consumable.values()[raw - 1001]
    else -> error("Don't know what $raw is")
  }
  override fun toString() = "ItemSlot(raw=$raw=${slotted})"
}

data class ItemSlot(val raw: UInt) {
  val slotted: InventorySlottable? = when(val raw = raw.toInt()) {
    in 0..310 -> Weapon.values()[raw-1]
    in 600..999 -> Accessory.values()[raw - 600]
    in 1000..1226 -> Consumable.values()[raw - 1000]
    else -> error("Don't know what $raw is")
  }
  override fun toString() = "ItemSlot(raw=$raw=${slotted})"
}


enum class Team { PLAYER, ENEMY, ALLY, ENEMY_YELLOW, UNK4 }

enum class FeRoute {
  SILVER_SNOW,
  AZURE_MOON,
  VERDANT_WIND,
  CRIMSON_FLOWER,
  CINDERED_SHADOWS;

  val niceString get() = when(this) {
    SILVER_SNOW -> "Silver Snow"
    AZURE_MOON -> "Azure Moon"
    VERDANT_WIND -> "Verdant Wind"
    CRIMSON_FLOWER -> "Crimson Flower"
    CINDERED_SHADOWS -> "Cindered Shadows"
  }
}

